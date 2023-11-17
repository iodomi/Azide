package xyz.azide.module.impl.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import xyz.azide.event.bus.Register;
import xyz.azide.event.impl.EventUpdate;
import xyz.azide.module.Module;
import xyz.azide.module.ModuleCategory;
import xyz.azide.value.impl.KeyValue;

import javax.swing.text.JTextComponent;
import java.security.Key;
import java.util.function.Consumer;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Automatically sprints for you", ModuleCategory.MOVEMENT);
        setEnabled(true);
    }

    @Register
    private final Consumer<EventUpdate> onEventUpdate = event -> {
        Minecraft.getMinecraft().thePlayer.setSprinting(true);
        System.out.println(true);
    };
}
