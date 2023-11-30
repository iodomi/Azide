package xyz.azide.ui.clickgui.impl;

import net.minecraft.client.gui.Gui;
import xyz.azide.Azide;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.ui.Component;
import xyz.azide.ui.font.FontRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public final class CategoryPanel extends Component {
    private final FontRenderer font = Azide.getSingleton().getFontManager().montserrat;
    private final ArrayList<ModuleComponent> componentList = new ArrayList<>();
    private final ModuleCategory moduleCategory;

    public CategoryPanel(final double x, final double y, final double width, final double height, final ModuleCategory moduleCategory) {
        super(x, y, width, height);
        this.moduleCategory = moduleCategory;

        for (final Module module : Azide.getSingleton().getModuleManager().getModules().stream().filter(module -> module.getModuleCategory() == moduleCategory).collect(Collectors.toList())) {
            componentList.add(new ModuleComponent(module));
        }
    }

    @Override
    public void draw(final double x, final double y, final double width, final double height) {
        Gui.drawRect((int) x, (int) y, (int) (x + width), (int) (y + height), new Color(20, 20, 20).getRGB());

        final String name = moduleCategory.getName();
        font.drawStringWithShadow(name, (float) (x + font.getStringWidth(name)), (float) (y + font.getHeight()), -1);

        double componentY = y + height;
        for (final ModuleComponent moduleComponent : componentList) {
            moduleComponent.draw(x, componentY, width, height);
            componentY += height;
        }
    }

    public ModuleCategory getModuleCategory() {
        return moduleCategory;
    }
}