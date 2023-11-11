package zen.relife.hud.mod.impl;

import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import zen.relife.eventbus.handler.SubscribeEvent;
import zen.relife.hud.mod.HudMod;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.impl.combat.KillAura;
import zen.relife.module.impl.render.Hud;
import zen.relife.render.font.CFontRenderer;
import zen.relife.setting.EnableSetting;
import zen.relife.util.RenderUtil;
import zen.relife.utils.PlayerUtils;
import zen.relife.utils.TextUtil;

import java.awt.*;

public class TargetHUD extends HudMod {
    EntityLivingBase target;
    EnableSetting option = Hud.TargetHUD;
    private float easingHP = 0f;

    public TargetHUD() {
        super("TargetHUD", 500, 270);
    }

    @Override
    public void draw() {
        if (Hud.TargetHUD.getEnable()) {
            this.renderTargetHud();
        }
        super.draw();
    }

    @SubscribeEvent
    public void renderDummy(int mouseX, int mouseY) {
        target = mc.player;
        easingHP = getHealth(target);

        if (option.getEnable()) {
            CFontRenderer font = FontManager.F18;
            int additionalWidth = Math.max(font.getStringWidth(target.getName()), 75);
            RenderUtil.drawRoundedCornerRect(getX() + 0.0F, getY() + 0.0F, getX() + 45.0F + (float) additionalWidth, getY() + 40.0F, 5.0F, (new Color(7, 7, 7, 174)).getRGB());

            // circle player avatar
            GL11.glColor4f(1F, 1F, 1F, 1F);
            mc.getTextureManager().bindTexture(PlayerUtils.getskin(target));
            RenderUtil.drawScaledCustomSizeModalCircle(getX() + 5, getY() + 5, 8f, 0, 0, 8, 30, 30, 64f, 64f);
            RenderUtil.drawScaledCustomSizeModalCircle(getX() + 5, getY() + 5, 40f, 0, 0, 8, 30, 30, 64f, 64f);

            // info text
            TextUtil.drawCenteredString(target.getName(), getX() + 40 + (additionalWidth / 2f), getY() + 5f, Color.WHITE.getRGB(), true);

            // hp bar
            RenderUtil.drawRoundedCornerRect(getX() + 40f, getY() + 28f, getX() + 40f + additionalWidth, getY() + 33f, 2.5f, new Color(7, 7, 7, 174).getRGB());
            RenderUtil.drawRoundedCornerRect(getX() + 40f, getY() + 28f, getX() + 40f + (easingHP / target.getMaxHealth()) * additionalWidth, getY() + 33f, 2.5f, new Color(255, 6, 6, 163).getRGB());
        }

        super.renderDummy(mouseX, mouseY);
    }

    private void renderTargetHud() {
        target = KillAura.target;
        easingHP = getHealth(target);

        if (target != null) {
            CFontRenderer font = FontManager.F18;
            int additionalWidth = Math.max(font.getStringWidth(target.getName()), 75);
            RenderUtil.drawRoundedCornerRect(getX() + 0.0F, getY() + 0.0F, getX() + 45.0F + (float) additionalWidth, getY() + 40.0F, 7.0F, (new Color(7, 7, 7, 174)).getRGB());

            // circle player avatar
            GL11.glColor4f(1F, 1F, 1F, 1F);
            try {
                mc.getTextureManager().bindTexture(PlayerUtils.getskin(target));
            } catch (Exception e) {
                mc.getTextureManager().bindTexture(PlayerUtils.getskin(mc.player));
            }
            RenderUtil.drawScaledCustomSizeModalCircle(getX() + 5, getY() + 5, 8f, 8f, 0, 0, 30, 30, 64f, 64f);
            RenderUtil.drawScaledCustomSizeModalCircle(getX() + 5, getY() + 5, 40f, 8f, 0, 0, 30, 30, 64f, 64f);

            // info text
            TextUtil.drawCenteredString(target.getName(), getX() + 40 + (additionalWidth / 2f), getY() + 5f, Color.WHITE.getRGB(), true);

            // hp bar
            RenderUtil.drawRoundedCornerRect(getX() + 40f, getY() + 28f, getX() + 40f + additionalWidth, getY() + 33f, 2.5f, new Color(241, 1, 1, 169).getRGB());
            RenderUtil.drawRoundedCornerRect(getX() + 40f, getY() + 28f, getX() + 40f + (easingHP / target.getMaxHealth()) * additionalWidth, getY() + 33f, 2.5f);
        }
    }

    private float getHealth(EntityLivingBase entity) {
        if (entity != null) {
            return entity.getHealth();
        }
        return 0f;
    }

    @Override
    public int getWidth() {
        return 100;
    }
}
