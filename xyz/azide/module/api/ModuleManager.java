package xyz.azide.module.api;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import org.reflections.Reflections;
import xyz.azide.Azide;
import xyz.azide.event.api.bus.Register;
import xyz.azide.event.impl.player.EventKey;
import xyz.azide.module.Module;
import xyz.azide.trait.Manager;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class ModuleManager implements Manager {
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
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Register
    private final Consumer<EventKey> onKeyPress = event -> {
        for (final Module module : getModules()) {
            if (event.getKey() == module.getKeybind().getValue()) {
                module.toggle();
            }
        }
    };

    public Module getModule(final Class<?> clazz) {
        return classModuleMap.get(clazz);
    }

    public Module getModule(final String name) {
        for (final Module module : getModules()) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }


    public Collection<Module> getModules() {
        return classModuleMap.values();
    }
}