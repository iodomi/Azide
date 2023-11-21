package xyz.azide.module.impl.movement;

import org.lwjgl.input.Keyboard;
import xyz.azide.event.api.bus.Register;
import xyz.azide.event.impl.player.EventUpdate;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.util.move.MovementUtil;
import xyz.azide.value.impl.KeyValue;

import java.util.function.Consumer;

public final class Flight extends Module {
    public Flight() {
        super("Flight", "Lets you fly", ModuleCategory.MOVEMENT);
        setKeybind(new KeyValue("Keybinding", Keyboard.KEY_F));

        onEnable = () -> {};
        onDisable = MovementUtil::stop;
    }

    @Register
    private final Consumer<EventUpdate> onEventUpdate = event -> {
        mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? 1 : mc.thePlayer.movementInput.sneak ? -1 : 0;
        if (MovementUtil.getMoving())
            MovementUtil.setMoving(1);
        else
            MovementUtil.stop();
    };
}
