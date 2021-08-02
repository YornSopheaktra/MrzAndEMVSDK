package emv.qrcode.model;

import emv.qrcode.enums.AdditionalDataTag;
import emv.qrcode.util.ValidationUtils;

import java.io.Serializable;

public final class AdditionalData extends AbstractDataModel<AdditionalDataTag> {
    private static final int UnrestrictedLower = 50;
    private static final int UnrestrictedUpper = 99;

    public AdditionalData() {
        super(AdditionalDataTag.class, "", "62");
    }

    public AdditionalData setValue(String tagString, Serializable value) throws Exception {
        super.setValue(tagString, value);
        return this;
    }

    public void validate() throws Exception {
        super.validate();
        this.validateUnrestrictedData();
    }

    public AdditionalData setDynamicTag(UnrestrictedData value) throws Exception {
        this.setValueInTagRange(value, UnrestrictedLower, UnrestrictedUpper);
        return this;
    }

    public String getBillNumber() {
        return this.getStringValue(AdditionalDataTag.TAG_01_BILL_NUMBER);
    }

    public AdditionalData setBillNumber(String billNumber) {
        this.setValue(AdditionalDataTag.TAG_01_BILL_NUMBER, billNumber);
        return this;
    }

    public String getMobileNumber() {
        return this.getStringValue(AdditionalDataTag.TAG_02_MOBILE_NUMBER);
    }

    public AdditionalData setMobileNumber(String mobileNumber) {
        this.setValue(AdditionalDataTag.TAG_02_MOBILE_NUMBER, mobileNumber);
        return this;
    }

    public String getStoreId() {
        return this.getStringValue(AdditionalDataTag.TAG_03_STORE_ID);
    }

    public AdditionalData setStoreId(String storeId) {
        this.setValue(AdditionalDataTag.TAG_03_STORE_ID, storeId);
        return this;
    }

    public String getLoyaltyNumber() {
        return this.getStringValue(AdditionalDataTag.TAG_04_LOYALTY_NUMBER);
    }

    public AdditionalData setLoyaltyNumber(String loyaltyNumber) {
        this.setValue(AdditionalDataTag.TAG_04_LOYALTY_NUMBER, loyaltyNumber);
        return this;
    }

    public String getReferenceId() {
        return this.getStringValue(AdditionalDataTag.TAG_05_REFERENCE_ID);
    }

    public AdditionalData setReferenceId(String referenceId) {
        this.setValue(AdditionalDataTag.TAG_05_REFERENCE_ID, referenceId);
        return this;
    }

    public String getConsumerId() {
        return this.getStringValue(AdditionalDataTag.TAG_06_CONSUMER_ID);
    }

    public AdditionalData setConsumerId(String consumerId) {
        this.setValue(AdditionalDataTag.TAG_06_CONSUMER_ID, consumerId);
        return this;
    }

    public String getTerminalId() {
        return this.getStringValue(AdditionalDataTag.TAG_07_TERMINAL_ID);
    }

    public AdditionalData setTerminalId(String terminalId) {
        this.setValue(AdditionalDataTag.TAG_07_TERMINAL_ID, terminalId);
        return this;
    }

    public String getPurpose() {
        return this.getStringValue(AdditionalDataTag.TAG_08_PURPOSE);
    }

    public AdditionalData setPurpose(String purpose) {
        this.setValue(AdditionalDataTag.TAG_08_PURPOSE, purpose);
        return this;
    }

    public String getAdditionalDataRequest() {
        return this.getStringValue(AdditionalDataTag.TAG_09_ADDITIONAL_CONSUMER_DATA_REQUEST);
    }

    public AdditionalData setAdditionalDataRequest(String additionalDataRequest) {
        this.setValue(AdditionalDataTag.TAG_09_ADDITIONAL_CONSUMER_DATA_REQUEST, additionalDataRequest);
        return this;
    }

    public UnrestrictedData getUnrestrictedDataFromSubTag(String subtag) throws Exception {
        if (ValidationUtils.isValidTagStringWithinRange(subtag, UnrestrictedLower, UnrestrictedUpper)) {
            Serializable data = this.getValue(subtag);
            return data == null ? null : (UnrestrictedData) data;
        } else {
            throw new Exception("Tag for Unreserved Data of Additional Data should be on the range of '50' to '99'");
        }
    }

    public AdditionalData setUnrestrictedDataForSubTag(String subtag, UnrestrictedData value) throws Exception {
        if (ValidationUtils.isValidTagStringWithinRange(subtag, UnrestrictedLower, UnrestrictedUpper)) {
            this.setValue(subtag, value);
            return this;
        } else {
            throw new Exception("Tag for Unreserved Data of Additional Data should be on the range of '50' to '99'");
        }
    }

    private void validateUnrestrictedData() throws Exception {
        for (int i = 50; i < 100; ++i) {
            String tag = String.valueOf(i);
            if (this.hasValue(tag)) {
                Serializable value = this.getValue(tag);
                if (value instanceof AbstractDataModel) {
                    ((AbstractDataModel) value).validate();
                }
            }
        }

    }

    public void validateDataForGeneration() throws Exception {
        for (int i = 1; i < 49; ++i) {
            if (i != 9) {
                String tagString = String.format("%02d", i);
                if (this.hasValue(tagString) && this.getStringValue(tagString).length() > 25) {
                    throw new Exception("Values in additionalData from TAG 1-49 should not exceed length 25 for generation");
                }
            }
        }
    }
}
