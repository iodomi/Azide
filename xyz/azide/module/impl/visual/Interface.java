package xyz.azide.module.impl.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Config;
import xyz.azide.Azide;
import xyz.azide.event.api.bus.Register;
import xyz.azide.event.impl.render.EventRender;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.ui.font.FontRenderer;

import java.awt.*;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Interface extends Module {
    public Interface() {
        super("Interface", ModuleCategory.VISUAL);
        onEnable = () -> {};
        onDisable = () -> {};
        setEnabled(true);
    }

    @Register
    private final Consumer<EventRender.Overlay> onRenderOverlay = event -> {
        final int color = new Color(137, 189, 249).getRGB();
        final FontRenderer font = Azide.getSingleton().getFontManager().poppins;

        font.drawStringWithShadow(Azide.getName().charAt(0)+ "\247f" + Azide.getName().substring(1), 3, 3, color);

        float y = 3;
        for (final Module module : Azide.getSingleton().getModuleManager().getModules().stream().filter(Module::isEnabled).sorted(Comparator.comparingDouble(module -> -font.getStringWidth(module.getName() + module.getSuffix()))).collect(Collectors.toList())) {
            final String arraylist = module.getName() + module.getSuffix();
            font.drawStringWithShadow(arraylist, event.getScaledResolution().getScaledWidth() - (font.getStringWidth(arraylist) + 3), y, color);
            y += font.getHeight() + 3;
        }
        String clientInfo = Minecraft.getDebugFPS() + "/" + Config.getFpsMin() + " fps, C: " + mc.renderGlobal.getCountActiveRenderers() + ", E: " + mc.renderGlobal.getCountEntitiesRendered() + "+" + mc.renderGlobal.getCountTileEntitiesRendered();
        font.drawString(clientInfo, 3, event.getScaledResolution().getScaledHeight()  - (font.getHeight() + 3), new Color(255,255,255,150).getRGB());
    };
}
