package xyz.azide.module;

import xyz.azide.Azide;
import xyz.azide.value.impl.KeyValue;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public class Module {
    private final String name, description;
    private final ModuleCategory moduleCategory;
    private boolean enabled;
    private KeyValue keybind;
    protected Runnable onEnable;
    protected Runnable onDisable;

    protected Module(final String name, final String description, final ModuleCategory moduleCategory) {
        this.name = name;
        this.description = !description.isEmpty() ? description : name;
        this.moduleCategory = moduleCategory;
        this.keybind = getKeybind();
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public KeyValue getKeybind() {
        return keybind;
    }

    public void setKeybind(KeyValue key) {
        this.keybind = key;
    }

    public ModuleCategory getModuleCategory() {
        return moduleCategory;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            onEnable.run();
            Azide.getSingleton().getEventBus().register(this);
        } else {
            Azide.getSingleton().getEventBus().unregister(this);
            onDisable.run();
        }
    }
}
