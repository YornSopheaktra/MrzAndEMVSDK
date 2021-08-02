package emv.qrcode.util;

import java.util.regex.Pattern;

public class ValidationUtils {
    private ValidationUtils() {
    }

    public static <T> T notNull(T object) {
        if (object == null) {
            throw new NullPointerException();
        } else {
            return object;
        }
    }

    public static boolean isNumeric(CharSequence cs) {
        if (cs != null && cs.length() != 0) {
            int sz = cs.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static String isValidTagString(String tagString) throws Exception {
        Pattern pattern = Pattern.compile("^\\d{2}$");
        if (!pattern.matcher(tagString).matches()) {
            throw new Exception("Unknown tag "+ tagString +" is not defined");
        } else {
            return tagString;
        }
    }

    public static boolean isValidTagStringWithinRange(String subtag, int topRange, int bottomRange) throws Exception {
        int tag;
        try {
            tag = Integer.parseInt(subtag);
        } catch (Exception var5) {
            throw new Exception("Tag "+ subtag +" is not defined");
        }

        return tag > topRange - 1 && tag < bottomRange + 1;
    }
}
