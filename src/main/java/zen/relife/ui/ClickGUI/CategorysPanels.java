package zen.relife.ui.ClickGUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zen.relife.Relife;
import zen.relife.manager.impl.FontManager;
import zen.relife.module.Category;
import zen.relife.module.Module;
import zen.relife.module.impl.render.ClickGui;
import zen.relife.ui.ClickGUI.module.ModulesPanels;
import zen.relife.ui.ClickGUI.module.setting.SettingPanel;
import zen.relife.util.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CategorysPanels {
    private final Category cat;
    private final TranslateUtil translate = new TranslateUtil(0.0f, 0.0f);
    private final TranslateUtil translate_ = new TranslateUtil(0.0f, 0.0f);
    public Category category;
    public int x;
    public int y;
    public int width;
    public int height;
    public int tempX;
    public int tempY;
    public int wheel;
    public ArrayList<ModulesPanels> modulesPanels;
    private boolean Move;
    private boolean hovered;
    private boolean open;
    private boolean show;
    private int posX;
    private int posY;

    public CategorysPanels(Category category, int x, int y, int width, int height, Category cat) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cat = cat;
        this.modulesPanels = new ArrayList();
        ArrayList<Module> modules = new ArrayList<Module>();
        modules.addAll(Relife.INSTANCE.getModuleManager().getModulesForCategory(this.category));
        for (Module module : modules) {
            this.modulesPanels.add(new ModulesPanels(module));
            System.out.println(this.modulesPanels.size());
        }
        this.translate.setX(0.0f);
        this.translate.setY(0.0f);
        this.translate_.setX(0.0f);
        this.translate_.setY(0.0f);
    }

    public static void startGlScissor(int x, int y, int width, int height) {
        int scaleFactor = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        GL11.glScissor(x * scaleFactor, Minecraft.getMinecraft().displayHeight - (y + height) * scaleFactor, width * scaleFactor, (height += 14) * scaleFactor);
    }

    public static void stopGlScissor() {
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isShow() {
        return this.show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.hovered = ClickGUIUtils.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
        this.width = this.show ? 89 : 85;
        if (this.Move && ClickGUI.categoryName.equalsIgnoreCase(this.category.name())) {
            this.x = this.tempX + mouseX;
            this.y = this.tempY + mouseY;
            this.posX = posX;
            this.posY = posY;
            float relife;
        }

        RenderUtil.drawBlurredShadow(2 - 20, 2 - 20, 40, 40, 60, new Color(0xD9E2EF, false));
        int color = new Color(201, 99, 239, 226).getRGB();
        Helper2D.drawRoundedRectangle(x, y, width, height, 4, new Color(29, 29, 29).getRGB(), true, 0);

        if (ClickGui.useFont.getEnable()) {
            FontManager.F16.drawString(this.category.name(), this.x + 5, this.y + this.height / 2 - 3, Color.WHITE.getRGB());
        } else {
            FontUtil.drawString(this.category.name(), this.x + 5, this.y + this.height / 2 - 4, Color.WHITE.getRGB());
        }
        if (this.open) {
            int var7_11 = 0;
            int modulePanelY = 0;
            for (ModulesPanels modulesPanels : this.modulesPanels) {
                if (modulesPanels.showSetting) {
                    modulePanelY += modulesPanels.getSettingY();
                }
                for (SettingPanel it : modulesPanels.settingPanelList) {
                    if (!it.isModedr() || !modulesPanels.showSetting) continue;
                    modulePanelY += it.getModeY();
                }
                modulePanelY += 16;
            }
            if (ClickGui.Element.getCurrent() != ClickGui.Element.getMax()) {
                CategorysPanels.startGlScissor(this.x, this.y + this.height + 14, this.width, (int) ClickGui.Element.getCurrent() * 16 - 14);
            } else {
                CategorysPanels.startGlScissor(this.x, this.y + this.height + 14, this.width, modulePanelY);
            }
            modulePanelY = ClickGui.Element.getCurrent() != ClickGui.Element.getMax() ? this.y + this.height + (int) this.translate.getY() : this.y + this.height;
            int rainbowTickc = 0;
            for (ModulesPanels modulesPanels : this.modulesPanels) {
                if (++rainbowTickc > 100) {
                    rainbowTickc = 0;
                }
                Color rainbow = new Color(Color.HSBtoRGB((float) ((double) Minecraft.getMinecraft().player.ticksExisted / ClickGui.rainBowSpeed.getCurrent() - Math.sin((double) rainbowTickc / 50.0 * 1.4)) % 1.0f, 0.5f, 1.0f));
                modulesPanels.drawScreen(mouseX, mouseY, partialTicks, this.x, modulePanelY, this.width, 16, modulePanelY + this.wheel, this.y, modulePanelY - this.y - 16, ClickGui.rainBow.getEnable() ? rainbow : new Color((int) ClickGui.red.getCurrent(), (int) ClickGui.green.getCurrent(), (int) ClickGui.blue.getCurrent()));
                if (modulesPanels.showSetting) {
                    modulePanelY += modulesPanels.getSettingY();
                }
                for (SettingPanel it : modulesPanels.settingPanelList) {
                    if (!it.isModedr() || !modulesPanels.showSetting) continue;
                    modulePanelY += it.getModeY();
                }
                modulePanelY += 16;
            }
            CategorysPanels.stopGlScissor();
            boolean bl = false;
            for (ModulesPanels modulesPanels : this.modulesPanels) {
                var7_11 += 16;
                if (modulesPanels.showSetting) {
                    var7_11 += modulesPanels.getSettingY();
                }
                for (SettingPanel it : modulesPanels.settingPanelList) {
                    if (!it.isModedr() || !modulesPanels.showSetting) continue;
                    var7_11 += it.getModeY();
                }
            }
            this.show = false;
            int n = (int) (ClickGui.Element.getCurrent() / (double) (var7_11 / 16) * (ClickGui.Element.getCurrent() * 16.0));
            if (var7_11 > (int) ClickGui.Element.getCurrent() * 16 && ClickGui.Element.getCurrent() != ClickGui.Element.getMax()) {
                this.show = true;
                Helper2D.drawRoundedRectangle(x, y + 19 - (int) this.translate.getY() * (int) ClickGui.Element.getCurrent(), width - 2, height, 4, new Color(201, 99, 239, 226).getRGB(), true, 0);

            }
            if (ClickGui.Element.getCurrent() != ClickGui.Element.getMax() && ClickGUIUtils.isHovered(mouseX, mouseY, this.x, this.y + 16, this.width - 4, (int) ClickGui.Element.getCurrent() * 16) && this.open && Mouse.hasWheel()) {
                int wheel_ = Mouse.getDWheel();
                if (wheel_ < 0) {
                    if (this.wheel < (int) ClickGui.Element.getCurrent() * 16 - var7_11 + 16) {
                        return;
                    }
                    this.wheel -= 16;
                } else if (wheel_ > 0) {
                    if (this.wheel > -16) {
                        return;
                    }
                    this.wheel += 16;
                }
            }
            if (ClickGui.Element.getCurrent() != ClickGui.Element.getMax()) {
                this.translate.interpolate(0.0f, this.wheel, 0.15f);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.hovered && mouseButton == 0) {
            ClickGUI.categoryName = this.category.name();
            this.Move = true;
            this.tempX = this.x - mouseX;
            this.tempY = this.y - mouseY;
        }
        if (this.hovered && mouseButton == 1) {
            ClickGUI.categoryNames = this.category.name();
            this.open = !this.open;
        } else if (mouseButton == 1) {
            boolean bl = false;
        }
        for (ModulesPanels modulesPanels : this.modulesPanels) {
            for (SettingPanel it : modulesPanels.settingPanelList) {
                it.mouseClicked1(mouseX, mouseY, mouseButton);
            }
        }
        for (ModulesPanels modulesPanels : this.modulesPanels) {
            modulesPanels.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            this.Move = false;
        }
        for (ModulesPanels modulesPanels : this.modulesPanels) {
            modulesPanels.mouseReleased(mouseX, mouseY, state);
        }
    }
}

