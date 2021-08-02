package emv.qrcode.util;

import java.io.UnsupportedEncodingException;

public class CheckSumUtils {
    private CheckSumUtils() {
    }

    public static String generateChecksumCRC16(String data) {
        int crc = 65535;
        int polynomial = 4129;
        ValidationUtils.notNull(data);

        byte[] bytes;
        try {
            bytes = data.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var11) {
            return null;
        }

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
        ValidationUtils.notNull(data);
        boolean isValid = false;
        if (data.length() > 4) {
            String content = data.substring(0, data.length() - 4);
            String crc = data.substring(data.length() - 4).toUpperCase();
            isValid = crc.equals(generateChecksumCRC16(content));
        }

        return isValid;
    }

    public static boolean validateChecksum(String numberString) {
        ValidationUtils.notNull(numberString);
        return !ValidationUtils.isNumeric(numberString) ? false : CheckSum.validate(numberString);
    }

    public static int generateChecksum(String numberString) throws Exception{
        ValidationUtils.notNull(numberString);
        if (!ValidationUtils.isNumeric(numberString)) {
            throw new Exception("The string "+ numberString +" is not numeric");
        } else {
            return CheckSum.checkSum(numberString, true);
        }
    }
}