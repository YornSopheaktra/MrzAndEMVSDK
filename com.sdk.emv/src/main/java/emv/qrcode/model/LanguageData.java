package emv.qrcode.model;

import emv.qrcode.enums.LanguageTag;
import emv.qrcode.enums.PPTag;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class LanguageData extends AbstractDataModel<LanguageTag> {
    private List<String> languages = Arrays.asList(Locale.getISOLanguages());

    public LanguageData() {
        super(LanguageTag.class, "^(0[3-9]|[1-9][0-9])$", "64");
    }

    public void validate() throws Exception{
        super.validate();
        this.isValidLanguage();
    }

    public String getLanguagePreference() {
        return this.getStringValue(LanguageTag.TAG_00_LANGUAGE_PREFERENCE);
    }

    public LanguageData setLanguagePreference(String languagePreference) {
        this.setValue(LanguageTag.TAG_00_LANGUAGE_PREFERENCE, languagePreference);
        return this;
    }

    public String getAlternateMerchantName() {
        return this.getStringValue(LanguageTag.TAG_01_ALTERNATE_MERCHANT_NAME);
    }

    public LanguageData setAlternateMerchantName(String altMerchantName) {
        this.setValue(LanguageTag.TAG_01_ALTERNATE_MERCHANT_NAME, altMerchantName);
        return this;
    }

    public String getAlternateMerchantCity() {
        return this.getStringValue(LanguageTag.TAG_02_ALTERNATE_MERCHANT_CITY);
    }

    public LanguageData setAlternateMerchantCity(String altMerchantCity) {
        this.setValue(LanguageTag.TAG_02_ALTERNATE_MERCHANT_CITY, altMerchantCity);
        return this;
    }

    private String isValidLanguage() throws Exception {
        String value = this.getLanguagePreference();
        if (!this.languages.contains(value.toLowerCase())) {
            throw new Exception("The tag " + PPTag.TAG_64_LANGUAGE.getTag() + " value as "+ value +" is invalid, "+LanguageTag.TAG_00_LANGUAGE_PREFERENCE);
        } else {
            return value;
        }
    }
}
