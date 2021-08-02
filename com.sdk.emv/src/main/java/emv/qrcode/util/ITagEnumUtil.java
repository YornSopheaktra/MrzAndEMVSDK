package emv.qrcode.util;

import emv.qrcode.enums.ITag;

public class ITagEnumUtil {
    public static <T extends ITag> T getTag(String tagString, Class<T> clazz) throws Exception {
        ITag[] var2 = clazz.getEnumConstants();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            T tag = (T) var2[var4];
            if (tag.getTag().equals(tagString)) {
                return tag;
            }
        }

        throw new Exception("Unknown tag "+ tagString +" is not defined");
    }

    private ITagEnumUtil() {
    }
}
