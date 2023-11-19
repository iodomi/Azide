package xyz.azide.module.impl.visual;

import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;

public class Animations extends Module {
    public Animations() {
        super("Animations", "Animates your swords blocking", ModuleCategory.VISUAL);
        setEnabled(true);
        onEnable = () -> {};
        onDisable = () -> {};
    }
}
