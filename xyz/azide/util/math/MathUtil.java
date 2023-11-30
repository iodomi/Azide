package xyz.azide.util.math;

import xyz.azide.trait.Util;

public final class MathUtil implements Util {
    public static float center(final float outer, final float inner) {
        return (outer - inner) / 2;
    }

    public static double center(final double outer, final double inner) {
        return (outer - inner) / 2;
    }
}