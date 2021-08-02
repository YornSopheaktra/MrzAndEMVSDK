package emv.qrcode.util;

public class CheckSum {
    private CheckSum() {
    }

    public static boolean validate(String numberString) {
        return checkSum(numberString) == 0;
    }

    public static int checkSum(String numberString) {
        return checkSum(numberString, false);
    }

    public static int checkSum(String numberString, boolean noCheckDigit) {
        int sum = 0;
        int checkDigit = 0;
        if (!noCheckDigit) {
            numberString = numberString.substring(0, numberString.length() - 1);
        }

        boolean isDouble = true;

        for (int i = numberString.length() - 1; i >= 0; --i) {
            int k = Integer.parseInt(String.valueOf(numberString.charAt(i)));
            sum += sumToSingleDigit(k * (isDouble ? 2 : 1));
            isDouble = !isDouble;
        }

        if (sum % 10 > 0) {
            checkDigit = 10 - sum % 10;
        }

        return checkDigit;
    }

    private static int sumToSingleDigit(int k) {
        return k < 10 ? k : sumToSingleDigit(k / 10) + k % 10;
    }
}
