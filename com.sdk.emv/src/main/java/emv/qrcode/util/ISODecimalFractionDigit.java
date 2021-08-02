package emv.qrcode.util;

import emv.qrcode.enums.CurrencyEnum;

import java.util.Currency;

public class ISODecimalFractionDigit {
    public ISODecimalFractionDigit() {
    }

    public int getISODecimalFractionDigits(String alphaCurrencyCode) {
        CurrencyEnum currencyEnum = CurrencyEnum.valueOf(alphaCurrencyCode);
        int decimalFractionDigits;
        switch (currencyEnum) {
            case RYR:
            case DJF:
            case TJR:
            case GRD:
            case LUF:
            case CLF:
                decimalFractionDigits = 0;
                break;
            case IDE:
            case ECS:
            case ECV:
            case XCD:
            case XEU:
            case SDP:
            case SRG:
            case YUN:
            case ZRN:
            case TZS:
            case RUR:
            case MZM:
            case LTL:
            case UGX:
            case ISK:
            case VND:
                decimalFractionDigits = 2;
                break;
            default:
                decimalFractionDigits = Currency.getInstance(alphaCurrencyCode).getDefaultFractionDigits();
        }

        return decimalFractionDigits;
    }
}