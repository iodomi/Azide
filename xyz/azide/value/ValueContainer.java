package xyz.azide.value;

import xyz.azide.value.impl.*;

import java.util.HashMap;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class ValueContainer {
    private final HashMap<String, Value<?>> stringValueMap;

    public ValueContainer() {
        stringValueMap = new HashMap<>();
    }

    public Value<?> getValue(final String name) {
        return stringValueMap.get(name);
    }

    public BooleanValue getBooleanValue(final String name) {
        return (BooleanValue) stringValueMap.get(name);
    }

    public ColorValue getColorValue(final String name) {
        return (ColorValue) stringValueMap.get(name);
    }

    public EnumValue getEnumValue(final String name) {
        return (EnumValue) stringValueMap.get(name);
    }

    public KeyValue getKeyValue(final String name) {
        return (KeyValue) stringValueMap.get(name);
    }

    public NumberValue getNumberValue(final String name) {
        return (NumberValue) stringValueMap.get(name);
    }

    public StringValue getStringValue(final String name) {
        return (StringValue) stringValueMap.get(name);
    }

    HashMap<String, Value<?>> getStringValueMap() {
        return stringValueMap;
    }
}
