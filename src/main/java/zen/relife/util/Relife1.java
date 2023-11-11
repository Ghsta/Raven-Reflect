package zen.relife.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.Packet;

import java.lang.reflect.Field;

public class Relife1 {
    public static boolean SelfFontRender;
    public static volatile Relife1 INSTANCE;

    static {
        Relife1.SelfFontRender = true;
        Relife1.INSTANCE = new Relife1();
    }

    public static double getRenderPosX() {
        return (double) getField("renderPosX", "renderPosX", Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosY() {
        return (double) getField("renderPosY", "renderPosY", Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosZ() {
        return (double) getField("renderPosZ", "renderPosZ", Minecraft.getMinecraft().getRenderManager());
    }

    public static Object getField(final String field, final String obfName, final Object instance) {
        final Class<?> class1 = instance.getClass();
        String[] array = new String[0];
        if (Mapping.isNotObfuscated()) {
            array = new String[]{field};
        } else {
        }
        final Field fField = ReflectionHelper.findField(class1, array);
        fField.setAccessible(true);
        try {
            return fField.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public EntityPlayerSP player() {
        return Relife1.INSTANCE.mc().player;
    }

    public WorldClient world() {
        return Relife1.INSTANCE.mc().world;
    }

    public GameSettings mcSettings() {
        return Relife1.INSTANCE.mc().gameSettings;
    }

    public FontRenderer fontRenderer() {
        return Relife1.INSTANCE.mc().fontRenderer;
    }

    public void sendPacket(final Packet packet) {
        this.player().connection.sendPacket(packet);
    }

    public InventoryPlayer inventory() {
        return this.player().inventory;
    }

    public PlayerControllerMP controller() {
        return Relife1.INSTANCE.mc().playerController;
    }

    public void message(String s) {
    }
}
