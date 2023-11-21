package xyz.azide.event.impl.player;

import xyz.azide.event.CancelableEvent;

public final class EventChat {
    public static final class Send extends CancelableEvent {
        private String message;

        public Send(final String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }
    }
}
