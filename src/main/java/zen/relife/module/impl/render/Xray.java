package zen.relife.module.impl.render;


import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.setting.IntegerSetting;
import zen.relife.util.HUDUtils;
import zen.relife.util.TimerUtils;
import zen.relife.util.Tools;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Xray
        extends Module {
    public static EnableSetting spawner = new EnableSetting("Spawner", true);
    public static EnableSetting coal = new EnableSetting("Coal", true);
    public static EnableSetting iron = new EnableSetting("Iron", true);
    public static EnableSetting lapis = new EnableSetting("Lapis", true);
    public static EnableSetting emerald = new EnableSetting("Emerald", true);
    public static EnableSetting redstone = new EnableSetting("Redstone", true);
    public static EnableSetting gold = new EnableSetting("Gold", true);
    public static EnableSetting diammond = new EnableSetting("Diammond", true);
    public static IntegerSetting r = new IntegerSetting("Range", 20.0, 5.0, 100.0, 1);
    public static List<BlockPos> toRender = new ArrayList<BlockPos>();
    private final long per = 200L;
    private final TimerUtils refresh = new TimerUtils();
    private Timer t;
    private List<BlockPos> ren;

    public Xray() {
        super("Xray", 0, Category.RENDER, false);
        this.getSetting().add(spawner);
        this.getSetting().add(coal);
        this.getSetting().add(iron);
        this.getSetting().add(lapis);
        this.getSetting().add(emerald);
        this.getSetting().add(redstone);
        this.getSetting().add(gold);
        this.getSetting().add(diammond);
        this.getSetting().add(r);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.ren = new ArrayList<BlockPos>();
        this.t = new Timer();
        this.t.scheduleAtFixedRate(this.t(), 0L, 200L);
    }

    @Override
    public void disable() {
        super.disable();
        if (this.t != null) {
            this.t.cancel();
            this.t.purge();
            this.t = null;
        }
    }

    private TimerTask t() {
        return new TimerTask() {

            @Override
            public void run() {
                int ra;
                Xray.this.ren.clear();
                for (int y = ra = (int) r.getCurrent(); y >= -ra; --y) {
                    for (int x = -ra; x <= ra; ++x) {
                        for (int z = -ra; z <= ra; ++z) {
                            if (!Tools.isPlayerInGame()) continue;
                            BlockPos p = new BlockPos(Module.mc.player.posX + (double) x, Module.mc.player.posY + (double) y, Module.mc.player.posZ + (double) z);
                            Block bl = Module.mc.world.getBlockState(p).getBlock();
                            if (!(iron.getEnable() && bl.equals(Blocks.IRON_ORE) || gold.getEnable() && bl.equals(Blocks.GOLD_ORE) || diammond.getEnable() && bl.equals(Blocks.DIAMOND_ORE) || emerald.getEnable() && bl.equals(Blocks.EMERALD_ORE) || lapis.getEnable() && bl.equals(Blocks.LAPIS_ORE) || redstone.getEnable() && bl.equals(Blocks.REDSTONE_ORE) || coal.getEnable() && bl.equals(Blocks.COAL_ORE)) && (!spawner.getEnable() || !bl.equals(Blocks.MOB_SPAWNER)))
                                continue;
                            Xray.this.ren.add(p);
                        }
                    }
                }
            }
        };
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (Tools.isPlayerInGame() && !this.ren.isEmpty()) {
            ArrayList<BlockPos> tRen = new ArrayList<BlockPos>(this.ren);
            for (BlockPos p : tRen) {
                this.dr(p);
            }
        }
    }

    private void dr(BlockPos p) {
        int[] rgb = this.c(Xray.mc.world.getBlockState(p).getBlock());
        if (rgb[0] + rgb[1] + rgb[2] != 0) {
            HUDUtils.re(p, new Color(rgb[0], rgb[1], rgb[2]).getRGB(), true);
        }
    }

    private int[] c(Block b) {
        int red = 0;
        int green = 0;
        int blue = 0;
        if (b.equals(Blocks.IRON_ORE)) {
            red = 255;
            green = 255;
            blue = 255;
        } else if (b.equals(Blocks.GOLD_ORE)) {
            red = 255;
            green = 255;
        } else if (b.equals(Blocks.DIAMOND_ORE)) {
            green = 220;
            blue = 255;
        } else if (b.equals(Blocks.EMERALD_ORE)) {
            red = 35;
            green = 255;
        } else if (b.equals(Blocks.LAPIS_ORE)) {
            green = 50;
            blue = 255;
        } else if (b.equals(Blocks.REDSTONE_ORE)) {
            red = 255;
        } else if (b.equals(Blocks.MOB_SPAWNER)) {
            red = 30;
            blue = 135;
        }
        return new int[]{red, green, blue};
    }
}

