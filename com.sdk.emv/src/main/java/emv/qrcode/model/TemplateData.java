package emv.qrcode.model;

import emv.qrcode.enums.TemplateTag;

public abstract class TemplateData extends AbstractDataModel<TemplateTag> {
    public TemplateData(String rootTag) {
        super(TemplateTag.class, "", rootTag);
    }
    public TemplateData(){
        super(TemplateTag.class, "", null);
    }

    public String getAID() {
        return this.getStringValue(TemplateTag.TAG_00_GLOBALLY_UNIQUE_IDENTIFIER);
    }

    public TemplateData setAID(String AID) {
        this.setValue(TemplateTag.TAG_00_GLOBALLY_UNIQUE_IDENTIFIER, AID);
        return this;
    }
}
