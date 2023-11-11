package zen.relife.hook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.Random;

public class Wrapper {
    public static Minecraft mc;
    public static volatile Wrapper INSTANCE;
    public static boolean canSendMotionPacket;
    public static Timer timer;


    public Minecraft mc() {
        return Minecraft.getMinecraft();
    }


    public GameSettings mcSettings() {
        return Wrapper.INSTANCE.mc().gameSettings;
    }


    public PlayerControllerMP controller() {
        return Wrapper.INSTANCE.mc().playerController;
    }

    public int r(final int init) {
        return new Random().nextInt(init);
    }

    public void reflect_field_bool(final Class clazz, final Object o, final String origin_name, final String srg, final boolean b) {
        final Field field = ReflectionHelper.findField(clazz, new String[]{origin_name, srg});
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.setBoolean(o, b);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}