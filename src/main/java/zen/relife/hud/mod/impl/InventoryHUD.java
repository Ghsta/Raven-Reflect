package zen.relife.hud.mod.impl;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import zen.relife.hud.mod.HudMod;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.impl.render.Hud;
import zen.relife.setting.EnableSetting;
import zen.relife.util.RenderUtil;

import java.awt.*;

public class InventoryHUD extends HudMod {
    EnableSetting option = Hud.InventoryHUD;

    public InventoryHUD() {
        super("InventoryHUD", 10, 20);
    }

    @Override
    public void draw() {
        if (Hud.InventoryHUD.getEnable()) {
            GL11.glPushMatrix();
            RenderUtil.drawRoundedCornerRect(getX(), getY() - 9, getX() + 182, getY() + 1, 2, new Color(255, 132, 211, 255).getRGB());
            FontManager.C16.drawString("Inventory HUD", getX() + 1, getY() - 7, new Color(255, 255, 255, 255).getRGB());
            RenderUtil.drawRoundedCornerRect(getX(), getY(), getX() + (20 * 9) + 2, getY() + (20 * 3) + 2, 6, new Color(0, 0, 0, 160).getRGB());
            RenderHelper.enableGUIStandardItemLighting();
            for (int i = 0; i < 27; i++) {
                ItemStack[] itemStack = mc.player.inventory.mainInventory.toArray(new ItemStack[0]);
                int offsetX = getX() + 2 + (i % 9) * 20;
                int offsetY = getY() + (i / 9) * 20;
                mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack[i + 9], offsetX, offsetY);
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack[i + 9], offsetX, offsetY, null);
            }
            GL11.glPopMatrix();
        }

        super.draw();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        if (option.getEnable()) {
            GL11.glPushMatrix();
            RenderUtil.drawRoundedCornerRect(getX(), getY() - 9, getX() + 182, getY() + 1, 2, new Color(255, 132, 211, 255).getRGB());
            FontManager.C16.drawString("Inventory HUD", getX() + 1, getY() - 7, new Color(255, 255, 255, 255).getRGB());
            RenderUtil.drawRoundedCornerRect(getX(), getY(), getX() + (20 * 9) + 2, getY() + (20 * 3) + 2, 6, new Color(0, 0, 0, 160).getRGB());
            RenderHelper.enableGUIStandardItemLighting();
            for (int i = 0; i < 27; i++) {
                ItemStack[] itemStack = mc.player.inventory.mainInventory.toArray(new ItemStack[0]);
                int offsetX = getX() + 2 + (i % 9) * 20;
                int offsetY = getY() + (i / 9) * 20;
                mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack[i + 9], offsetX, offsetY);
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack[i + 9], offsetX, offsetY, null);
            }
            GL11.glPopMatrix();
        }

        super.renderDummy(mouseX, mouseY);
    }

}
