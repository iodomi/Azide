package xyz.azide.value.impl;

import xyz.azide.value.Value;

import java.util.function.Supplier;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class KeyValue extends Value<Integer> {
    public KeyValue(final String name, final Integer value, final Supplier<Boolean> supplier) {
        super(name, value, supplier);
    }

    public KeyValue(final String name, final Integer value) {
        super(name, value);
    }
}