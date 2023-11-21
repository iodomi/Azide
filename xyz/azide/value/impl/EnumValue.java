package xyz.azide.value.impl;

import xyz.azide.value.Value;

import java.util.function.Supplier;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class EnumValue extends Value<Enum<?>> {
    public EnumValue(final String name, final Enum<?> value, final Supplier<Boolean> supplier) {
        super(name, value, supplier);
    }

    public EnumValue(final String name, final Enum<?> value) {
        super(name, value);
    }

    public String getFormattedValue() {
        final String unformattedName = getValue().toString();
        return unformattedName.charAt(0) + unformattedName.substring(1).toLowerCase();
    }

    public void cycle(final boolean next) {
        final Enum<?> value = getValue();
        final Enum<?>[] constants = value.getDeclaringClass().getEnumConstants();

        int i = value.ordinal() + (next ? 1 : -1);

        if ((next && i >= constants.length) || (!next && i < 0)) {
            i = next ? 0 : constants.length - 1;
        }

        setValue(constants[i]);
    }
}