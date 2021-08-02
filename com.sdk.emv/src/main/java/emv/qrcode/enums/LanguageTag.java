package emv.qrcode.enums;

import emv.qrcode.util.ITagEnumUtil;

import java.util.regex.Pattern;

public enum LanguageTag implements ITag {
	TAG_00_LANGUAGE_PREFERENCE("00", "^[a-zA-Z]{2}$", true),
	TAG_01_ALTERNATE_MERCHANT_NAME("01", "^.{1,25}$", true),
	TAG_02_ALTERNATE_MERCHANT_CITY("02", "^.{1,15}$", false);

	private String tag;
	private Pattern pattern = null;
	private boolean isMandatory;

	public String getTag() {
		return this.tag;
	}

	public Pattern getPattern() {
		return this.pattern;
	}

	public boolean isMandatory() {
		return this.isMandatory;
	}

	LanguageTag(String tag, String regex, boolean isMandatory) {
		this.tag = tag;
		this.isMandatory = isMandatory;
		if (regex != null) {
			this.pattern = Pattern.compile(regex);
		}
	}

	public static LanguageTag getTag(String tag) throws Exception {
		return ITagEnumUtil.getTag(tag, LanguageTag.class);
	}
}
