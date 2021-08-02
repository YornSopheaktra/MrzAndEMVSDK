package emv.qrcode.model;

import emv.qrcode.enums.CurrencyEnum;
import emv.qrcode.enums.PPTag;
import emv.qrcode.util.CheckSumUtils;
import emv.qrcode.util.ISODecimalFractionDigit;
import emv.qrcode.util.ValidationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public final class EMVQRCodeData extends AbstractDataModel<PPTag> {
	private static final int MerchantIdentifierEMVCoLower = 17;
	private static final int MerchantIdentifierEMVCoUpper = 25;
	private static final int MAILower = 26;
	private static final int MAIUpper = 51;
	private static final int UnrestrictedLower = 80;
	private static final int UnrestrictedUpper = 99;


	private List<String> countries = Arrays.asList(Locale.getISOCountries());

	public EMVQRCodeData() {
		super(PPTag.class, "^(6[5-9]|7[0-9])$");
	}

	public void validate() throws Exception {
		super.validate();
		this.validateMerchantIdentifiers();
		this.validateTipFields();
		this.validatePriceField();
		this.isValidCountry(this.getCountryCode());
	}

	public EMVQRCodeData setValue(String tagString, Serializable value) throws Exception {
		super.setValue(tagString, value);
		return this;
	}

	public EMVQRCodeData setDynamicMAIDTag(MAIData value) throws Exception {
		this.setValueInTagRange(value, MAILower, MAIUpper);
		return this;
	}

	public EMVQRCodeData setDynamicUnrestrictedTag(UnrestrictedData value) throws Exception {
		this.setValueInTagRange(value, UnrestrictedLower, UnrestrictedUpper);
		return this;
	}

	public String generateEMVQRString() throws Exception {
		super.checkForRFU();
		this.generatePayLoadFormatIndicator();
		this.generatePointOfInitiationMethod();
		this.validate();
		this.validatePriceDecimals();
		if (this.hasValue(PPTag.TAG_62_ADDITIONAL_DATA_FIELD)) {
			this.getAdditionalData().validateDataForGeneration();
		}

		this.removeValue(PPTag.TAG_63_CRC);
		String content = this.toString() + "6304";
		String checksum = CheckSumUtils.generateChecksumCRC16(content);
		this.setValue(PPTag.TAG_63_CRC, checksum);
		return content + checksum;
	}

	public String getValueWittChecksum(PPTag tag) throws Exception {
		Serializable value = this.getValue(tag);
		if (value != null) {
			String content = String.valueOf(value);
			int checksum = CheckSumUtils.generateChecksum(content);
			return content + checksum;
		} else {
			return null;
		}
	}

	public String getPayloadFormatIndicator() {
		return this.getStringValue(PPTag.TAG_00_PAYLOAD_FORMAT_INDICATOR);
	}

	public EMVQRCodeData setPayloadFormatIndicator(String indicator) {
		this.setValue(PPTag.TAG_00_PAYLOAD_FORMAT_INDICATOR, indicator);
		return this;
	}

	public String getPointOfInitiationMethod() {
		return this.getStringValue(PPTag.TAG_01_POINT_INITIATION_METHOD);
	}

	private EMVQRCodeData generatePointOfInitiationMethod() {
		if (!this.hasValue(PPTag.TAG_01_POINT_INITIATION_METHOD) || !String.valueOf(this.getValue(PPTag.TAG_01_POINT_INITIATION_METHOD)).equalsIgnoreCase("11") && !String.valueOf(this.getValue(PPTag.TAG_01_POINT_INITIATION_METHOD)).equalsIgnoreCase("12")) {
			if (this.hasValue(PPTag.TAG_54_TRANSACTION_AMOUNT)) {
				this.setValue(PPTag.TAG_01_POINT_INITIATION_METHOD, "12");
			} else {
				this.setValue(PPTag.TAG_01_POINT_INITIATION_METHOD, "11");
			}
		}

		return this;
	}

	private EMVQRCodeData generatePayLoadFormatIndicator() {
		if (!this.hasValue(PPTag.TAG_00_PAYLOAD_FORMAT_INDICATOR)) {
			this.setValue(PPTag.TAG_00_PAYLOAD_FORMAT_INDICATOR, "01");
		}
		return this;
	}

	public String getMerchantIdentifierVisa02() {
		return this.getStringValue(PPTag.TAG_02_MERCHANT_IDENTIFIER_VISA);
	}

	public EMVQRCodeData setMerchantIdentifierVisa02(String identifier) {
		this.setValue(PPTag.TAG_02_MERCHANT_IDENTIFIER_VISA, identifier);
		return this;
	}

	public String getMerchantIdentifierVisa03() {
		return this.getStringValue(PPTag.TAG_03_MERCHANT_IDENTIFIER_VISA);
	}

	public EMVQRCodeData setMerchantIdentifierVisa03(String identifier) {
		this.setValue(PPTag.TAG_03_MERCHANT_IDENTIFIER_VISA, identifier);
		return this;
	}

	public String getMerchantIdentifierMastercard04() {
		return this.getStringValue(PPTag.TAG_04_MERCHANT_IDENTIFIER_MASTERCARD);
	}

	public EMVQRCodeData setMerchantIdentifierMastercard04(String identifier) {
		this.setValue(PPTag.TAG_04_MERCHANT_IDENTIFIER_MASTERCARD, identifier);
		return this;
	}

	public String getMerchantIdentifierMastercard05() {
		return this.getStringValue(PPTag.TAG_05_MERCHANT_IDENTIFIER_MASTERCARD);
	}

	public EMVQRCodeData setMerchantIdentifierMastercard05(String identifier) {
		this.setValue(PPTag.TAG_05_MERCHANT_IDENTIFIER_MASTERCARD, identifier);
		return this;
	}

	public String getMerchantIdentifierNPCI06() {
		return this.getStringValue(PPTag.TAG_06_MERCHANT_IDENTIFIER_NPCI);
	}

	public EMVQRCodeData setMerchantIdentifierNPCI06(String identifier) {
		this.setValue(PPTag.TAG_06_MERCHANT_IDENTIFIER_NPCI, identifier);
		return this;
	}

	public String getMerchantIdentifierNPCI07() {
		return this.getStringValue(PPTag.TAG_07_MERCHANT_IDENTIFIER_NPCI);
	}

	public EMVQRCodeData setMerchantIdentifierNPCI07(String identifier) {
		this.setValue(PPTag.TAG_07_MERCHANT_IDENTIFIER_NPCI, identifier);
		return this;
	}

	public String getMerchantIdentifierDiscover08() {
		return this.getStringValue(PPTag.TAG_08_MERCHANT_IDENTIFIER_IFSCODE);
	}

	public EMVQRCodeData setMerchantIdentifierDiscover08(String identifier) {
		this.setValue(PPTag.TAG_08_MERCHANT_IDENTIFIER_IFSCODE, identifier);
		return this;
	}

	public String getMerchantIdentifierDiscover09() {
		return this.getStringValue(PPTag.TAG_09_MERCHANT_IDENTIFIER_DISCOVER);
	}

	public EMVQRCodeData setMerchantIdentifierDiscover09(String identifier) {
		this.setValue(PPTag.TAG_09_MERCHANT_IDENTIFIER_DISCOVER, identifier);
		return this;
	}

	public String getMerchantIdentifierDiscover10() {
		return this.getStringValue(PPTag.TAG_10_MERCHANT_IDENTIFIER_DISCOVER);
	}

	public EMVQRCodeData setMerchantIdentifierDiscover10(String identifier) {
		this.setValue(PPTag.TAG_10_MERCHANT_IDENTIFIER_DISCOVER, identifier);
		return this;
	}

	public String getMerchantIdentifierAmex11() {
		return this.getStringValue(PPTag.TAG_11_MERCHANT_IDENTIFIER_AMEX);
	}

	public EMVQRCodeData setMerchantIdentifierAmex11(String identifier) {
		this.setValue(PPTag.TAG_11_MERCHANT_IDENTIFIER_AMEX, identifier);
		return this;
	}

	public String getMerchantIdentifierAmex12() {
		return this.getStringValue(PPTag.TAG_12_MERCHANT_IDENTIFIER_AMEX);
	}

	public EMVQRCodeData setMerchantIdentifierAmex12(String identifier) {
		this.setValue(PPTag.TAG_12_MERCHANT_IDENTIFIER_AMEX, identifier);
		return this;
	}

	public String getMerchantIdentifierJCB13() {
		return this.getStringValue(PPTag.TAG_13_MERCHANT_IDENTIFIER_JCB);
	}

	public EMVQRCodeData setMerchantIdentifierJCB13(String identifier) {
		this.setValue(PPTag.TAG_13_MERCHANT_IDENTIFIER_JCB, identifier);
		return this;
	}

	public String getMerchantIdentifierJCB14() {
		return this.getStringValue(PPTag.TAG_14_MERCHANT_IDENTIFIER_JCB);
	}

	public EMVQRCodeData setMerchantIdentifierJCB14(String identifier) {
		this.setValue(PPTag.TAG_14_MERCHANT_IDENTIFIER_JCB, identifier);
		return this;
	}

	public String getMerchantIdentifierUnionPay15() {
		return this.getStringValue(PPTag.TAG_15_MERCHANT_IDENTIFIER_UNIONPAY);
	}

	public EMVQRCodeData setMerchantIdentifierUnionPay15(String identifier) {
		this.setValue(PPTag.TAG_15_MERCHANT_IDENTIFIER_UNIONPAY, identifier);
		return this;
	}

	public String getMerchantIdentifierUnionPay16() {
		return this.getStringValue(PPTag.TAG_16_MERCHANT_IDENTIFIER_UNIONPAY);
	}

	public EMVQRCodeData setMerchantIdentifierUnionPay16(String identifier) {
		this.setValue(PPTag.TAG_16_MERCHANT_IDENTIFIER_UNIONPAY, identifier);
		return this;
	}

	public String getMerchantIdentifierEMVCo(String tagString) throws Exception {
		if (ValidationUtils.isValidTagStringWithinRange(tagString, MerchantIdentifierEMVCoLower, MerchantIdentifierEMVCoUpper)) {
			return this.getStringValue(tagString);
		} else {
			throw new Exception("Tag for Merchant Identifier EMVCo Data should be on the range of '17' to '25'");
		}
	}

	public EMVQRCodeData setMerchantIdentifierEMVCo(String tagString, String identifier) throws Exception {
		if (ValidationUtils.isValidTagStringWithinRange(tagString, MerchantIdentifierEMVCoLower, MerchantIdentifierEMVCoUpper)) {
			this.setValue(tagString, identifier);
			return this;
		} else {
			throw new Exception("Tag for Merchant Identifier EMVCo Data should be on the range of '17' to '25'");
		}
	}

	public String getMerchantCategoryCode() {
		return this.getStringValue(PPTag.TAG_52_MERCHANT_CATEGORY_CODE);
	}

	public EMVQRCodeData setMerchantCategoryCode(String categoryCode) {
		this.setValue(PPTag.TAG_52_MERCHANT_CATEGORY_CODE, categoryCode);
		return this;
	}

	public String getTransactionCurrencyCode() {
		return this.getStringValue(PPTag.TAG_53_TRANSACTION_CURRENCY_CODE);
	}

	public EMVQRCodeData setTransactionCurrencyCode(String currencyCode) {
		this.setValue(PPTag.TAG_53_TRANSACTION_CURRENCY_CODE, currencyCode);
		return this;
	}

	public Double getTransactionAmount() {
		String amountString = this.getStringValue(PPTag.TAG_54_TRANSACTION_AMOUNT);
		return amountString == null ? null : Double.valueOf(amountString);
	}

	public EMVQRCodeData setTransactionAmount(double amount) {
		String amountString = String.valueOf(amount);
		this.setValue(PPTag.TAG_54_TRANSACTION_AMOUNT, amountString);
		this.generatePointOfInitiationMethod();
		return this;
	}

	public String getTipOrConvenienceIndicator() {
		return this.getStringValue(PPTag.TAG_55_TIP_INDICATOR);
	}

	public EMVQRCodeData setTipOrConvenienceIndicator(String indicator) {
		this.setValue(PPTag.TAG_55_TIP_INDICATOR, indicator);
		return this;
	}

	public Double getValueOfConvenienceFeeFixed() {
		String amountString = this.getStringValue(PPTag.TAG_56_CONVENIENCE_FEE_FIXED);
		return amountString == null ? null : Double.valueOf(amountString);
	}

	public EMVQRCodeData setValueOfConvenienceFeeFixed(double fee) {
		String feeString = String.valueOf(fee);
		this.setValue(PPTag.TAG_56_CONVENIENCE_FEE_FIXED, feeString);
		return this;
	}

	public Double getValueOfConvenienceFeePercentage() {
		String amountString = this.getStringValue(PPTag.TAG_57_CONVENIENCE_FEE_PERCENTAGE);
		return amountString == null ? null : Double.valueOf(amountString);
	}

	public EMVQRCodeData setValueOfConvenienceFeePercentage(double percentage) {
		String percentageString = String.valueOf(percentage);
		this.setValue(PPTag.TAG_57_CONVENIENCE_FEE_PERCENTAGE, percentageString);
		return this;
	}

	public String getCountryCode() {
		return this.getStringValue(PPTag.TAG_58_COUNTRY_CODE);
	}

	public EMVQRCodeData setCountryCode(String countryCode) {
		this.setValue(PPTag.TAG_58_COUNTRY_CODE, countryCode);
		return this;
	}

	public String getMerchantName() {
		return this.getStringValue(PPTag.TAG_59_MERCHANT_NAME);
	}

	public EMVQRCodeData setMerchantName(String merchantName) {
		this.setValue(PPTag.TAG_59_MERCHANT_NAME, merchantName);
		return this;
	}

	public String getMerchantCity() {
		return this.getStringValue(PPTag.TAG_60_MERCHANT_CITY);
	}

	public EMVQRCodeData setMerchantCity(String merchantCity) {
		this.setValue(PPTag.TAG_60_MERCHANT_CITY, merchantCity);
		return this;
	}

	public String getPostalCode() {
		return this.getStringValue(PPTag.TAG_61_POSTAL_CODE);
	}

	public EMVQRCodeData setPostalCode(String postalCode) {
		this.setValue(PPTag.TAG_61_POSTAL_CODE, postalCode);
		return this;
	}

	public AdditionalData getAdditionalData() {
		Serializable data = this.getValue(PPTag.TAG_62_ADDITIONAL_DATA_FIELD);
		return data == null ? null : (AdditionalData) data;
	}

	public EMVQRCodeData setAdditionalData(AdditionalData additionalData) {
		this.setValue(PPTag.TAG_62_ADDITIONAL_DATA_FIELD, additionalData);
		return this;
	}

	public LanguageData getLanguageData() {
		Serializable data = this.getValue(PPTag.TAG_64_LANGUAGE);
		return data == null ? null : (LanguageData) data;
	}

	public EMVQRCodeData setLanguageData(LanguageData languageData) {
		this.setValue(PPTag.TAG_64_LANGUAGE, languageData);
		return this;
	}

	public MAIData getMAIData(String tagString) throws Exception {
		Serializable data = null;
		if (ValidationUtils.isValidTagStringWithinRange(tagString, MAILower, MAIUpper)) {
			data = this.getValue(tagString);
			return data == null ? null : (MAIData) data;
		} else {
			throw new Exception("The tag " + tagString + " value as 'MAIData' is invalid, Merchant account information that are inserted in dynamically allocable tags should appear in Tag '26' to '51'");
		}
	}

	public List<MAIData> getMAIData() throws Exception {
		List<MAIData> data = new ArrayList<>();

		for (int i = MAILower; i < MAIUpper + 1; ++i) {
			String currentTag = String.valueOf(i);
			if (this.hasValue(currentTag)) {
				Serializable value = this.getValue(currentTag);
				if (value != null) {
					data.add((MAIData) value);
				}
			}
		}
		return data;
	}

	public EMVQRCodeData setMAIData(String tagString, MAIData maiData) throws Exception {
		if (ValidationUtils.isValidTagStringWithinRange(tagString, MAILower, MAIUpper)) {
			this.setValue(tagString, maiData);
			return this;
		} else {
			throw new Exception("The tag " + tagString + " value as 'MAIData' is invalid, Tag for MAI Data should be on the range of '26' to '51'");
		}
	}

	public UnrestrictedData getUnrestrictedData(String tagString) throws Exception {
		if (ValidationUtils.isValidTagStringWithinRange(tagString, UnrestrictedLower, UnrestrictedUpper)) {
			Serializable data = this.getValue(tagString);
			return data == null ? null : (UnrestrictedData) data;
		} else {
			throw new Exception("The tag " + tagString + " value as 'UnrestrictedData' is invalid, Tag for Unrestricted Data should be on the range of '80' to '99'");
		}
	}

	public EMVQRCodeData setUnrestrictedData(String tagString, UnrestrictedData unrestrictedData) throws Exception {
		if (ValidationUtils.isValidTagStringWithinRange(tagString, UnrestrictedLower, UnrestrictedUpper)) {
			this.setValue(tagString, unrestrictedData);
			return this;
		} else {
			throw new Exception("The tag " + tagString + " value as 'UnrestrictedData' is invalid, Tag for Unrestricted Data should be on the range of '80' to '99'");
		}
	}

	public String getCRC() {
		return this.getStringValue(PPTag.TAG_63_CRC);
	}

	public boolean isDynamic() {
		String poi = this.getPointOfInitiationMethod();
		boolean isDynamic = false;
		if (poi != null && poi.endsWith("2")) {
			isDynamic = true;
		}

		return isDynamic;
	}

	private void validateMerchantIdentifiers() throws Exception {
		PPTag[] allTags = PPTag.values();
		boolean foundValue = false;
		for (int i = 2; i < 52 && !foundValue; ++i) {
			PPTag tag = allTags[i];
			Serializable value = this.getValue(tag);
			foundValue = value != null && value.toString().length() != 0;

		}

		if (!foundValue) {
			throw new Exception("At least one merchant identifier must be inserted");
		}
	}

	private void validateTipFields() throws Exception {
		if (this.hasValue(PPTag.TAG_55_TIP_INDICATOR)) {
			Serializable indicator = this.getValue(PPTag.TAG_55_TIP_INDICATOR);
			if ("01".equals(indicator)) {
				if (this.hasValue(PPTag.TAG_56_CONVENIENCE_FEE_FIXED)) {
					throw new Exception("String with tip convenience indicator value '01' should not contain tag '56' or '57'");
				}

				if (this.hasValue(PPTag.TAG_57_CONVENIENCE_FEE_PERCENTAGE)) {
					throw new Exception("String with tip convenience indicator value '01' should not contain tag '56' or '57'");
				}
			} else if ("02".equals(indicator)) {
				if (!this.hasValue(PPTag.TAG_56_CONVENIENCE_FEE_FIXED)) {
					throw new Exception( "Mandatory tag(s) " + PPTag.TAG_56_CONVENIENCE_FEE_FIXED + " is missing, String with tip convenience indicator value '02' must contain tag '56'");
				}

				if (this.hasValue(PPTag.TAG_57_CONVENIENCE_FEE_PERCENTAGE)) {
					throw new Exception("String with tip convenience indicator value '02' should not contain tag '57'");
				}
			} else if ("03".equals(indicator)) {
				if (!this.hasValue(PPTag.TAG_57_CONVENIENCE_FEE_PERCENTAGE)) {
					throw new Exception("String with tip convenience indicator value '03' must contain tag '57'");
				}

				if (this.hasValue(PPTag.TAG_56_CONVENIENCE_FEE_FIXED)) {
					throw new Exception("String with tip convenience indicator value '03' should not contain tag '56'");
				}
			}
		}

	}

	private void validatePriceField() {
	}

	private void validatePriceDecimals() throws Exception {
		String alphaCurrencyCode = null;
		String numericCode = this.getTransactionCurrencyCode();
		String not_zero_regex = "^[0.]*$";
		Pattern not_zero_pattern = Pattern.compile(not_zero_regex);
		CurrencyEnum[] var5 = CurrencyEnum.class.getEnumConstants();
		int var6 = var5.length;

		int decimalPlaces;
		for (decimalPlaces = 0; decimalPlaces < var6; ++decimalPlaces) {
			CurrencyEnum c = var5[decimalPlaces];
			if (c.getCurrencyCode().equals(numericCode)) {
				alphaCurrencyCode = c.getAlphaCode();
				break;
			}
		}

		if (alphaCurrencyCode == null) {
			throw new Exception("The tag " + PPTag.TAG_53_TRANSACTION_CURRENCY_CODE + " value as " + numericCode + " is invalid");
		} else {
			ISODecimalFractionDigit ISODecimalFractionDigit = new ISODecimalFractionDigit();
			decimalPlaces = ISODecimalFractionDigit.getISODecimalFractionDigits(alphaCurrencyCode);
			String regex = "^\\d+(\\.\\d{0," + decimalPlaces + "}[0]*)?$";
			Pattern pattern = Pattern.compile(regex);
			String value;
			if (this.hasValue(PPTag.TAG_54_TRANSACTION_AMOUNT)) {
				value = this.getTransactionAmount().toString();
				if (!pattern.matcher(value).matches()) {
					throw new Exception("The tag " + PPTag.TAG_54_TRANSACTION_AMOUNT + " value as " + value + " is invalid");
				}
			}

			if (this.hasValue(PPTag.TAG_56_CONVENIENCE_FEE_FIXED)) {
				value = String.valueOf(this.getValueOfConvenienceFeeFixed());
				if (not_zero_pattern.matcher(value).matches()) {
					throw new Exception("The tag " + PPTag.TAG_56_CONVENIENCE_FEE_FIXED + " value as " + value + " is invalid");
				}

				if (!pattern.matcher(value).matches()) {
					throw new Exception("The tag " + PPTag.TAG_56_CONVENIENCE_FEE_FIXED + " value as " + value + " is invalid");
				}
			} else if (this.hasValue(PPTag.TAG_57_CONVENIENCE_FEE_PERCENTAGE)) {
				Double percentageValue = this.getValueOfConvenienceFeePercentage();
				if (percentageValue <= 0.0D || percentageValue >= 100.0D) {
					throw new Exception("The tag " + PPTag.TAG_57_CONVENIENCE_FEE_PERCENTAGE + " value as " + percentageValue + " is invalid");
				}
			}
		}
	}

	private String isValidCountry(String value) throws Exception {
		if (!this.countries.contains(value.toUpperCase())) {
			throw new Exception("The tag " + PPTag.TAG_58_COUNTRY_CODE + " value as " + value + " is invalid");
		} else {
			return value;
		}
	}
}
