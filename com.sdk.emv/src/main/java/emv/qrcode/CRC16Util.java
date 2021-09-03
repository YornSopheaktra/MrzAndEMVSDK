package emv.qrcode;

import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;

@NoArgsConstructor
public class CRC16Util {
    public static String generateChecksumCRC16(String data) {
        Assert.notNull(data, "Data QR cannot be blank or null");
        int crc = 65535;
        int polynomial = 4129;
        byte[] bytes;
        bytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] var4 = bytes;
        int var5 = bytes.length;
        for(int var6 = 0; var6 < var5; ++var6) {
            byte b = var4[var6];

            for(int i = 0; i < 8; ++i) {
                boolean bit = (b >> 7 - i & 1) == 1;
                boolean c15 = (crc >> 15 & 1) == 1;
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                }
            }
        }
        crc &= 65535;
        return String.format("%04X", crc);
    }

    public static boolean validateChecksumCRC16(String data) {
        Assert.notNull(data, "Data QR cannot be blank or null");
        boolean isValid = false;
        if (data.length() > 4) {
            String content = data.substring(0, data.length() - 4);
            String crc = data.substring(data.length() - 4).toUpperCase();
            isValid = crc.equals(generateChecksumCRC16(content));
        }
        return isValid;
    }
}
