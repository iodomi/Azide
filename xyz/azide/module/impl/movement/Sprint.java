package xyz.azide.module.impl.movement;

import xyz.azide.module.Module;
import xyz.azide.module.ModuleCategory;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Automatically sprints for you", ModuleCategory.MOVEMENT);
        setEnabled(true);
    }

}
