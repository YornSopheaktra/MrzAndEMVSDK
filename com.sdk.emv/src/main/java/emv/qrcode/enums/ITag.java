package emv.qrcode.enums;

import java.io.Serializable;
import java.util.regex.Pattern;

public interface ITag extends Serializable {
    String getTag();

    Pattern getPattern();

    boolean isMandatory();
}
