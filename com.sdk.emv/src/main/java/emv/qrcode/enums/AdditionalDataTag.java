package emv.qrcode.enums;

import emv.qrcode.util.ITagEnumUtil;

import java.util.regex.Pattern;

public enum AdditionalDataTag implements ITag {
    TAG_01_BILL_NUMBER("01", "^.{1,26}$", false),
    TAG_02_MOBILE_NUMBER("02", "^.{1,26}$", false),
    TAG_03_STORE_ID("03", "^.{1,26}$", false),
    TAG_04_LOYALTY_NUMBER("04", "^.{1,26}$", false),
    TAG_05_REFERENCE_ID("05", "^.{1,26}$", false),
    TAG_06_CONSUMER_ID("06", "^.{1,26}$", false),
    TAG_07_TERMINAL_ID("07", "^.{1,26}$", false),
    TAG_08_PURPOSE("08", "^.{1,26}$", false),
    TAG_09_ADDITIONAL_CONSUMER_DATA_REQUEST("09", "(?i)^(?!.?(.).?\\1)[AEM]?[AEM][AEM]?$", false);

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

    AdditionalDataTag(String tag, String regex, boolean isMandatory) {
        this.tag = tag;
        this.isMandatory = isMandatory;
        if (regex != null) {
            this.pattern = Pattern.compile(regex);
        }

    }

    public static AdditionalDataTag getTag(String tag) throws Exception {
        return ITagEnumUtil.getTag(tag, AdditionalDataTag.class);
    }
}
