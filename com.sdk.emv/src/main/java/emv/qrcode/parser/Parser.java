package emv.qrcode.parser;
import emv.qrcode.enums.PPTag;
import emv.qrcode.model.*;
import emv.qrcode.util.CheckSumUtils;
import emv.qrcode.util.ValidationUtils;
import org.springframework.util.Assert;

import java.io.Serializable;

public class Parser {

    private Parser() {
    }

    private static final int TL_WIDTH = 2;

    public static EMVQRCodeData parse(String rawData) throws Exception {
        EMVQRCodeData ppData = parseWithoutTagValidation(rawData);
        ppData.validate();
        return ppData;
    }

    public static EMVQRCodeData parseWithoutTagValidation(String rawData) throws Exception {
        ValidationUtils.notNull(rawData);
        if (!CheckSumUtils.validateChecksumCRC16(rawData)) {
            String crc = null;
            if (rawData.length() > 4) {
                crc = rawData.substring(rawData.length() - 4);
            }
            throw new Exception("The tag " + PPTag.TAG_63_CRC + " value as " + crc + " is invalid");
        } else {
            return parseWithoutTagValidationAndCRC(rawData);
        }
    }

    public static EMVQRCodeData parseWithoutTagValidationAndCRC(String rawData) throws Exception {
        Assert.hasText(rawData,()-> "Data cannot be null");
        int index = 0;
        EMVQRCodeData ppData;
        TLV tlv;
        for (ppData = new EMVQRCodeData(); index < rawData.length(); index = index + tlv.getValue().length() + 4) {
            tlv = readNextTLV(rawData, index);
            String tag = tlv.getTag();
            int tagvalue = Integer.parseInt(tag);
            if (tag.equals("62")) {
                AdditionalData additionalData = parseAdditionalData(tlv.getValue());
                setParsedValue(ppData, tag, additionalData);
            } else if (tag.equals("64")) {
                LanguageData languageData = parseLanguageData(tlv.getValue());
                setParsedValue(ppData, tag, languageData);
            } else if (tagvalue > 25 && tagvalue < 52) {
                MAIData maiData = parseMAIData(tag, tlv.getValue());
                setParsedValue(ppData, tag, maiData);
            } else if (tagvalue > 79 && tagvalue < 100) {
                UnrestrictedData unrestrictedData = parseUnrestrictedData(tag, tlv.getValue());
                setParsedValue(ppData, tag, unrestrictedData);
            } else {
                setParsedValue(ppData, tag, tlv.getValue());
            }
        }

        return ppData;
    }

    private static AdditionalData parseAdditionalData(String rawData) throws Exception {
        AdditionalData additionalData = new AdditionalData();
        return parseDataForSubDataModels(rawData, additionalData);
    }

    private static LanguageData parseLanguageData(String rawData) throws Exception {
        LanguageData languageData = new LanguageData();
        return parseDataForSubDataModels(rawData, languageData);
    }

    private static MAIData parseMAIData(String rootTag, String rawData) throws Exception {
        MAIData maiData = new MAIData(rootTag);
        return parseDataForSubDataModels(rawData, maiData);
    }

    private static UnrestrictedData parseUnrestrictedData(String rootTag, String rawData) throws Exception {
        UnrestrictedData unrestrictedData = new UnrestrictedData(rootTag);
        return parseDataForSubDataModels(rawData, unrestrictedData);
    }

    private static <A extends AbstractDataModel> A parseDataForSubDataModels(String rawData, A dataModel) throws Exception {
        TLV tlv;
        for (int index = 0; index < rawData.length(); index = index + tlv.getValue().length() + 4) {
            tlv = readNextTLV(rawData, index);
            String tag = tlv.getTag();
            int tagNumber = Integer.parseInt(tag);
            if (dataModel instanceof AdditionalData && tagNumber > 49 && tagNumber < 100) {
                UnrestrictedData unrestrictedData = parseUnrestrictedData(tag + " (in main TAG_62_ADDITIONAL_DATA)", tlv.getValue());
                setParsedValue(dataModel, tag, unrestrictedData);
            } else {
                setParsedValue(dataModel, tag, tlv.getValue());
            }
        }

        return dataModel;
    }

    private static TLV readNextTLV(String rawData, int start) throws Exception {
        String substring = readSubstring((String) null, rawData, start, start + TL_WIDTH);
        String tagString = ValidationUtils.isValidTagString(substring);
        int index = start + TL_WIDTH;
        substring = readSubstring(tagString, rawData, index, index + TL_WIDTH);
        if (!ValidationUtils.isNumeric(substring)) {
            throw new Exception("Length field is not numeric start index " + index);
        } else {
            int length = Integer.parseInt(substring);
            index += TL_WIDTH;
            substring = readSubstring(tagString, rawData, index, index + length);
            return new TLV(tagString, length, substring);
        }
    }

    private static String readSubstring(String tagString, String string, int start, int end) throws Exception {
        if (string.length() < end) {
            if (tagString != null) {
                throw new Exception("Cannot read enough characters for tag " + tagString);
            } else {
                throw new Exception("Cannot read enough characters for next tag");
            }
        } else {
            return string.substring(start, end);
        }
    }

    private static <A extends AbstractDataModel> A setParsedValue(A dataModel, String tagString, Serializable value) throws Exception {
        if (dataModel.hasValue(tagString)) {
            if (dataModel.getRootTag() != null) {
                throw new Exception("This sub-tag " + tagString + " at root-tag " + dataModel.getRootTag() + " has a duplicate, with values " + value + " and " + dataModel.getValue(tagString));
            } else {
                throw new Exception("This tag " + tagString + " has a duplicate, with values of " + value + " and " + dataModel.getValue(tagString));
            }
        } else {
            dataModel.setValue(tagString, value);
            return dataModel;
        }
    }
}