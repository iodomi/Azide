package xyz.azide.event.impl;

import xyz.azide.event.CancelableEvent;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class KeyPressEvent extends CancelableEvent  {
    private int key;

    public KeyPressEvent(final int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(final int key) {
        this.key = key;
    }
}
