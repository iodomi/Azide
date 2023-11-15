package xyz.azide.event.bus;

import xyz.azide.event.Event;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class EventBus {
    private final ConcurrentHashMap<Class<?>, Registrar> classRegistrarMap;

    public EventBus() {
        classRegistrarMap = new ConcurrentHashMap<>();
    }

    public void register(final Object object) {
        final Class<?> clazz = object.getClass();

        if (!classRegistrarMap.containsKey(clazz)) {
            for (final Field field : clazz.getDeclaredFields()) {
                if (Consumer.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(Register.class)) {
                    classRegistrarMap.put(clazz, new Registrar(field, object));
                }
            }
        }
    }

    public void unregister(final Object object) {
        classRegistrarMap.remove(object.getClass());
    }

    public <T extends Event> void dispatch(final T event) {
        for (final Registrar registrar : classRegistrarMap.values()) {
            registrar.invoke(event);
        }
    }
}
