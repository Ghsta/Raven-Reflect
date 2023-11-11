package zen.relife.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;


public interface Utils {
    Minecraft mc = Minecraft.getMinecraft();
    FontRenderer fr = mc.fontRenderer;

    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder worldrenderer = tessellator.getBuffer();

    FontUtil.FontType tenacityFont = FontUtil.FontType.TENACITY,
            iconFont = FontUtil.FontType.ICON,
            neverloseFont = FontUtil.FontType.NEVERLOSE,
            tahomaFont = FontUtil.FontType.TAHOMA,
            rubikFont = FontUtil.FontType.RUBIK;


    //Regular Fonts
    CustomFont tenacityFont12 = tenacityFont.size(12),
            tenacityFont14 = tenacityFont.size(14),
            tenacityFont16 = tenacityFont.size(16),
            tenacityFont18 = tenacityFont.size(18),
            tenacityFont20 = tenacityFont.size(20),
            tenacityFont22 = tenacityFont.size(22),
            tenacityFont24 = tenacityFont.size(24),
            tenacityFont26 = tenacityFont.size(26),
            tenacityFont28 = tenacityFont.size(28),
            tenacityFont32 = tenacityFont.size(32),
            tenacityFont40 = tenacityFont.size(40),
            tenacityFont80 = tenacityFont.size(80);

    //Bold Fonts
    CustomFont tenacityBoldFont12 = tenacityFont12,
            tenacityBoldFont14 = tenacityFont14,
            tenacityBoldFont16 = tenacityFont16,
            tenacityBoldFont18 = tenacityFont18,
            tenacityBoldFont20 = tenacityFont20,
            tenacityBoldFont22 = tenacityFont22,
            tenacityBoldFont24 = tenacityFont24,
            tenacityBoldFont26 = tenacityFont26,
            tenacityBoldFont28 = tenacityFont28,
            tenacityBoldFont32 = tenacityFont32,
            tenacityBoldFont40 = tenacityFont40,
            tenacityBoldFont80 = tenacityFont80;

    //Icon Fontsor i
    CustomFont iconFont16 = iconFont.size(16),
            iconFont20 = iconFont.size(20),
            iconFont26 = iconFont.size(26),
            iconFont35 = iconFont.size(35),
            iconFont40 = iconFont.size(40);


}
