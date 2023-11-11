package zen.relife.manager.impl;


import zen.relife.Relife;
import zen.relife.eventbus.handler.impl.EventPacket;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.module.impl.combat.*;
import zen.relife.module.impl.configs.LoadConfig;
import zen.relife.module.impl.configs.SaveConfig;
import zen.relife.module.impl.example.example;
import zen.relife.module.impl.explolt.Disabler;
import zen.relife.module.impl.misc.AntiAFK;
import zen.relife.module.impl.misc.ChatRemind;
import zen.relife.module.impl.misc.Teams;
import zen.relife.module.impl.movement.InvMove;
import zen.relife.module.impl.movement.*;
import zen.relife.module.impl.render.*;
import zen.relife.module.impl.world.AutoArmor;
import zen.relife.module.impl.world.AutoTool;
import zen.relife.module.impl.world.FastBreak;
import zen.relife.setting.ModeSetting;
import zen.relife.setting.Setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
    static ArrayList<Module> list = new ArrayList();

    static {
        //combat
        list.add(new KillAura());
        list.add(new SuperKnockBack());
        list.add(new HitBox());
        list.add(new AimAssist());
        list.add(new AntiAFK());
        list.add(new ClickAssist());
        list.add(new Reach());
        list.add(new Velocity());
        list.add(new FastBow());
        list.add(new AutoDestroy());
        list.add(new AutoClicker());
        //movement
        list.add(new KeepSprint());
        list.add(new Sprint());
        list.add(new Fly());
        list.add(new Speed());
        list.add(new InvMove());
        list.add(new Eagle());
        //render
        list.add(new FullBright());
        list.add(new ClickGui());
        list.add(new Hud());
        list.add(new ESP());
        list.add(new NameTags());
        list.add(new Chams());
        list.add(new ChestESP());
        list.add(new ItemESP());
        list.add(new FakePlayer());
        list.add(new DragScreen());
        list.add(new Tracers());
        list.add(new Panic());
        list.add(new AttackEffect());
        list.add(new Trails());
        list.add(new Xray());
        list.add(new ViewModel());
        list.add(new AttackTrace());
        //misc
        list.add(new Teams());
        list.add(new ChatRemind());
        list.add(new AntiBot());
        list.add(new example());
        //world
        list.add(new AutoTool());
        list.add(new AutoArmor());
        list.add(new FastBreak());
        //config
        list.add(new LoadConfig());
        list.add(new SaveConfig());
        //explolt
        list.add(new Disabler());
    }

    public static void onPacketEvent(EventPacket event) {
        for (Module module : Relife.INSTANCE.getModuleManager().getModules()) {
            module.onPacketEvent(event);
        }
    }

    public static void keyPress(int key) {
        for (Module m : modules) {
            if (m.getKey() == key) {
                m.toggle();
            }
        }
    }

    public ArrayList<Module> getModules() {
        return list;
    }

    public final List<String> getSettingByModule(String name) {
        ArrayList setting = new ArrayList();
        for (Module it : Relife.INSTANCE.getModuleManager().getModules()) {
            Iterator<Setting> iterator;
            if (!it.getName().equalsIgnoreCase(name) || !(iterator = it.getSetting().iterator()).hasNext()) continue;
            Setting s = iterator.next();
            return ((ModeSetting) s).getModes();
        }
        return null;
    }

    public void regMods(Module... mods) {
        Collections.addAll(this.getModules(), mods);
    }

    public final List<Module> getModulesForCategory(Category category) {
        ArrayList<Module> localModules = new ArrayList<Module>();
        ArrayList<Module> modules = list;
        int modulesSize = modules.size();
        for (int i = 0; i < modulesSize; ++i) {
            Module module = modules.get(i);
            if (module.getCategory() != category) continue;
            localModules.add(module);
        }
        return localModules;
    }

    public Module getModule(String name) {
        for (Module m : list) {
            if (!m.getName().equalsIgnoreCase(name)) continue;
            return m;
        }
        return null;
    }
}
