package emv.reader;
import java.util.Optional;

public class EMVCoQRUtil extends EMVCoQRParser {

    public EMVCoQRUtil(String emvCoQrValue) throws Exception {
        super(emvCoQrValue);
    }

    private static final Integer PAYLOAD_FORMAT_INDICATOR = 00;
    private static final Integer POINT_OF_INITIATION_METHOD = 01;
    private static final Integer GLOBALLY_UNIQUE_IDENTIFIER = 00;
    private static final Integer MERCHANT_CATEGORY_CODE = 52;
    private static final Integer TRANSACTION_CURRENCY = 53;
    private static final Integer COUNTRY_CODE = 58;
    private static final Integer MERCHANT_NAME = 59;
    private static final Integer MERCHANT_CITY = 60;
    private static final Integer CRC = 63;

    public Optional<String> getPayloadFormatIndicator() {
        return getTagValue(PAYLOAD_FORMAT_INDICATOR);
    }

    public Optional<String> getPointOfInitiationMethod() {
        return getTagValue(POINT_OF_INITIATION_METHOD);
    }

    public Optional<String> getMerchantCategoryCode() {
        return getTagValue(MERCHANT_CATEGORY_CODE);
    }

    public Optional<String> getTransactionCurrency() {
        return getTagValue(TRANSACTION_CURRENCY);
    }

    public Optional<String> getCountryCode() {
        return getTagValue(COUNTRY_CODE);
    }

    public Optional<String> getMerchantName() {
        return getTagValue(MERCHANT_NAME);
    }

    public Optional<String> getMerchantCity() {
        return getTagValue(MERCHANT_CITY);
    }

    public Optional<String> getCRC() {
        return getTagValue(CRC);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

