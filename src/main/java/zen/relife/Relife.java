package zen.relife;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zen.relife.Notifications.NotificationManager;
import zen.relife.eventbus.EventBus;
import zen.relife.eventbus.EventEngine;
import zen.relife.eventbus.handler.IHandler;
import zen.relife.eventbus.handler.Listener;
import zen.relife.eventbus.handler.SubscribeEvent;
import zen.relife.eventbus.handler.impl.PlayerKeyEvent;
import zen.relife.manager.impl.HudManager;
import zen.relife.manager.impl.ModuleManager;
import zen.relife.module.Module;
import zen.relife.ui.ClickGUI.ClickGUI;
import zen.relife.util.CpsHelper;
import zen.relife.util.FontHelper;
import zen.relife.util.VerifyUtil;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

@Mod(modid = Relife.MODID, name = Relife.NAME, version = Relife.VERSION)
public final class Relife {
    public static final String MODID = "relife";
    public static final String NAME = "Relife";
    public static final String VERSION = "1.0";
    public static final String REVISION = "@REVISION@";
    public static final Relife INSTANCE = new Relife();
    public static String cName = "Relife Client";
    public static EventEngine eventEngine;
    public static boolean isObfuscate;

    static {
        isObfuscate = false;
    }

    private final EventBus eventBus = new EventBus();
    public Minecraft mc = Minecraft.getMinecraft();
    public CpsHelper cpsHelper;
    @Mod.Instance
    public Relife instance;
    public String prefix;
    public boolean inited;
    public NotificationManager notificationManager;
    public HudManager hudManager;
    public ClickGUI clickGUI;
    public FontHelper fontHelper;
    private ModuleManager moduleManager;

    public Relife() {
        this.prefix = "§f[" + ChatFormatting.RED + "K§f]";
    }

    public static boolean nullCheck() {
        final Minecraft mc = Minecraft.getMinecraft();
        return mc.player == null || mc.world == null;

    }

    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
        (Relife.INSTANCE.instance = new Relife()).init();
    }

    @Mod.EventHandler
    public void init() {
        Relife.INSTANCE.initialize();
    }

    public void initialize() {

        try {
            new VerifyUtil();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "验证成功");
        System.out.println("Initializing Relife client (" + REVISION + ")");
        EVENT_BUS.register(this);
        moduleManager = new ModuleManager();
        hudManager = new HudManager();
        clickGUI = new ClickGUI();
        eventEngine = new EventEngine();
        eventBus.register(new EventListener());
        notificationManager = new NotificationManager();
        fontHelper = new FontHelper();
        EVENT_BUS.register(new ForgeEventListener());
        JOptionPane.showMessageDialog(null, "加载成功");
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public void error(String syntaxError) {

    }

    public void setOBF() {
        instance = this;
        try {
            Field F = SPacketEntityTeleport.class.getDeclaredField("field_149456_b");
            isObfuscate = true;
        } catch (NoSuchFieldException ex) {
            try {
                Field F = SPacketEntityTeleport.class.getDeclaredField("posX");
                isObfuscate = false;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        message = Relife.INSTANCE.instance.prefix + message;

    }

    private final static class EventListener implements IHandler {
        @SubscribeEvent
        public final Listener<PlayerKeyEvent> playerKeyEventListener =
                event -> Relife.INSTANCE.getModuleManager().getModules()
                        .stream()
                        .filter(module -> module.getKey() == event.getKey())
                        .forEach(Module::toggle);
    }
}


