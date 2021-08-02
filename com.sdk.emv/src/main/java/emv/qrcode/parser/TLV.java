package emv.qrcode.parser;

class TLV {
    private String tag;
    private int length;
    private String value;

    TLV(String tag, int length, String value) {
        this.tag = tag;
        this.length = length;
        this.value = value;
    }

    public String getTag() {
        return this.tag;
    }

    public int getLength() {
        return this.length;
    }

    public String getValue() {
        return this.value;
    }
}