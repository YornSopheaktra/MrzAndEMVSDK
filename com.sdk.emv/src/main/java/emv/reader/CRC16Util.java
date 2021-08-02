package emv.reader;


import emv.qrcode.util.ValidationUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class CRC16Util {
    
    private CRC16Util() {}

    public static String generateEMV(String str) {
        String content = str.substring(0, str.length() - 4);
        byte[] contentByte = content.getBytes(StandardCharsets.UTF_8);
        int crc16 = generate(contentByte);
        return StringUtils.leftPad(Integer.toHexString(crc16).toUpperCase(), 4, '0');
    }
    
    public static int generate(final byte[] buffer) {
        int crc = 0xFFFF;
        for (int j = 0; j < buffer.length ; j++) {
            crc = ((crc  >>> 8) | (crc  << 8) )& 0xffff;
            crc ^= (buffer[j] & 0xff);//byte to int, trunc sign
            crc ^= ((crc & 0xff) >> 4);
            crc ^= (crc << 12) & 0xffff;
            crc ^= ((crc & 0xFF) << 5) & 0xffff;
        }
        crc &= 0xffff;
        return crc;
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

}
