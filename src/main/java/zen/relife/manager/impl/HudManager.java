package zen.relife.manager.impl;

import zen.relife.hud.mod.HudMod;
import zen.relife.hud.mod.impl.InventoryHUD;
import zen.relife.hud.mod.impl.ModsArrayList;
import zen.relife.hud.mod.impl.Sprint;
import zen.relife.hud.mod.impl.TargetHUD;

import java.util.ArrayList;

public class HudManager {
    public ArrayList<HudMod> hudMods = new ArrayList<>();

    public HudManager() {
        hudMods.add(new ModsArrayList());
        hudMods.add(new InventoryHUD());
        hudMods.add(new Sprint());
        hudMods.add(new TargetHUD());
    }

    public void renderMods() {
        for (HudMod m : hudMods) {
            m.draw();
        }
    }
}
