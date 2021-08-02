package emv.qrcode.enums;

import emv.qrcode.util.ITagEnumUtil;

import java.util.regex.Pattern;

public enum TemplateTag implements ITag {
	TAG_00_GLOBALLY_UNIQUE_IDENTIFIER("00", "^[a-zA-Z0-9!@#$&()\\\\-`.+,/\\\"]{1,32}$", true);

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

	TemplateTag(String tag, String regex, boolean isMandatory) {
		this.tag = tag;
		this.isMandatory = isMandatory;
		if (regex != null) {
			this.pattern = Pattern.compile(regex);
		}
	}

	public static TemplateTag getTag(String tag) throws Exception {
		return ITagEnumUtil.getTag(tag, TemplateTag.class);
	}
}
