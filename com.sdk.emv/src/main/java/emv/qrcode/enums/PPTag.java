package emv.qrcode.enums;

import emv.qrcode.util.ITagEnumUtil;

import java.util.regex.Pattern;

public enum PPTag implements ITag {
	TAG_00_PAYLOAD_FORMAT_INDICATOR("00", "^\\d{2}$", true),
	TAG_01_POINT_INITIATION_METHOD("01", "^\\d{2}$", false),
	TAG_02_MERCHANT_IDENTIFIER_VISA("02", null, false),
	TAG_03_MERCHANT_IDENTIFIER_VISA("03", null, false),
	TAG_04_MERCHANT_IDENTIFIER_MASTERCARD("04", "^\\d+$", false),
	TAG_05_MERCHANT_IDENTIFIER_MASTERCARD("05", "^\\d+$", false),
	TAG_06_MERCHANT_IDENTIFIER_NPCI("06", null, false),
	TAG_07_MERCHANT_IDENTIFIER_NPCI("07", null, false),
	TAG_08_MERCHANT_IDENTIFIER_IFSCODE("08", null, false),
	TAG_09_MERCHANT_IDENTIFIER_DISCOVER("09", null, false),
	TAG_10_MERCHANT_IDENTIFIER_DISCOVER("10", null, false),
	TAG_11_MERCHANT_IDENTIFIER_AMEX("11", null, false),
	TAG_12_MERCHANT_IDENTIFIER_AMEX("12", null, false),
	TAG_13_MERCHANT_IDENTIFIER_JCB("13", null, false),
	TAG_14_MERCHANT_IDENTIFIER_JCB("14", null, false),
	TAG_15_MERCHANT_IDENTIFIER_UNIONPAY("15", null, false),
	TAG_16_MERCHANT_IDENTIFIER_UNIONPAY("16", null, false),
	TAG_17_MERCHANT_IDENTIFIER_EMVCO("17", null, false),
	TAG_18_MERCHANT_IDENTIFIER_EMVCO("18", null, false),
	TAG_19_MERCHANT_IDENTIFIER_EMVCO("19", null, false),
	TAG_20_MERCHANT_IDENTIFIER_EMVCO("20", null, false),
	TAG_21_MERCHANT_IDENTIFIER_EMVCO("21", null, false),
	TAG_22_MERCHANT_IDENTIFIER_EMVCO("22", null, false),
	TAG_23_MERCHANT_IDENTIFIER_EMVCO("23", null, false),
	TAG_24_MERCHANT_IDENTIFIER_EMVCO("24", null, false),
	TAG_25_MERCHANT_IDENTIFIER_EMVCO("25", null, false),
	TAG_26_MAI_TEMPLATE("26", null, false),
	TAG_27_MAI_TEMPLATE("27", null, false),
	TAG_28_MAI_TEMPLATE("28", null, false),
	TAG_29_MAI_TEMPLATE("29", null, false),
	TAG_30_MAI_TEMPLATE("30", null, false),
	TAG_31_MAI_TEMPLATE("31", null, false),
	TAG_32_MAI_TEMPLATE("32", null, false),
	TAG_33_MAI_TEMPLATE("33", null, false),
	TAG_34_MAI_TEMPLATE("34", null, false),
	TAG_35_MAI_TEMPLATE("35", null, false),
	TAG_36_MAI_TEMPLATE("36", null, false),
	TAG_37_MAI_TEMPLATE("37", null, false),
	TAG_38_MAI_TEMPLATE("38", null, false),
	TAG_39_MAI_TEMPLATE("39", null, false),
	TAG_40_MAI_TEMPLATE("40", null, false),
	TAG_41_MAI_TEMPLATE("41", null, false),
	TAG_42_MAI_TEMPLATE("42", null, false),
	TAG_43_MAI_TEMPLATE("43", null, false),
	TAG_44_MAI_TEMPLATE("44", null, false),
	TAG_45_MAI_TEMPLATE("45", null, false),
	TAG_46_MAI_TEMPLATE("46", null, false),
	TAG_47_MAI_TEMPLATE("47", null, false),
	TAG_48_MAI_TEMPLATE("48", null, false),
	TAG_49_MAI_TEMPLATE("49", null, false),
	TAG_50_MAI_TEMPLATE("50", null, false),
	TAG_51_MAI_TEMPLATE("51", null, false),
	TAG_52_MERCHANT_CATEGORY_CODE("52", "^\\d{4}$", true),
	TAG_53_TRANSACTION_CURRENCY_CODE("53", "^\\d{3}$", true),
	TAG_54_TRANSACTION_AMOUNT("54", "^(?!.{14,})((\\d+)(\\.\\d*)?)$", false),
	TAG_55_TIP_INDICATOR("55", "^(01|02|03)$", false),
	TAG_56_CONVENIENCE_FEE_FIXED("56", "^(?!.{14,})(\\d+(\\.\\d*)?)$", false),
	TAG_57_CONVENIENCE_FEE_PERCENTAGE("57", "^(?!.{6,})0*(\\d{1,2}(\\.\\d*)?|100)$", false),
	TAG_58_COUNTRY_CODE("58", "^[a-zA-Z]{2}$", true),
	TAG_59_MERCHANT_NAME("59", "^.{1,25}$", true),
	TAG_60_MERCHANT_CITY("60", "^.{1,15}$", true),
	TAG_61_POSTAL_CODE("61", "^.{1,10}$", false),
	TAG_62_ADDITIONAL_DATA_FIELD("62", "^.{1,99}$", false),
	TAG_64_LANGUAGE("64", "^.{1,99}$", false),
	TAG_65("65", null, false),
	TAG_66("66", null, false),
	TAG_67("67", null, false),
	TAG_68("68", null, false),
	TAG_69("69", null, false),
	TAG_70("70", null, false),
	TAG_71("71", null, false),
	TAG_72("72", null, false),
	TAG_73("73", null, false),
	TAG_74("74", null, false),
	TAG_75("75", null, false),
	TAG_76("76", null, false),
	TAG_77("77", null, false),
	TAG_78("78", null, false),
	TAG_79("79", null, false),
	TAG_80_UNRESTRICTED_DATA("80", "^.{1,99}$", false),
	TAG_81_UNRESTRICTED_DATA("81", "^.{1,99}$", false),
	TAG_82_UNRESTRICTED_DATA("82", "^.{1,99}$", false),
	TAG_83_UNRESTRICTED_DATA("83", "^.{1,99}$", false),
	TAG_84_UNRESTRICTED_DATA("84", "^.{1,99}$", false),
	TAG_85_UNRESTRICTED_DATA("85", "^.{1,99}$", false),
	TAG_86_UNRESTRICTED_DATA("86", "^.{1,99}$", false),
	TAG_87_UNRESTRICTED_DATA("87", "^.{1,99}$", false),
	TAG_88_UNRESTRICTED_DATA("88", "^.{1,99}$", false),
	TAG_89_UNRESTRICTED_DATA("89", "^.{1,99}$", false),
	TAG_90_UNRESTRICTED_DATA("90", "^.{1,99}$", false),
	TAG_91_UNRESTRICTED_DATA("91", "^.{1,99}$", false),
	TAG_92_UNRESTRICTED_DATA("92", "^.{1,99}$", false),
	TAG_93_UNRESTRICTED_DATA("93", "^.{1,99}$", false),
	TAG_94_UNRESTRICTED_DATA("94", "^.{1,99}$", false),
	TAG_95_UNRESTRICTED_DATA("95", "^.{1,99}$", false),
	TAG_96_UNRESTRICTED_DATA("96", "^.{1,99}$", false),
	TAG_97_UNRESTRICTED_DATA("97", "^.{1,99}$", false),
	TAG_98_UNRESTRICTED_DATA("98", "^.{1,99}$", false),
	TAG_99_UNRESTRICTED_DATA("99", "^.{1,99}$", false),
	TAG_63_CRC("63", "^.{4}$", false);

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

	PPTag(String tag, String regex, boolean isMandatory) {
		this.tag = tag;
		this.isMandatory = isMandatory;
		if (regex != null) {
			this.pattern = Pattern.compile(regex);
		}
	}

	public static PPTag getTag(String tag) throws Exception {
		return ITagEnumUtil.getTag(tag, PPTag.class);
	}
}