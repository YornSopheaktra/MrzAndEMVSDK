
import emv.qrcode.enums.CurrencyEnum;
import emv.qrcode.enums.PPTag;
import emv.qrcode.model.EMVQRCodeData;
import emv.qrcode.model.MAIData;
import emv.qrcode.parser.Parser;
import emv.reader.EMVCoQRUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EVMCoQRUTILTest {

    private EMVCoQRUtil emvCoQRUtil;

    public EVMCoQRUTILTest() throws Exception {
        String STATIC_EMV_QR_CODE = "00020101021129230019night_man_1990@trmc5204599953031165802KH5909Night Man6010Phnom Penh630415FF";
        emvCoQRUtil = new EMVCoQRUtil(STATIC_EMV_QR_CODE);
    }

    @Test
    void testEMVCoQRCode() {
        Assertions.assertThat(emvCoQRUtil.getPayloadFormatIndicator()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getPointOfInitiationMethod()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getSubTagValueByTag(29, 00)).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getMerchantCategoryCode()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getTransactionCurrency()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getCountryCode()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getMerchantName()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getMerchantCity()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getCRC()).isNotEmpty();
        System.out.println(emvCoQRUtil);
    }


    @Test
    void TestGenerateAndParseMVCoQR() throws Exception {
        EMVQRCodeData emvQrData = new EMVQRCodeData();
        emvQrData.setCountryCode("KH");
        emvQrData.setMerchantCity("KP");
        emvQrData.setMerchantCategoryCode("5999");
        emvQrData.setTransactionCurrencyCode(CurrencyEnum.USD.getCurrencyCode());
        emvQrData.setMerchantName("Tra OG");
        MAIData maiData = new MAIData(PPTag.TAG_29_MAI_TEMPLATE.getTag());
        maiData.setAID("tra_og@trmc");
        emvQrData.setDynamicMAIDTag(maiData);
        String rawData = emvQrData.generateEMVQRString();
        System.out.println(rawData);
        EMVQRCodeData emvqrCodeData = Parser.parse(rawData);
        List<MAIData> maiDataList = emvqrCodeData.getMAIData();
        Assertions.assertThat(maiDataList).isNotEmpty();
    }

}
