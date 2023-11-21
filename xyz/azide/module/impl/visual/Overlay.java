package xyz.azide.module.impl.visual;

import xyz.azide.Azide;
import xyz.azide.event.api.bus.Register;
import xyz.azide.event.impl.render.EventRender;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.value.impl.BooleanValue;
import xyz.azide.value.impl.KeyValue;

import java.awt.*;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Overlay extends Module {
    private final BooleanValue test = new BooleanValue("Test", true);
    public String watermarkName = Azide.getName();

    public Overlay() {
        super("Overlay", ModuleCategory.VISUAL);
        onEnable = () -> {};
        onDisable = () -> {};
        setEnabled(true);
    }

    @Register
    private final Consumer<EventRender.Overlay> onRenderOverlay = event -> {
        final int color = Color.WHITE.getRGB();
        mc.fontRendererObj.drawStringWithShadow(watermarkName, 3, 3, color);

        float y = 3;
        for (final Module module : Azide.getSingleton().getModuleManager().getModules().stream().filter(Module::isEnabled).sorted(Comparator.comparingDouble(module -> -mc.fontRendererObj.getStringWidth(module.getName()))).collect(Collectors.toList())) {
            final String name = module.getName();
            mc.fontRendererObj.drawStringWithShadow(name, event.getScaledResolution().getScaledWidth() - (mc.fontRendererObj.getStringWidth(name) + 3), y, color);
            y += mc.fontRendererObj.FONT_HEIGHT + 3;
        }
    };

    public String getWatermarkName() {
        return watermarkName;
    }

    public void setWatermarkName(String watermarkName) {
        this.watermarkName = watermarkName;
    }
}
