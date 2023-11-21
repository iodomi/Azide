package xyz.azide.value.impl;

import xyz.azide.value.Value;

import java.util.function.Supplier;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class NumberValue extends Value<Number> {
    private Number min, max, increment;

    public NumberValue(final String name, final Number value, final Number min, final Number max, final Number increment, final Supplier<Boolean> supplier) {
        super(name, value, supplier);
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public NumberValue(final String name, final Number value, final Number min, final Number max, final Number increment) {
        super(name, value);
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public Number getMin() {
        return min;
    }

    public void setMin(final Number min) {
        this.min = min;
    }

    public Number getMax() {
        return max;
    }

    public void setMax(final Number max) {
        this.max = max;
    }

    public Number getIncrement() {
        return increment;
    }

    public void setIncrement(final Number increment) {
        this.increment = increment;
    }
}