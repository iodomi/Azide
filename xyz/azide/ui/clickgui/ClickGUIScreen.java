package xyz.azide.ui.clickgui;

import net.minecraft.client.gui.GuiScreen;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.ui.clickgui.impl.CategoryPanel;
import xyz.azide.util.math.MathUtil;

import java.util.ArrayList;

public final class ClickGUIScreen extends GuiScreen {
    private final ArrayList<CategoryPanel> panelList = new ArrayList<>();

    @Override
    public void initGui() {
        if (panelList.isEmpty()) {
            double x = MathUtil.center(width, (130 * ModuleCategory.values().length) - 30);
            for (final ModuleCategory moduleCategory : ModuleCategory.values()) {
                panelList.add(new CategoryPanel(x, 60, 100, 18, moduleCategory));
                x += 130;
            }
        }
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        for (final CategoryPanel categoryPanel : panelList) {
            categoryPanel.draw(categoryPanel.getX(), categoryPanel.getY(), categoryPanel.getWidth(), categoryPanel.getHeight());
        }
    }
}