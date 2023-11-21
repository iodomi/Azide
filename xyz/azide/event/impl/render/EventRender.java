package xyz.azide.event.impl.render;

import net.minecraft.client.gui.ScaledResolution;
import xyz.azide.event.Event;

public final class EventRender extends Event{
    public static final class Overlay extends Event {
        private final ScaledResolution scaledResolution;
        private final float partialTicks;

        public Overlay(final ScaledResolution scaledResolution, final float partialTicks) {
            this.scaledResolution = scaledResolution;
            this.partialTicks = partialTicks;
        }

        public ScaledResolution getScaledResolution() {
            return scaledResolution;
        }

        public float getPartialTicks() {
            return partialTicks;
        }
    }
}
