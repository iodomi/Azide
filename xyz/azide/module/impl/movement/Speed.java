package xyz.azide.module.impl.movement;

import org.lwjgl.input.Keyboard;
import xyz.azide.event.bus.Register;
import xyz.azide.event.impl.EventUpdate;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.util.move.MovementUtil;
import xyz.azide.value.impl.KeyValue;

import java.util.function.Consumer;

public final class Speed extends Module {
    public Speed() {
        super("Speed", "Lets you move faster", ModuleCategory.MOVEMENT);
        setKeybind(new KeyValue("Speed", null, Keyboard.KEY_G));

        onEnable = () -> {};
        onDisable = () -> {};
    }

    @Register
    private final Consumer<EventUpdate> onEventUpdate = event -> {
        if (MovementUtil.getMoving() && mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        } else if (MovementUtil.getMoving()) {
            MovementUtil.setMoving(1);
        }
    };
}
