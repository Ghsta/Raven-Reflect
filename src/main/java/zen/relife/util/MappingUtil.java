package zen.relife.util;

import net.minecraft.client.Minecraft;

public class MappingUtil {
    public static String blockHitDelay = isNotObfuscated() ? "blockHitDelay" : "field_78781_i";
    public static String curBlockDamageMP = isNotObfuscated() ? "curBlockDamageMP" : "field_78770_f";

    public static boolean isNotObfuscated() {
        try {
            return Minecraft.class.getDeclaredField("instance") != null;
        } catch (Exception ex) {
            return false;
        }
    }
}
