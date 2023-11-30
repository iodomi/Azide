package xyz.azide.module.impl.visual;

import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.value.impl.BooleanValue;
import xyz.azide.value.impl.EnumValue;
import xyz.azide.value.impl.NumberValue;
import xyz.azide.value.impl.StringValue;

import java.awt.Color;

public class Animations extends Module {
    public final EnumValue animationType = new EnumValue("Animation Type", AnimationType.AZIDE);

    public Animations() {
        super("Animations", "Animates your swords blocking", ModuleCategory.VISUAL);
        setSuffix(animationType.getFormattedValue());
        onEnable = () -> {};
        onDisable = () -> {};
    }

    public enum AnimationType {
        OLD,
        AZIDE,
        EXHIBITION,
        SWING
    }
}
