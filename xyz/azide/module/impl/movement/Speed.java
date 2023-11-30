package xyz.azide.module.impl.movement;

import org.lwjgl.input.Keyboard;
import xyz.azide.event.api.bus.Register;
import xyz.azide.event.impl.player.EventUpdate;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.util.move.MovementUtil;
import xyz.azide.value.impl.EnumValue;
import xyz.azide.value.impl.KeyValue;
import xyz.azide.value.impl.NumberValue;

import java.util.function.Consumer;

public final class Speed extends Module {
    public final EnumValue mode = new EnumValue("Mode", Mode.VANILLA);
    public final NumberValue speed = new NumberValue("Speed", 1, 0.1, 10, 0.1);

    public Speed() {
        super("Speed", "Lets you move faster", ModuleCategory.MOVEMENT);
        setKeybind(new KeyValue("Keybinding", Keyboard.KEY_G));
        setSuffix(mode.getFormattedValue());
        onEnable = () -> {};
        onDisable = () -> {};
    }

    @Register
    private final Consumer<EventUpdate> onEventUpdate = event -> {
        switch (mode.getFormattedValue()){
            case "Vanilla":
                if (MovementUtil.getMoving() && mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                } else if (MovementUtil.getMoving()) {
                    MovementUtil.setMoving(speed.getValue().doubleValue());
                }
                break;
            case "Strafe":
                if (MovementUtil.getMoving() && mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                } else if (MovementUtil.getMoving()) {
                    MovementUtil.strafe();
                }
                break;
        }
    };

    public enum Mode {
        VANILLA,
        STRAFE
    }
}
