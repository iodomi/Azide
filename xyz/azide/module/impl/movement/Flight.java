package xyz.azide.module.impl.movement;

import org.lwjgl.input.Keyboard;
import xyz.azide.event.api.bus.Register;
import xyz.azide.event.impl.player.EventUpdate;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.module.impl.visual.Animations;
import xyz.azide.util.move.MovementUtil;
import xyz.azide.value.impl.EnumValue;
import xyz.azide.value.impl.KeyValue;
import xyz.azide.value.impl.NumberValue;

import java.util.function.Consumer;

public final class Flight extends Module {
    public final EnumValue mode = new EnumValue("Mode", Mode.VANILLA);
    public final NumberValue speed = new NumberValue("Speed", 2.5, 0.1, 10, 0.1);

    public Flight() {
        super("Flight", "Lets you fly", ModuleCategory.MOVEMENT);
        setKeybind(new KeyValue("Keybinding", Keyboard.KEY_F));
        setSuffix(mode.getFormattedValue());
        onEnable = () -> {};
        onDisable = MovementUtil::stop;
    }

    @Register
    private final Consumer<EventUpdate> onEventUpdate = event -> {
        mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? speed.getValue().doubleValue() : mc.thePlayer.movementInput.sneak ? -speed.getValue().doubleValue() : 0;
        if (MovementUtil.getMoving())
            MovementUtil.setMoving(speed.getValue().doubleValue());
        else
            MovementUtil.stop();
    };

     public enum Mode {
         VANILLA
     }
}
