package emv.qrcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyUtil {

    private Integer currencyCode;
    private String currencyName;

    public CurrencyUtil(String currencyName){
        this.currencyCode = getCurrencyNum(currencyName);
        this.currencyName = currencyName;
    }

    public static int getCurrencyNum(String currencyCode) {
        for (Currency currency : Currency.getAvailableCurrencies()) {
            if (currency.getCurrencyCode().equals(currencyCode)) {
                return currency.getNumericCode();
            }
        }
        return -1;
    }
}
