package xyz.azide.module.impl.visual;

import org.lwjgl.input.Keyboard;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.ui.clickgui.ClickGUIScreen;
import xyz.azide.value.impl.KeyValue;

public class ClickGUI extends Module {
    private ClickGUIScreen clickGUIScreen;

    public ClickGUI() {
        super("ClickGUI", "", ModuleCategory.VISUAL);
        setKeybind(new KeyValue("Keybinding", Keyboard.KEY_RSHIFT));
        onEnable = () -> {
            if (clickGUIScreen == null) {
            clickGUIScreen = new ClickGUIScreen();
            }

            if(!(mc.currentScreen instanceof ClickGUIScreen)) {
                mc.displayGuiScreen(clickGUIScreen);
                if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
                    onDisable.run();
                }
            }
        };
        onDisable = () -> {};
    }
}
