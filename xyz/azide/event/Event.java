package xyz.azide.event;

/**
 * @author plusbox
 * @since 11/14/2023
 * @version 1.0
 */
public class Event {
    private final EventDirection eventDirection;

    protected Event(final EventDirection eventDirection) {
        this.eventDirection = eventDirection;
    }

    protected Event() {
        eventDirection = EventDirection.PRE;
    }

    public EventDirection getEventDirection() {
        return eventDirection;
    }
}
