package zen.relife.util;


import zen.relife.render.font.GlyphPageFontRenderer;

public class FontHelper {

    public GlyphPageFontRenderer size15;
    public GlyphPageFontRenderer size20;
    public GlyphPageFontRenderer size30;
    public GlyphPageFontRenderer size40;
    private String font;

    public void init() {
        int doublef = 0;
        size15 = GlyphPageFontRenderer.create(font, 15, true, true, true);
        size20 = GlyphPageFontRenderer.create(font, 20, true, true, true);
        size30 = GlyphPageFontRenderer.create(font, 30, true, true, true);
        size40 = GlyphPageFontRenderer.create(font, 40, true, true, true);
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}
