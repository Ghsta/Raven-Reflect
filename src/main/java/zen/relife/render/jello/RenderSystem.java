package zen.relife.render.jello;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderSystem {
    public static void enableGL2D() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void disableGL2D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawBorderedRectReliant(float x2, float y2, float x1, float y1, float lineWidth, int inside, int border) {
        enableGL2D();
        drawRect(x2, y2, x1, y1, inside);
        glColor(border);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        disableGL2D();
    }

    public static void drawGradientBorderedRectReliant(float x2, float y2, float x1, float y1, float lineWidth, int border, int bottom, int top) {
        enableGL2D();
        drawGradientRect(x2, y2, x1, y1, top, bottom);
        glColor(border);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        disableGL2D();
    }

    public static void drawRoundedRect(float x2, float y2, float x1, float y1, int borderC, int insideC) {
        enableGL2D();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        x2 *= 2.0F;
        y2 *= 2.0F;
        y1 *= 2.0F;
        drawVLine(x2, y2 + 1.0F, y1 - 2.0F, borderC);
        drawVLine(x1 *= 2.0F - 1.0F, y2 + 1.0F, y1 - 2.0F, borderC);
        drawHLine(x2 + 2.0F, x1 - 3.0F, y2, borderC);
        drawHLine(x2 + 2.0F, x1 - 3.0F, y1 - 1.0F, borderC);
        drawHLine(x2 + 1.0F, x2 + 1.0F, y2 + 1.0F, borderC);
        drawHLine(x1 - 2.0F, x1 - 2.0F, y2 + 1.0F, borderC);
        drawHLine(x1 - 2.0F, x1 - 2.0F, y1 - 2.0F, borderC);
        drawHLine(x2 + 1.0F, x2 + 1.0F, y1 - 2.0F, borderC);
        drawRect(x2 + 1.0F, y2 + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
    }

    public static void drawStrip(int x2, int y2, float width, double angle, float points, float radius, int color) {
        float f1 = (color >> 24 & 0xFF) / 255.0F;
        float f2 = (color >> 16 & 0xFF) / 255.0F;
        float f3 = (color >> 8 & 0xFF) / 255.0F;
        float f4 = (color & 0xFF) / 255.0F;
        GL11.glPushMatrix();
        GL11.glTranslated(x2, y2, 0.0D);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glLineWidth(width);
        if (angle > 0.0D) {
            GL11.glBegin(3);
            int i2 = 0;
            while (i2 < angle) {
                // pi?
                float a2 = (float) (i2 * (angle * 3.141592653589793D / points));
                float xc2 = (float) (Math.cos(a2) * radius);
                float yc2 = (float) (Math.sin(a2) * radius);
                GL11.glVertex2f(xc2, yc2);
                i2++;
            }
            GL11.glEnd();
        }
        if (angle < 0.0D) {
            GL11.glBegin(3);
            int i2 = 0;
            while (i2 > angle) {
                float a2 = (float) (i2 * (angle * 3.141592653589793D / points));
                float xc2 = (float) (Math.cos(a2) * -radius);
                float yc2 = (float) (Math.sin(a2) * -radius);
                GL11.glVertex2f(xc2, yc2);
                i2--;
            }
            GL11.glEnd();
        }
        disableGL2D();
        GL11.glDisable(3479);
        GL11.glPopMatrix();
    }

    public static void drawRect(int x, int y, int x2, int y2, int color) {
        GuiScreen.drawRect(x, y, x2, y2, color);
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aabb, float r, float g, float b, float a) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        Tessellator tessellator = Tessellator.getInstance();
        bufferBuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        tessellator.draw();
        bufferBuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        tessellator.draw();
        bufferBuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        tessellator.draw();
    }

    public static void drawBoundingBox(AxisAlignedBB aabb, float r, float g, float b, float a) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        Tessellator tessellator = Tessellator.getInstance();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.minX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.minZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).color(r, g, b, a).endVertex();
        bufferBuilder.pos(aabb.maxX, aabb.minY, aabb.maxZ).color(r, g, b, a).endVertex();
        tessellator.draw();
    }

    public static void glColor(int hex) {
        float alpha = (hex >> 24 & 0xFF) / 255.0F;
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
        float red = 0.003921569F * redRGB;
        float green = 0.003921569F * greenRGB;
        float blue = 0.003921569F * blueRGB;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawESP(double x, double y, double z, double offsetX, double offsetY, double offsetZ, float r, float g, float b, float a) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0F);
        GL11.glDisable(2896);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        drawBoundingBox(new AxisAlignedBB(x, y, z, x + offsetX, y + offsetY, z + offsetZ), r, g, b, a);
        drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + offsetX, y + offsetY, z + offsetZ), r, g, b, 1.0F);
        GL11.glLineWidth(1.0F);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(2896);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, int paramColor) {
        float alpha = (paramColor >> 24 & 0xFF) / 255.0F;
        float red = (paramColor >> 16 & 0xFF) / 255.0F;
        float green = (paramColor >> 8 & 0xFF) / 255.0F;
        float blue = (paramColor & 0xFF) / 255.0F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(7);
        GL11.glVertex2d(paramXEnd, paramYStart);
        GL11.glVertex2d(paramXStart, paramYStart);
        GL11.glVertex2d(paramXStart, paramYEnd);
        GL11.glVertex2d(paramXEnd, paramYEnd);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawPoint(int x, int y, int color) {
        drawRect(x, y, x + 1, y + 1, color);
    }

    public static void drawVerticalLine(int x, int y, int height, int color) {
        drawRect(x, y, x + 1, height, color);
    }

    public static void drawHorizontalLine(int x, int y, int width, int color) {
        drawRect(x, y, width, y + 1, color);
    }

    public static void drawBorderedRect(int x, int y, int x1, int y1, int bord, int color) {
        drawRect(x + 1, y + 1, x1, y1, color);
        drawVerticalLine(x, y, y1, bord);
        drawVerticalLine(x1, y, y1, bord);
        drawHorizontalLine(x + 1, y, x1, bord);
        drawHorizontalLine(x, y1, x1 + 1, bord);
    }

    public static void drawFineBorderedRect(int x, int y, int x1, int y1, int bord, int color) {
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2;
        y *= 2;
        x1 *= 2;
        y1 *= 2;
        drawRect(x + 1, y + 1, x1, y1, color);
        drawVerticalLine(x, y, y1, bord);
        drawVerticalLine(x1, y, y1, bord);
        drawHorizontalLine(x + 1, y, x1, bord);
        drawHorizontalLine(x, y1, x1 + 1, bord);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
    }

    public static void drawBorderRectNoCorners(int x, int y, int x2, int y2, int bord, int color) {
        x *= 2;
        y *= 2;
        x2 *= 2;
        y2 *= 2;
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        drawRect(x + 1, y + 1, x2, y2, color);
        drawVerticalLine(x, y + 1, y2, bord);
        drawVerticalLine(x2, y + 1, y2, bord);
        drawHorizontalLine(x + 1, y, x2, bord);
        drawHorizontalLine(x + 1, y2, x2, bord);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
    }

    public static void drawBorderedGradient(int x, int y, int x1, int y1, int bord, int gradTop, int gradBot) {
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2;
        y *= 2;
        x1 *= 2;
        y1 *= 2;
        float f = (gradTop >> 24 & 0xFF) / 255.0F;
        float f2 = (gradTop >> 16 & 0xFF) / 255.0F;
        float f3 = (gradTop >> 8 & 0xFF) / 255.0F;
        float f4 = (gradTop & 0xFF) / 255.0F;
        float f5 = (gradBot >> 24 & 0xFF) / 255.0F;
        float f6 = (gradBot >> 16 & 0xFF) / 255.0F;
        float f7 = (gradBot >> 8 & 0xFF) / 255.0F;
        float f8 = (gradBot & 0xFF) / 255.0F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(x1, y + 1);
        GL11.glVertex2d(x + 1, y + 1);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(x + 1, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(7424);
        drawVLine(x, y, y1 - 1, bord);
        drawVLine(x1 - 1, y, y1 - 1, bord);
        drawHLine(x, x1 - 1, y, bord);
        drawHLine(x, x1 - 1, y1 - 1, bord);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
    }

    public static void framelessBlockESP(BlockPos blockPos, Color color) {
        try {
            RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
            renderManager.getClass().getField("renderPosX").setAccessible(true);
            renderManager.getClass().getField("renderPosY").setAccessible(true);
            renderManager.getClass().getField("renderPosZ").setAccessible(true);
            double renderPosX = renderManager.getClass().getField("renderPosX").getDouble(renderManager);
            double renderPosY = renderManager.getClass().getField("renderPosY").getDouble(renderManager);
            double renderPosZ = renderManager.getClass().getField("renderPosZ").getDouble(renderManager);

            double x2 = blockPos.getX() - renderPosX;
            double y2 = blockPos.getY() - renderPosY;
            double z2 = blockPos.getZ() - renderPosZ;
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            drawColorBox(new AxisAlignedBB(x2, y2, z2, x2 + 1.0D, y2 + 1.0D, z2 + 1.0D), color.getRed(), color.getGreen(), color.getBlue(), 40);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_BLEND);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

    public static void drawColorBox(AxisAlignedBB axisalignedbb, int r, int g, int b, int a) {
        Tessellator ts2 = Tessellator.getInstance();
        BufferBuilder buf = ts2.getBuffer();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        buf.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(r, g, b, a).endVertex();
        ts2.draw();
    }

    public static void tracerLine(Entity entity, int mode) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        try {
            renderManager.getClass().getField("renderPosX").setAccessible(true);
            renderManager.getClass().getField("renderPosY").setAccessible(true);
            renderManager.getClass().getField("renderPosZ").setAccessible(true);
            double renderPosX = renderManager.getClass().getField("renderPosX").getDouble(renderManager);
            double renderPosY = renderManager.getClass().getField("renderPosY").getDouble(renderManager);
            double renderPosZ = renderManager.getClass().getField("renderPosZ").getDouble(renderManager);
            double x2 = entity.posX - renderPosX;
            double y2 = entity.posY + entity.height / 2.0F - renderPosY;
            double z2 = entity.posZ - renderPosZ;
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            if (mode == 0) {
                GL11.glColor4d(1.0F - Minecraft.getMinecraft().player.getDistance(entity) / 40.0F, Minecraft.getMinecraft().player.getDistance(entity) / 40.0F, 0.0D, 0.5D);
            } else if (mode == 1) {
                GL11.glColor4d(0.0D, 0.0D, 1.0D, 0.5D);
            } else if (mode == 2) {
                GL11.glColor4d(1.0D, 1.0D, 0.0D, 0.5D);
            } else if (mode == 3) {
                GL11.glColor4d(1.0D, 0.0D, 0.0D, 0.5D);
            } else if (mode == 4) {
                GL11.glColor4d(0.0D, 1.0D, 0.0D, 0.5D);
            }
            GL11.glBegin(1);
            GL11.glVertex3d(0.0D, Minecraft.getMinecraft().player.getEyeHeight(), 0.0D);
            GL11.glVertex3d(x2, y2, z2);
            GL11.glEnd();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_BLEND);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

    public static void tracerLine(Entity entity, Color color) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        try {
            renderManager.getClass().getField("renderPosX").setAccessible(true);
            renderManager.getClass().getField("renderPosY").setAccessible(true);
            renderManager.getClass().getField("renderPosZ").setAccessible(true);
            double renderPosX = renderManager.getClass().getField("renderPosX").getDouble(renderManager);
            double renderPosY = renderManager.getClass().getField("renderPosY").getDouble(renderManager);
            double renderPosZ = renderManager.getClass().getField("renderPosZ").getDouble(renderManager);

            double x2 = entity.posX - renderPosX;
            double y2 = entity.posY + entity.height / 2.0F - renderPosY;
            double z2 = entity.posZ - renderPosZ;

            GL11.glBlendFunc(770, 771);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            setColor(color);
            GL11.glBegin(1);
            GL11.glVertex3d(0.0D, Minecraft.getMinecraft().player.getEyeHeight(), 0.0D);
            GL11.glVertex3d(x2, y2, z2);
            GL11.glEnd();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_BLEND);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

    public static void tracerLine(int x2, int y2, int z2, Color color) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        try {
            renderManager.getClass().getField("renderPosX").setAccessible(true);
            renderManager.getClass().getField("renderPosY").setAccessible(true);
            renderManager.getClass().getField("renderPosZ").setAccessible(true);
            double renderPosX = renderManager.getClass().getField("renderPosX").getDouble(renderManager);
            double renderPosY = renderManager.getClass().getField("renderPosY").getDouble(renderManager);
            double renderPosZ = renderManager.getClass().getField("renderPosZ").getDouble(renderManager);
            x2 = (int) (x2 + (0.5D - renderPosX));
            y2 = (int) (y2 + (0.5D - renderPosY));
            z2 = (int) (z2 + (0.5D - renderPosZ));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            return;
        }
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        setColor(color);
        GL11.glBegin(1);
        GL11.glVertex3d(0.0D, Minecraft.getMinecraft().player.getEyeHeight(), 0.0D);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawHLine(float par1, float par2, float par3, int par4) {
        if (par2 < par1) {
            float var5 = par1;
            par1 = par2;
            par2 = var5;
        }
        drawRect(par1, par3, par2 + 1.0F, par3 + 1.0F, par4);
    }

    public static void drawVLine(float par1, float par2, float par3, int par4) {
        if (par3 < par2) {
            float var5 = par2;
            par2 = par3;
            par3 = var5;
        }
        drawRect(par1, par2 + 1.0F, par1 + 1.0F, par3, par4);
    }

    public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3) {
        float f = (col1 >> 24 & 0xFF) / 255.0F;
        float f2 = (col1 >> 16 & 0xFF) / 255.0F;
        float f3 = (col1 >> 8 & 0xFF) / 255.0F;
        float f4 = (col1 & 0xFF) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(1.0F);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        drawGradientRect(x, y, x2, y2, col2, col3);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) {
        float f = (col1 >> 24 & 0xFF) / 255.0F;
        float f2 = (col1 >> 16 & 0xFF) / 255.0F;
        float f3 = (col1 >> 8 & 0xFF) / 255.0F;
        float f4 = (col1 & 0xFF) / 255.0F;
        float f5 = (col2 >> 24 & 0xFF) / 255.0F;
        float f6 = (col2 >> 16 & 0xFF) / 255.0F;
        float f7 = (col2 >> 8 & 0xFF) / 255.0F;
        float f8 = (col2 & 0xFF) / 255.0F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(7424);
    }

    public static void drawSidewaysGradientRect(double x, double y, double x2, double y2, int col1, int col2) {
        float f = (col1 >> 24 & 0xFF) / 255.0F;
        float f2 = (col1 >> 16 & 0xFF) / 255.0F;
        float f3 = (col1 >> 8 & 0xFF) / 255.0F;
        float f4 = (col1 & 0xFF) / 255.0F;
        float f5 = (col2 >> 24 & 0xFF) / 255.0F;
        float f6 = (col2 >> 16 & 0xFF) / 255.0F;
        float f7 = (col2 >> 8 & 0xFF) / 255.0F;
        float f8 = (col2 & 0xFF) / 255.0F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(7424);
    }

    public static void drawBorderedCircle(int x, int y, float radius, int outsideC, int insideC) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        float scale = 0.1F;
        GL11.glScalef(0.1F, 0.1F, 0.1F);
        x *= 10;
        y *= 10;
        radius *= 10.0F;
        drawCircle(x, y, radius, insideC);
        drawUnfilledCircle(x, y, radius, 1.0F, outsideC);
        GL11.glScalef(10.0F, 10.0F, 10.0F);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawUnfilledCircle(int x, int y, float radius, float lineWidth, int color) {
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glLineWidth(lineWidth);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(2);
        for (int i = 0; i <= 360; i++) {
            GL11.glVertex2d(x + Math.sin(i * 3.141526D / 180.0D) * radius, y + Math.cos(i * 3.141526D / 180.0D) * radius);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawCircle(int x, int y, float radius, int color) {
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(9);
        for (int i = 0; i <= 360; i++) {
            GL11.glVertex2d(x + Math.sin(i * 3.141526D / 180.0D) * radius, y + Math.cos(i * 3.141526D / 180.0D) * radius);
        }
        GL11.glEnd();
    }

    public static void drawCircle(float cx, float cy2, float r2, int num_segments, int c2) {
        cx *= 2.0F;
        cy2 *= 2.0F;
        float f2 = (c2 >> 24 & 0xFF) / 255.0F;
        float f1 = (c2 >> 16 & 0xFF) / 255.0F;
        float f22 = (c2 >> 8 & 0xFF) / 255.0F;
        float f3 = (c2 & 0xFF) / 255.0F;
        float theta = (float) (6.2831852D / num_segments);
        float p2 = (float) Math.cos(theta);
        float s = (float) Math.sin(theta);
        float x2 = r2 *= 2.0F;
        float y2 = 0.0F;
        enableGL2D();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glBegin(2);
        for (int ii2 = 0; ii2 < num_segments; ii2++) {
            GL11.glVertex2f(x2 + cx, y2 + cy2);
            float t = x2;
            x2 = p2 * x2 - s * y2;
            y2 = s * t + p2 * y2;
        }
        GL11.glEnd();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
    }

    public static void drawFullCircle(int cx, int cy2, double r2, int c2) {
        r2 *= 2.0D;
        cx *= 2;
        cy2 *= 2;
        float f2 = (c2 >> 24 & 0xFF) / 255.0F;
        float f1 = (c2 >> 16 & 0xFF) / 255.0F;
        float f22 = (c2 >> 8 & 0xFF) / 255.0F;
        float f3 = (c2 & 0xFF) / 255.0F;
        enableGL2D();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glBegin(6);
        for (int i2 = 0; i2 <= 360; i2++) {
            double x2 = Math.sin(i2 * 3.141592653589793D / 180.0D) * r2;
            double y2 = Math.cos(i2 * 3.141592653589793D / 180.0D) * r2;
            GL11.glVertex2d(cx + x2, cy2 + y2);
        }
        GL11.glEnd();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
    }

    public static void setColor(Color c2) {
        GL11.glColor4f(c2.getRed() / 255.0F, c2.getGreen() / 255.0F, c2.getBlue() / 255.0F, c2.getAlpha() / 255.0F);
    }

    public static double getAlphaFromHex(int color) {
        return (color >> 24 & 0xFF) / 255.0F;
    }

    public static double getRedFromHex(int color) {
        return (color >> 16 & 0xFF) / 255.0F;
    }

    public static double getGreenFromHex(int color) {
        return (color >> 8 & 0xFF) / 255.0F;
    }

    public static double getBlueFromHex(int color) {
        return (color & 0xFF) / 255.0F;
    }
}
