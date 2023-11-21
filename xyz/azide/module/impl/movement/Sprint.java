package xyz.azide.module.impl.movement;

import org.lwjgl.input.Keyboard;
import xyz.azide.event.api.bus.Register;
import xyz.azide.event.impl.player.EventUpdate;
import xyz.azide.module.Module;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.util.move.MovementUtil;
import xyz.azide.value.impl.KeyValue;

import java.util.function.Consumer;

public final class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Automatically sprints for you", ModuleCategory.MOVEMENT);
        setKeybind(new KeyValue("Keybinding", Keyboard.KEY_LCONTROL));
        onEnable = () -> {};
        onDisable = () -> mc.thePlayer.setSprinting(false);
    }

    @Register
    private final Consumer<EventUpdate> onEventUpdate = event -> {
        if (MovementUtil.getMoving()) {
            mc.thePlayer.setSprinting(true);
        }
    };
}
