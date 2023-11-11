package zen.relife.render.font;

import java.util.regex.Pattern;

public class StringUtils {
    public static String getSubString(final String text, final String left, final String right) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }

    public static String removeFormattingCodes(final String text) {
        return Pattern.compile("(?i)��[0-9A-FK-OR]").matcher(text).replaceAll("");
    }
}
