package emv.reader;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmvData {

    private int length;
    private String value;
    private int tagId;

    private List<EmvData> subEmvs;

    public EmvData(int length, String value, int tagId){
        this.length = length;
        this.value = value;
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "EmvData{" +
                "length=" + length +
                ", values='" + value + '\'' +
                ", tagId=" + tagId +
                ", subEmv=" + subEmvs +
                '}';
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
