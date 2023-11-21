package xyz.azide.event.impl.player;

import xyz.azide.event.CancelableEvent;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public final class EventKey extends CancelableEvent  {
    private int key;

    public EventKey(final int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(final int key) {
        this.key = key;
    }
}
