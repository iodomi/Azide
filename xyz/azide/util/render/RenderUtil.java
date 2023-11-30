package xyz.azide.util.render;

import xyz.azide.Azide;
import xyz.azide.shader.impl.GaussianShader;
import xyz.azide.shader.impl.RoundedRectangleShader;
import xyz.azide.trait.Util;

import java.awt.*;

public final class RenderUtil implements Util {
    private RenderUtil() {

    }

    public static void drawRoundedRectangle(final float x, final float y, final float width, final float height, final float radius, final Color color) {
        ((RoundedRectangleShader) Azide.getSingleton().getShaderManager().get(RoundedRectangleShader.class)).render(x, y, width, height, radius, color);
    }

    public static void drawGaussian(final float radius) {
        ((GaussianShader) Azide.getSingleton().getShaderManager().get(GaussianShader.class)).render(radius);
    }
}
