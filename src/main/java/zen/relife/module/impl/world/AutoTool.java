package zen.relife.module.impl.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.util.Tools;

public class AutoTool extends Module {
    public static int previousSlot;
    public static boolean justFinishedMining;
    public static boolean mining;

    public AutoTool() {
        super("AutoTool", 0, Category.WORLD, false);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        if (Mouse.isButtonDown(0)) {
            Block stateBlock;
            BlockPos lookingAtBlock = AutoTool.mc.objectMouseOver.getBlockPos();
            if (lookingAtBlock != null && (stateBlock = AutoTool.mc.world.getBlockState(lookingAtBlock).getBlock()) != Blocks.AIR && !(stateBlock instanceof BlockLiquid) && stateBlock instanceof Block) {
                if (!mining) {
                    previousSlot = Tools.getCurrentPlayerSlot();
                    mining = true;
                }
                int index = -1;
                double speed = 1.0;
                for (int slot = 0; slot <= 8; ++slot) {
                    Block bl;
                    BlockPos p;
                    ItemStack itemInSlot = AutoTool.mc.player.inventory.getStackInSlot(slot);
                    if (itemInSlot != null && itemInSlot.getItem() instanceof ItemTool) {
                        p = AutoTool.mc.objectMouseOver.getBlockPos();
                        bl = AutoTool.mc.world.getBlockState(p).getBlock();
                        if (!((double) itemInSlot.getItem().getDestroySpeed(itemInSlot, bl.getDefaultState()) > speed))
                            continue;
                        speed = itemInSlot.getItem().getDestroySpeed(itemInSlot, bl.getDefaultState());
                        index = slot;
                        continue;
                    }
                    if (itemInSlot == null || !(itemInSlot.getItem() instanceof ItemShears)) continue;
                    p = AutoTool.mc.objectMouseOver.getBlockPos();
                    bl = AutoTool.mc.world.getBlockState(p).getBlock();
                    if (!((double) itemInSlot.getItem().getDestroySpeed(itemInSlot, bl.getDefaultState()) > speed))
                        continue;
                    speed = itemInSlot.getItem().getDestroySpeed(itemInSlot, bl.getDefaultState());
                    index = slot;
                }
                if (index != -1 && !(speed <= 1.1) && speed != 0.0) {
                    Tools.hotkeyToSlot(index);
                }
            }
        } else {
            if (mining) {
                Tools.hotkeyToSlot(previousSlot);
            }
            justFinishedMining = false;
            mining = false;
        }
    }
}

