package zen.relife.module.impl.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import zen.relife.module.Category;
import zen.relife.module.Module;

import java.util.List;

public class ItemESP
        extends Module {
    public ItemESP() {
        super("ItemESP", 0, Category.RENDER, false);
    }

    public static List getEntityList() {
        return ItemESP.mc.world.getLoadedEntityList();
    }

    public static void drawESP(Entity entity, float colorRed, float colorGreen, float colorBlue, float colorAlpha, float ticks) {
        try {
            double exception = ItemESP.mc.getRenderManager().viewerPosX;
            double renderPosY = ItemESP.mc.getRenderManager().viewerPosY;
            double renderPosZ = ItemESP.mc.getRenderManager().viewerPosZ;
            double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) ticks - exception;
            double yPos = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) ticks + (double) (entity.height / 2.0f) - renderPosY;
            double zPos = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) ticks - renderPosZ;
            float playerViewY = ItemESP.mc.getRenderManager().playerViewY;
            float playerViewX = ItemESP.mc.getRenderManager().playerViewX;
            boolean thirdPersonView = ItemESP.mc.getRenderManager().options.thirdPersonView == 2;
            GL11.glPushMatrix();
            GlStateManager.translate(xPos, yPos, zPos);
            GlStateManager.rotate(-playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate((float) (thirdPersonView ? -1 : 1) * playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(1.0f);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glColor4f(colorRed, colorGreen, colorBlue, colorAlpha);
            GL11.glBegin(1);
            GL11.glVertex3d(0.0, 1.0, 0.0);
            GL11.glVertex3d(-0.5, 0.5, 0.0);
            GL11.glVertex3d(0.0, 1.0, 0.0);
            GL11.glVertex3d(0.5, 0.5, 0.0);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(-0.5, 0.5, 0.0);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.5, 0.5, 0.0);
            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glDisable(2848);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        RenderHelper.enableGUIStandardItemLighting();
        for (Object object : ItemESP.getEntityList()) {
            if (!(object instanceof EntityItem) && !(object instanceof EntityArrow)) continue;
            Entity entity = (Entity) object;
            ItemESP.drawESP(entity, (float) ClickGui.red.getCurrent(), (float) ClickGui.green.getCurrent(), (float) ClickGui.blue.getCurrent(), 1.0f, event.getPartialTicks());
        }
        RenderHelper.disableStandardItemLighting();
    }
}

