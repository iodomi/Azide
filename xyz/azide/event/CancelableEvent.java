package xyz.azide.event;

import xyz.azide.event.api.EventDirection;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public class CancelableEvent extends Event {
    private boolean cancelled;

    protected CancelableEvent(final EventDirection eventDirection) {
        super(eventDirection);
    }

    protected CancelableEvent() {

    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
