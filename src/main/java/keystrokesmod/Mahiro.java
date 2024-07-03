package keystrokesmod;

import keystrokesmod.client.clickgui.raven.ClickGui;
import keystrokesmod.client.command.CommandManager;
import keystrokesmod.client.config.ConfigManager;
import keystrokesmod.client.main.ClientConfig;
import keystrokesmod.client.main.LaunchTracker;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.ModuleManager;
import keystrokesmod.client.module.modules.HUD;
import keystrokesmod.client.utils.ChatHelper;
import keystrokesmod.client.utils.DebugInfoRenderer;
import keystrokesmod.client.utils.MouseManager;
import keystrokesmod.keystroke.KeyStroke;
import keystrokesmod.keystroke.KeyStrokeCommand;
import keystrokesmod.keystroke.KeyStrokeConfigGui;
import keystrokesmod.keystroke.KeyStrokeRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Mod(modid = "keystrokesmod", name = "KeystrokesMod", version = "2024.6.22", acceptedMinecraftVersions = "[1.12.2]")

public class Mahiro {
    public static Mahiro INSTANCE = new Mahiro();
    @Mod.Instance
    public Mahiro instance;
    private static KeyStroke keyStroke;
    private static KeyStrokeRenderer keyStrokeRenderer;
    private static boolean isKeyStrokeConfigGuiToggled = false;
    public Raven raven;
    public static ConfigManager configManager;
    public static ClientConfig clientConfig;
    public static CommandManager commandManager;

    public static  ModuleManager moduleManager;
    public static ResourceLocation mResourceLocation;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        (Mahiro.INSTANCE.instance = new Mahiro()).init();
    }

    @Mod.EventHandler
    public void init() {
        ClientCommandHandler.instance.registerCommand(new KeyStrokeCommand());
        ClientConfig.applyKeyStrokeSettingsFromConfigFile();
        Raven.init();
    }
    public static KeyStroke getKeyStroke() {
        return keyStroke;
    }

    public static KeyStrokeRenderer getKeyStrokeRenderer() {
        return keyStrokeRenderer;
    }

    public static void toggleKeyStrokeConfigGui() {
        isKeyStrokeConfigGuiToggled = true;
    }
}
