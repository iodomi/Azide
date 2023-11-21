package xyz.azide.trait;

import xyz.azide.value.Value;
import java.util.Collection;

/**
 * @author plusbox
 * @since 11/6/2023
 * @version 4.0
 */
public interface ValueContainer {
    void register();

    <T extends Value<?>> Value<?> get(final String name);

    Collection<Value<?>> getValues();
}
