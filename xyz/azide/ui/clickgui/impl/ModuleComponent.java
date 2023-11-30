package xyz.azide.ui.clickgui.impl;

import net.minecraft.client.gui.Gui;
import xyz.azide.Azide;
import xyz.azide.module.Module;
import xyz.azide.ui.Component;
import xyz.azide.ui.font.FontRenderer;
import xyz.azide.util.math.MathUtil;

import java.awt.*;

public final class ModuleComponent extends Component {
    private final FontRenderer font = Azide.getSingleton().getFontManager().montserrat;
    private final Module module;

    public ModuleComponent(final Module module) {
        super(0, 0);
        this.module = module;
    }

    @Override
    public void draw(final double x, final double y, final double width, final double height) {
        Gui.drawRect((int) x, (int) y, (int) (x + width), (int) (y + height), new Color(30, 30, 30).getRGB());
        font.drawStringWithShadow(module.getName(), (float) (x + MathUtil.center(width, font.getStringWidth(module.getName()))), (float) (y + MathUtil.center(height, font.getHeight())), module.isEnabled() ? Color.RED.getRGB() : -1);
    }
}