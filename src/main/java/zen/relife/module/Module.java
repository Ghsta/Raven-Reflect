package zen.relife.module;


import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import zen.relife.Notifications.Notification;
import zen.relife.Relife;
import zen.relife.eventbus.handler.impl.EventPacket;
import zen.relife.manager.impl.EventManager;
import zen.relife.module.impl.render.Hud;
import zen.relife.setting.ModeSetting;
import zen.relife.setting.Setting;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public boolean state;

    public int key;

    public String name;
    public Category category;

    public ArrayList<Setting> setting;
    public boolean toggled;

    public Module(String name, int key, Category category, boolean toggle) {

        this.name = name;

        this.key = key;

        this.category = category;

        this.setting = new ArrayList();

        if (toggle) {
            this.setState(true);
        }
    }

    public boolean onPacket(Object packet, int side) {
        return true;
    }

    public boolean isToggledMode(String modeName) {
        for (Setting value : this.setting) {
            if (!(value instanceof ModeSetting)) continue;
            ModeSetting modeValue = (ModeSetting) value;
            for (String name : modeValue.getModes()) {
                if (!name.equalsIgnoreCase(modeName) || modeValue.getCurrent() != name) continue;
                return true;
            }
        }
        return false;
    }

    public void onCheck(Setting setting) {
    }

    public void setMode(Setting setting) {
    }

    public void onPacketEvent(EventPacket event) {
    }

    public ArrayList<Setting> getSettings() {
        return this.setting;
    }

    public void isSilder(Setting setting) {
    }

    public List<Setting> getSetting() {
        return this.setting;
    }

    public void toggle() {
        this.setState(!this.state);
    }

    public void onEnable() {
    }

    public void disable() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        if (this.state == state) {

            return;
        }

        this.state = state;
        if (state) {

            EventManager.register(this);

            MinecraftForge.EVENT_BUS.register(this);

            FMLCommonHandler.instance().bus().register(this);

            this.onEnable();

            if (!Hud.notification.getEnable()) {
                return;
            }
            if (this.getName().equalsIgnoreCase("ClickGUI")) {
                Relife.INSTANCE.inited = true;
                return;
            }
            if (this.getName().equalsIgnoreCase("SaveConfig")) {
                return;
            }
            if (this.getName().equalsIgnoreCase("ReloadConfig")) {
                return;
            }
            if (Relife.INSTANCE.inited) {

                Relife.INSTANCE.notificationManager.add(new Notification(this.getName(), Notification.Type.Enable, 0));
            }

        } else {
            EventManager.unregister(this);
            MinecraftForge.EVENT_BUS.unregister(this);
            FMLCommonHandler.instance().bus().unregister(this);
            this.disable();
            if (!Hud.notification.getEnable()) {
                return;
            }
            if (this.getName().equalsIgnoreCase("ClickGUI") || this.getName().equalsIgnoreCase("SaveConfig") || this.getName().equalsIgnoreCase("ReloadConfig")) {
                return;
            }
            if (Relife.INSTANCE.inited) {
                Relife.INSTANCE.notificationManager.add(new Notification(this.getName(), Notification.Type.Disable, 0));
            }
        }
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setToggled(boolean b) {
    }

    protected void renderMod(int mouseX, int mouseY) {
    }

    public void onTick() {
    }
}
