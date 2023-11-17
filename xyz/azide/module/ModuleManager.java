package xyz.azide.module;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import org.reflections.Reflections;
import xyz.azide.Azide;
import xyz.azide.event.bus.Register;
import xyz.azide.event.impl.EventKey;
import xyz.azide.module.impl.movement.Sprint;
import xyz.azide.trait.IManager;
import xyz.azide.value.Value;
import xyz.azide.value.ValueContainer;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class ModuleManager implements IManager {
    private final ClassToInstanceMap<Module> classModuleMap;

    public ModuleManager() {
        classModuleMap = MutableClassToInstanceMap.create();
    }

    @Override
    public void initialize() {
        Azide.getSingleton().getEventBus().register(this);

        final Set<Class<? extends Module>> classSet = new Reflections().getSubTypesOf(Module.class);

        if (!classSet.isEmpty()) {
            for (final Class<? extends Module> clazz : classSet) {
                try {
                    Module moduleInstance = clazz.getDeclaredConstructor().newInstance();
                    classModuleMap.put(clazz, moduleInstance);
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Register
    private final Consumer<EventKey> onKeyPressEvent = event -> {
        for (final Module module : getModules()) {
            ValueContainer valueContainer = Azide.getSingleton().getValueManager().getModuleValueContainerMap().get(module.getClass());

            if (valueContainer != null) {
                Value<?> keybindValue = valueContainer.getKeyValue("keybind");

                if (keybindValue != null && keybindValue.getValue() != null && keybindValue.getValue().equals(event.getKey())) {
                    module.setEnabled(!module.isEnabled());
                }
            }
        }
    };

    public Module getModule(final Class<?> clazz) {
        return classModuleMap.get(clazz);
    }

    public Collection<Module> getModules() {
        return classModuleMap.values();
    }
}
