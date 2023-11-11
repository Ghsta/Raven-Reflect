package zen.relife.render.font;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public abstract class FontLoaders {
    public static EnglishFontRenderer arial18;
    public static EnglishFontRenderer arial17;
    public static EnglishFontRenderer arial16;
    public static EnglishFontRenderer arial15;
    public static EnglishFontRenderer GG18;
    public static EnglishFontRenderer GG16;

    static {
        FontLoaders.arial18 = new EnglishFontRenderer(getArial(18), true, true);
        FontLoaders.arial17 = new EnglishFontRenderer(getArial(17), true, true);
        FontLoaders.arial16 = new EnglishFontRenderer(getArial(16), true, true);
        FontLoaders.arial15 = new EnglishFontRenderer(getArial(15), true, true);
        FontLoaders.GG18 = new EnglishFontRenderer(getGG(18), true, true);
        FontLoaders.GG16 = new EnglishFontRenderer(getGG(16), false, false);
    }

    private static Font getArial(final int size) {
        Font font;
        try {
            final File file = new File("C:\\Users\\Administrator\\AppData\\Roaming\\Relife\\resource\\fonts\\Arial.ttf");
            final InputStream is = new FileInputStream(file);
            font = Font.createFont(0, is);
            font = font.deriveFont(0, (float) size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static Font getGG(final int size) {
        Font font;
        try {
            final File file = new File("C:\\Users\\Administrator\\AppData\\Roaming\\Relife\\resource\\fonts\\tahoma-bold.ttf");
            final InputStream is = new FileInputStream(file);
            font = Font.createFont(0, is);
            font = font.deriveFont(0, (float) size);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
}
