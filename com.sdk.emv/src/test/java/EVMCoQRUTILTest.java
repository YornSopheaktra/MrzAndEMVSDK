import emv.qrcode.CurrencyUtil;
import emv.qrcode.CRC16Util;
import emv.qrcode.EMVCoQRUtil;
import emv.qrcode.EmvData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class EVMCoQRUTILTest {

    @Test
    void testGenerateEMVQR_Expected_Ok() {
        EMVCoQRUtil emvCoQRUtil = new EMVCoQRUtil();
        List<EmvData> merchantInfo = Arrays.asList(new EmvData(0, "sopheaktra_yorn@trmc")
        );
        EmvData merchantInfoTag = new EmvData().setSubEmv(29, merchantInfo);
        emvCoQRUtil.add(merchantInfoTag)
                .add(new EmvData(52, "5999"))
                .add(new EmvData(53, "116"))
                .add(new EmvData(54, "4000.0"))
                .add(new EmvData(58, "KH"))
                .add(new EmvData(59, "Tra OG"))
                .add(new EmvData(60, "Phnom Penh"));
        String QRRawContent = emvCoQRUtil.generateEMVQR();
        Assertions.assertThat(CRC16Util.validateChecksumCRC16(QRRawContent)).isTrue();
        Assertions.assertThat(QRRawContent).isNotEmpty();
    }

    @Test
    void testEMVCoQRCode_Read_Expected_ok() throws Exception {
        EMVCoQRUtil emvCoQRUtil = new EMVCoQRUtil("00020101021229150011tra_og@trmc5204599953031165408100000.05802KH5906Tra OG6010Phnom Penh63048295");
        Assertions.assertThat(emvCoQRUtil.getPayloadFormatIndicator()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getPointOfInitiationMethod()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getSubTagValueByTag(29, 00)).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getMerchantCategoryCode()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getTransactionCurrency()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getCountryCode()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getMerchantName()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getMerchantCity()).isNotEmpty();
        Assertions.assertThat(emvCoQRUtil.getCRC()).isNotEmpty();
    }

    @Test
    public void TestCurrencyCode_Expected_OK(){
        CurrencyUtil currencyUtil = new CurrencyUtil("KHR");
        Assertions.assertThat(currencyUtil.getCurrencyName()).isNotEmpty();
        Assertions.assertThat(currencyUtil.getCurrencyCode()).isNotNegative();
    }
}
