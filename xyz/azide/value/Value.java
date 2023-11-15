package xyz.azide.value;

import java.util.function.Supplier;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public class Value<T> {
    private final String name, description;
    private T value;
    private final Supplier<Boolean> supplier;

    protected Value(final String name, final String description, final T value, final Supplier<Boolean> supplier) {
        this.value = value;
        this.name = name;
        this.description = description;
        this.supplier = supplier;
    }

    protected Value(final String name, final String description, final T value) {
        this.value = value;
        this.name = name;
        this.description = description;
        this.supplier = null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public T getValue() {
        return value;
    }

    public void setValue(final T value) {
        this.value = value;
    }

    public boolean isVisible() {
        return supplier == null || supplier.get();
    }
}
