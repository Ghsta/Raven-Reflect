package zen.relife.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class ReflectionUtil {
    public static final Field delayTimer = ReflectionHelper.findField(Minecraft.class, "field_71467_ac", "rightClickDelayTimer");
    public static final Field running = ReflectionHelper.findField(Minecraft.class, "field_71425_J", "running");
    public static final Field pressed = ReflectionHelper.findField(KeyBinding.class, "field_74513_e", "pressed");
    public static final Field theShaderGroup = ReflectionHelper.findField(EntityRenderer.class, "field_147707_d", "shaderGroupName");
    public static final Field listShaders = ReflectionHelper.findField(ShaderGroup.class, "field_148034_c", "listShaders");

    static {
        delayTimer.setAccessible(true);
        running.setAccessible(true);
        pressed.setAccessible(true);
        theShaderGroup.setAccessible(false);
        listShaders.setAccessible(true);
    }
}

