package emv.qrcode;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EmvData {

    private int length;
    private String value;
    private int tagId;

    private List<EmvData> subEmvs;

    public EmvData setSubEmv(int tagId, List<EmvData> subEmvs){
        this.tagId = tagId;
        this.subEmvs =subEmvs;
        String subTagVal = this.generateSubEmvStr(subEmvs);
        this.length = subTagVal.length();
        this.value = this.generateSubEmvStr(subEmvs);
        return this;
    }


  private String generateSubEmvStr(List<EmvData> subEmvs) {
        StringBuilder emv = new StringBuilder();
        subEmvs.forEach(emvData -> emv.append(String.format("%02d%02d%s",emvData.getTagId(),emvData.getLength(),emvData.getValue())));
        return emv.toString();
    }
    public EmvData(int tagId, String value){
        this.length = value.length();
        this.value = value;
        this.tagId = tagId;
    }

    public EmvData(int length, String value, int tagId){
        this.length = length;
        this.value = value;
        this.tagId = tagId;
    }

    public String toEmvStr(){
        return "EmvData{" +
                "length=" + length +
                ", values='" + value + '\'' +
                ", tagId=" + tagId +
                ", subEmv=" + subEmvs +
                '}';

    }

    @Override
    public String toString() {
        return String.format("%02d%02d%s",tagId,length,value);
    }

    @Override
    public int hashCode() {
        int result = length;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmvData)) return false;
        EmvData emvData = (EmvData) o;
        return getLength() == emvData.getLength() &&
                getTagId() == emvData.getTagId() &&
                getValue().equals(emvData.getValue()) &&
                getSubEmvs().equals(emvData.getSubEmvs());
    }
}
