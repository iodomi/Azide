package xyz.azide.event.bus;

import xyz.azide.event.Event;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.function.Consumer;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class Registrar {
    private final Field field;
    private final Class<?> type;
    private final Consumer<Event> consumer;

    Registrar(final Field field, final Object object) {
        this.field = field;
        type = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

        try {
            final boolean accessible = field.isAccessible();
            field.setAccessible(true);

            consumer = ((Consumer<Event>) field.get(object));

            field.setAccessible(accessible);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    void invoke(final Event event) {
        if (type.isAssignableFrom(event.getClass())) {
            final boolean accessible = field.isAccessible();
            field.setAccessible(true);

            consumer.accept(event);

            field.setAccessible(accessible);
        }
    }
}
