package emv.qrcode.model;

import emv.qrcode.enums.ITag;
import emv.qrcode.util.ValidationUtils;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDataModel<T extends ITag> implements Serializable {
	private Pattern RFU_Pattern;
	private T[] allTags;
	private String rootTag;
	private HashMap<String, Serializable> map;

	AbstractDataModel(Class<T> typeParameterClass, String RFU_Regex) {
		this.RFU_Pattern = null;
		this.rootTag = null;
		this.map = new HashMap();
		this.allTags = typeParameterClass.getEnumConstants();
		if (!RFU_Regex.isEmpty()) {
			this.RFU_Pattern = Pattern.compile(RFU_Regex);
		}

	}

	AbstractDataModel(Class<T> typeParameterClass, String RFU_Regex, String rootTag) {
		this(typeParameterClass, RFU_Regex);
		this.rootTag = rootTag;
	}

	private boolean checkValueAgainstTagString(String tagString) {
		ValidationUtils.notNull(tagString);
		return this.map.containsKey(tagString);
	}

	public boolean hasValue(String tagString) throws Exception {
		ValidationUtils.isValidTagString(tagString);
		return this.checkValueAgainstTagString(tagString);
	}

	public boolean hasValue(T tag) {
		return this.checkValueAgainstTagString(tag.getTag());
	}

	private Serializable getValueFromTagString(String tagString) {
		ValidationUtils.notNull(tagString);
		Serializable value = this.map.get(tagString);
		return value;
	}

	public Serializable getValue(String tagString) throws Exception {
		ValidationUtils.isValidTagString(tagString);
		return this.getValueFromTagString(tagString);
	}

	public Serializable getValue(T tag) {
		return this.getValueFromTagString(tag.getTag());
	}

	public String getStringValue(String tagString) throws Exception {
		Serializable value = this.getValue(tagString);
		return value == null ? null : value.toString();
	}

	public String getStringValue(T tag) {
		Serializable value = this.getValue(tag);
		return value == null ? null : value.toString();
	}

	private AbstractDataModel setValueWithTagString(String tagString, Serializable value) {
		ValidationUtils.notNull(tagString);
		this.map.put(tagString, value);
		return this;
	}

	public AbstractDataModel setValue(String tagString, Serializable value) throws Exception {
		ValidationUtils.isValidTagString(tagString);
		return this.setValueWithTagString(tagString, value);
	}

	public AbstractDataModel setValue(T tag, Serializable value) {
		this.setValueWithTagString(tag.getTag(), value);
		return this;
	}

	public AbstractDataModel setValueInTagRange(Serializable value, int low, int high) throws Exception {
		for (int i = low; i < high + 1; ++i) {
			String currentTag = String.valueOf(i);
			if (!this.hasValue(currentTag)) {
				this.setValueWithTagString(currentTag, value);
				break;
			}

			if (currentTag.equals(String.valueOf(high))) {
				throw new Exception("Tag range " + low + "-" + high + " is full, cannot allocate value " + value.toString() + " to a tag");
			}
		}

		return this;
	}

	protected void removeValue(String tagString) throws Exception {
		ValidationUtils.isValidTagString(tagString);
		this.map.remove(tagString);
	}

	public void removeValue(T tag) {
		ValidationUtils.notNull(tag);
		this.map.remove(tag.getTag());
	}

	public String getRootTag() {
		return this.rootTag;
	}

	public void checkForRFU() throws Exception {
		List<String> list = new ArrayList(this.map.keySet());
		Iterator var2 = list.iterator();

		String key;
		do {
			if (!var2.hasNext()) {
				return;
			}

			key = (String) var2.next();
			Serializable value = this.map.get(key);
			if (value instanceof AbstractDataModel) {
				((AbstractDataModel) value).checkForRFU();
			}
		} while (this.RFU_Pattern == null || !this.RFU_Pattern.matcher(key).matches());

		if (this instanceof EMVQRCodeData) {
			throw new Exception("The tag  " + key + " is reserved for use and should not have a value when generating QR");
		} else {
			throw new Exception("The sub-tag " + key + " in root-tag " + this.rootTag + " is reserved for use and should not have a value when generating QR");
		}
	}

	public String toString() {
		List<String> list = new ArrayList(this.map.keySet());
		Collections.sort(list, new Comparator<String>() {
			public int compare(String o1, String o2) {
				int k1 = Integer.parseInt(o1);
				int k2 = Integer.parseInt(o2);
				return Integer.compare(k1, k2);
			}
		});
		StringBuilder sb = new StringBuilder();
		Iterator var3 = list.iterator();

		while (true) {
			String key;
			Serializable value;
			do {
				boolean print;
				do {
					do {
						do {
							if (!var3.hasNext()) {
								if (this instanceof EMVQRCodeData && this.map.get("63") != null) {
									sb.append("6304" + this.map.get("63"));
								}

								return sb.toString();
							}

							key = (String) var3.next();
						} while (this instanceof EMVQRCodeData && key.equals("63"));

						value = this.map.get(key);
						print = true;
						if (this.RFU_Pattern != null && this.RFU_Pattern.matcher(key).matches()) {
							print = false;
						}
					} while (!print);
				} while (value == null);
			} while (value.toString().length() == 0);

			String vString = String.valueOf(value);
			if (this instanceof EMVQRCodeData && (key.equals("54") || key.equals("56")) && Double.parseDouble(vString) != 0.0D) {
				while (vString.substring(0, 1).equalsIgnoreCase("0") && vString.length() > 1) {
					vString = vString.substring(1);
				}

				if (vString.substring(0, 1).equalsIgnoreCase(".")) {
					vString = '0' + vString;
				}
			}

			sb.append(key).append(String.format("%02d", vString.length())).append(vString);
		}
	}

	public void validate() throws Exception {
		ITag[] var1 = this.allTags;
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			T tag = (T) var1[var3];
			int tagString = Integer.parseInt(tag.getTag());
			Serializable value = this.map.get(tag.getTag());
			if (value == null) {
				if (tag.isMandatory()) {
					if (this instanceof EMVQRCodeData) {
						throw new Exception("Mandatory tag(s) " + tag + " is/are missing");
					}
					throw new Exception("Mandatory sub-tag(s) " + tag + " from root-tag " + this.rootTag + " are missing");
				}
			} else {
				if (value instanceof AbstractDataModel) {
					((AbstractDataModel) value).validate();
				}

				String vString = String.valueOf(value);
				Pattern pattern = tag.getPattern();
				if (pattern != null) {
					Matcher matcher = pattern.matcher(vString);
					if (!matcher.matches()) {
						if (this.rootTag != null) {
							throw new Exception("The tag " + this.rootTag + " value as " + vString + " is invalid");
						}
						throw new Exception("The tag " + tag + " value as " + vString + " is invalid");
					}
				} else if ((!(this instanceof EMVQRCodeData) || 1 >= tagString || tagString >= 52) && (vString.length() > 99 || vString.length() == 0)) {
					if (this instanceof EMVQRCodeData) {
						throw new Exception("The tag " + tag + " value as " + vString + " is invalid");
					} else {
						throw new Exception("The tag " + this.rootTag + " value as " + vString + " is invalid");
					}
				}
			}
		}

	}
}