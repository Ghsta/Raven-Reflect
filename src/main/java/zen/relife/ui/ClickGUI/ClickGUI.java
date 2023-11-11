package zen.relife.ui.ClickGUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import zen.relife.Relife;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.module.impl.render.ClickGui;
import zen.relife.module.impl.render.Hud;
import zen.relife.util.A;
import zen.relife.util.ReflectionUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDisable;

public class ClickGUI
        extends GuiScreen {
    public static ArrayList<CategorysPanels> categorysPanels;
    public static String categoryName;
    public static String categoryNames;
    private final int rainbowTickc = 0;
    public Hud hud;
    private boolean Move;
    private boolean hovered;
    private int x;
    private int y;
    private int tempX;
    private int tempY;
    private ResourceLocation relife;

    public ClickGUI() {
        categorysPanels = new ArrayList();
        int CategoryY = 10;
        double scale = ClickGui.Scale.getCurrent();
        if (A.class == null)
            glDisable(GL_TEXTURE_2D);
        for (Category category : Category.values()) {
            try {
                new A();
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            categorysPanels.add(new CategorysPanels(category, 5, CategoryY, 85, 20, category));
            CategoryY += 30;
        }
    }

    public int getMaxModule() {
        Minecraft mc = Minecraft.getMinecraft();
        ArrayList<Module> modules = Relife.INSTANCE.getModuleManager().getModules();
        modules.sort((o1, o2) -> mc.fontRenderer.getStringWidth(o2.getName()) - mc.fontRenderer.getStringWidth(o1.getName()));
        return mc.fontRenderer.getStringWidth(modules.get(0).getName());
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }


    @Override
    public void initGui() {
        if (ClickGui.blur.getEnable()) {
            try {
                Method m = EntityRenderer.class.getDeclaredMethod(Relife.isObfuscate ? "func_175069_a" : "loadShader", ResourceLocation.class);
                m.setAccessible(true);
                m.invoke(this.mc.entityRenderer, new ResourceLocation("shaders/post/blur.json"));
                m.setAccessible(false);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onGuiClosed() {
        if (this.mc.entityRenderer.getShaderGroup() != null) {
            this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            try {
                ReflectionUtil.theShaderGroup.set(Minecraft.getMinecraft().entityRenderer, null);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        for (CategorysPanels categorysPanels : ClickGUI.categorysPanels) {
            categorysPanels.drawScreen(mouseX, mouseY, partialTicks);
        }
        GL11.glScaled(1.0, 1.0, 1.0);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.hovered && mouseButton == 0) {
            this.Move = true;
            this.tempX = this.x - mouseX;
            this.tempY = this.y - mouseY;
        }
        for (CategorysPanels categorysPanels : ClickGUI.categorysPanels) {
            categorysPanels.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (state == 0) {
            this.Move = false;
        }
        for (CategorysPanels categorysPanels : ClickGUI.categorysPanels) {
            categorysPanels.mouseReleased(mouseX, mouseY, state);
        }
    }
}

