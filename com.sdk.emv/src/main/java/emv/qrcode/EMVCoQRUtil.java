package emv.qrcode;

import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
public class EMVCoQRUtil extends EMVCoQRParser {

    public EMVCoQRUtil(String emvCoQrValue) throws Exception {
        super(emvCoQrValue);
    }

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

