package xyz.azide.module;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import org.reflections.Reflections;
import xyz.azide.Azide;
import xyz.azide.event.bus.Register;
import xyz.azide.event.impl.KeyPressEvent;
import xyz.azide.trait.IManager;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
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
                    classModuleMap.put(clazz, clazz.getDeclaredConstructor().newInstance());
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Register
    private final Consumer<KeyPressEvent> onKeyPressEvent = event -> {
        for (final Module module : getModules()) {
            if (event.getKey() == Azide.getSingleton().getValueManager().getModuleValueContainerMap().get(module.getClass()).getKeyValue("Keybind").getValue()) {
                module.setEnabled(!module.isEnabled());
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
