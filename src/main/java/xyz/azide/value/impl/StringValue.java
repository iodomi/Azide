package xyz.azide.value.impl;

import xyz.azide.value.Value;

import java.util.function.Supplier;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class StringValue extends Value<String> {
    public StringValue(final String name, final String description, final String value, final Supplier<Boolean> supplier) {
        super(name, description, value, supplier);
    }

    public StringValue(final String name, final String description, final String value) {
        super(name, description, value);
    }
}