package zen.relife.module.impl.world;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.setting.EnableSetting;
import zen.relife.util.Relife1;
import zen.relife.util.TimerUtils;
import zen.relife.util.Tools;

public class AutoArmor extends Module {
    private final TimerUtils timer = new TimerUtils();
    private final EnableSetting isOngui = new EnableSetting("Inventory", false);

    public AutoArmor() {
        super("AutoArmor", 0, Category.WORLD, false);
        this.getSetting().add(this.isOngui);
    }

    public static boolean isBestArmor(ItemStack stack, int type) {
        float prot = AutoArmor.getProtection(stack);
        String strType = "";
        if (type == 1) {
            strType = "helmet";
        } else if (type == 2) {
            strType = "chestplate";
        } else if (type == 3) {
            strType = "leggings";
        } else if (type == 4) {
            strType = "boots";
        }
        if (!stack.getUnlocalizedName().contains(strType)) {
            return false;
        }
        for (int i = 5; i < 45; ++i) {
            ItemStack is;
            if (!AutoArmor.mc.player.inventoryContainer.getSlot(i).getHasStack() || !(AutoArmor.getProtection(is = AutoArmor.mc.player.inventoryContainer.getSlot(i).getStack()) > prot) || !is.getUnlocalizedName().contains(strType))
                continue;
            return false;
        }
        return true;
    }

    public static float getProtection(ItemStack stack) {
        float prot = 0.0f;
        if (stack.getItem() instanceof ItemArmor) {
            final Enchantment protection = Enchantments.PROTECTION;
            ItemArmor armor = (ItemArmor) stack.getItem();
            prot = (float) ((double) prot + (double) armor.damageReduceAmount + ((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(protection, stack) * 0.0075));
            prot = (float) ((double) prot + (double) EnchantmentHelper.getEnchantmentLevel(protection, stack) / 100.0);
            prot = (float) ((double) prot + (double) EnchantmentHelper.getEnchantmentLevel(protection, stack) / 100.0);
            prot = (float) ((double) prot + (double) EnchantmentHelper.getEnchantmentLevel(protection, stack) / 100.0);
            prot = (float) ((double) prot + (double) EnchantmentHelper.getEnchantmentLevel(protection, stack) / 50.0);
            prot = (float) ((double) prot + (double) EnchantmentHelper.getEnchantmentLevel(protection, stack) / 100.0);
        }
        return prot;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Tools.isPlayerInGame()) {
            return;
        }
        long delay = 250L;
        if (this.isOngui.getEnable()) {
            if (AutoArmor.mc.currentScreen instanceof GuiInventory && (AutoArmor.mc.currentScreen == null || AutoArmor.mc.currentScreen instanceof GuiInventory || AutoArmor.mc.currentScreen instanceof GuiChat) && this.timer.delay(delay)) {
                this.getBestArmor();
            }
        } else {
            this.getBestArmor();
        }
    }

    public void getBestArmor() {
        for (int type = 1; type < 5; ++type) {
            if (AutoArmor.mc.player.inventoryContainer.getSlot(4 + type).getHasStack()) {
                ItemStack i = AutoArmor.mc.player.inventoryContainer.getSlot(4 + type).getStack();
                if (AutoArmor.isBestArmor(i, type)) continue;
                CPacketClientStatus is = new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN);
                Relife1.INSTANCE.sendPacket(is);
                this.drop(4 + type);
            }
            for (int var4 = 9; var4 < 45; ++var4) {
                ItemStack var5;
                if (!AutoArmor.mc.player.inventoryContainer.getSlot(var4).getHasStack() || !AutoArmor.isBestArmor(var5 = AutoArmor.mc.player.inventoryContainer.getSlot(var4).getStack(), type) || !(AutoArmor.getProtection(var5) > 0.0f))
                    continue;
                this.shiftClick(var4);
                this.timer.reset();
                return;
            }
        }
    }

    public void shiftClick(int slot) {
    }

    public void drop(int slot) {
    }
}
