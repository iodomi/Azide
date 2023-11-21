package xyz.azide.module;

import xyz.azide.Azide;
import xyz.azide.module.api.ModuleCategory;
import xyz.azide.trait.Util;
import xyz.azide.trait.ValueContainer;
import xyz.azide.value.Value;
import xyz.azide.value.impl.KeyValue;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public class Module implements ValueContainer, Util {
    private final LinkedHashMap<String, Value<?>> stringValueMap = new LinkedHashMap<>();
    private final String name, description;
    private final ModuleCategory moduleCategory;
    private boolean enabled;
    private KeyValue keybind;
    protected Runnable onEnable;
    protected Runnable onDisable;

    protected Module(final String name, final String description, final ModuleCategory moduleCategory) {
        this.name = name;
        this.description = description.isEmpty() ? name : description;
        this.moduleCategory = moduleCategory;
        this.keybind = new KeyValue("Keybinding", 0);
    }

    protected Module(final String name, final ModuleCategory moduleCategory) {
        this.name = name;
        this.description = name;
        this.moduleCategory = moduleCategory;
        this.keybind = new KeyValue("Keybinding", 0);
    }

    public final void toggle() {
        setEnabled(!enabled);
    }

    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final KeyValue getKeybind() {
        return keybind;
    }

    public final void setKeybind(KeyValue key) {
        this.keybind.setValue(key.getValue());
    }

    public final ModuleCategory getModuleCategory() {
        return moduleCategory;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(final boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            Azide.getSingleton().getEventBus().register(this);
            onEnable.run();
        } else {
            onDisable.run();
            Azide.getSingleton().getEventBus().unregister(this);
        }
    }

    @Override
    public void register() {

    }

    @Override
    public final <T extends Value<?>> Value<T> get(final String name) {
        return (Value<T>) stringValueMap.get(name);
    }

    @Override
    public final Collection<Value<?>> getValues() {
        return stringValueMap.values();
    }
}
