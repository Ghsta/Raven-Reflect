package keystrokesmod.client.module.modules.other;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.utils.DimensionHelper;
import keystrokesmod.client.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class WaterBucket extends Module {
   public static DescriptionSetting moduleDesc;
   private boolean handling;

   public WaterBucket() {
      super("Water bucket", ModuleCategory.other);
      this.registerSetting(moduleDesc = new DescriptionSetting("Credits: aycy"));
      this.registerSetting(moduleDesc = new DescriptionSetting("Disabled in the Nether"));
   }

   @Override
   public boolean canBeEnabled() {
      return !DimensionHelper.isPlayerInNether();
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent ev) {
      if (ev.phase != Phase.END && Utils.Player.isPlayerInGame() && !mc.isGamePaused()) {
         if (DimensionHelper.isPlayerInNether()) this.disable();

         if (this.inPosition() && this.holdWaterBucket()) {
            this.handling = true;
         }

         if (this.handling) {
            this.mlg();
            if (mc.player.onGround || mc.player.motionY > 0.0D) {
               this.reset();
            }
         }

      }
   }

   private boolean inPosition() {
      if (mc.player.motionY < -0.6D && !mc.player.onGround && !mc.player.capabilities.isFlying && !mc.player.capabilities.isCreativeMode && !this.handling) {
         BlockPos playerPos = mc.player.getPosition();

         for(int i = 1; i < 3; ++i) {
            BlockPos blockPos = playerPos.down(i);
            Block block = mc.world.getBlockState(blockPos).getBlock();
            if (block.canPlaceBlockOnSide(mc.world, blockPos, EnumFacing.UP)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean holdWaterBucket() {
      if (this.containsItem(mc.player.getHeldItemMainhand(), Items.WATER_BUCKET)) {
         return true;
      } else {
         for(int i = 0; i < InventoryPlayer.getHotbarSize(); ++i) {
            if (this.containsItem(mc.player.inventory.mainInventory.get(i), Items.WATER_BUCKET)) {
               mc.player.inventory.currentItem = i;
               return true;
            }
         }

         return false;
      }
   }

   private void mlg() {
      ItemStack heldItem = mc.player.getHeldItemMainhand();
      if (this.containsItem(heldItem, Items.WATER_BUCKET) && mc.player.rotationPitch >= 70.0F) {
         RayTraceResult object = mc.objectMouseOver;
         if (object.typeOfHit == RayTraceResult.Type.BLOCK && object.sideHit == EnumFacing.UP) {
         }
      }

   }

   private void reset() {
      ItemStack heldItem = mc.player.getHeldItemMainhand();
      if (this.containsItem(heldItem, Items.BUCKET)) {
      }

      this.handling = false;
   }

   private boolean containsItem(ItemStack itemStack, Item item) {
      return itemStack != null && itemStack.getItem() == item;
   }
}
