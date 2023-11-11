package zen.relife.util;

import net.minecraft.client.Minecraft;

public class Mapping {
    public static String onGround;
    public static String tickLength;
    public static String timer;
    public static String session;
    public static String yaw;
    public static String pitch;
    public static String rightClickDelayTimer;
    public static String getPlayerInfo;
    public static String playerTextures;
    public static String currentGameType;
    public static String connection;
    public static String blockHitDelay;
    public static String isInWeb;
    public static String curBlockDamageMP;
    public static String isHittingBlock;
    public static String onUpdateWalkingPlayer;

    static {
        Mapping.onGround = ("onGround");
        Mapping.tickLength = ("tickLength");
        Mapping.timer = ("timer");
        Mapping.session = ("session");
        Mapping.yaw = ("yaw");
        Mapping.pitch = ("pitch");
        Mapping.rightClickDelayTimer = ("rightClickDelayTimer");
        Mapping.getPlayerInfo = ("getPlayerInfo");
        Mapping.playerTextures = ("playerTextures");
        Mapping.currentGameType = ("currentGameType");
        Mapping.connection = ("connection");
        Mapping.blockHitDelay = ("blockHitDelay");
        Mapping.isInWeb = ("isInWeb");
        Mapping.curBlockDamageMP = ("curBlockDamageMP");
        Mapping.isHittingBlock = ("isHittingBlock");
        Mapping.onUpdateWalkingPlayer = ("onUpdateWalkingPlayer");
    }

    public static boolean isNotObfuscated() {
        try {
            return Minecraft.class.getDeclaredField("instance") != null;
        } catch (Exception ex) {
            return false;
        }
    }
}
