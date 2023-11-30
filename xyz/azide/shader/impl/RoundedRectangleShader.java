package xyz.azide.shader.impl;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import xyz.azide.shader.AbstractShader;
import xyz.azide.shader.api.ShaderType;

import java.awt.*;

public final class RoundedRectangleShader extends AbstractShader {
    public RoundedRectangleShader() {
        super(ShaderType.ROUNDED_RECTANGLE);
    }

    public void render(final float x, final float y, final float width, final float height, final float radius, final Color color) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        start();
        setUniforms(width, height, radius, color);
        drawQuads(x, y, width, height);
        stop();

        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    @Override
    public void updateUniforms() {

    }

    private void setUniforms(final float width, final float height, final float radius, final Color color) {
        updateUniforms();

        setUniformFloat("width", width);
        setUniformFloat("height", height);
        setUniformFloat("radius", radius);
        setUniformFloat("color", color.getRed() / 255F, color.getGreen() / 255F,color.getBlue() / 255F ,color.getAlpha() / 255F);
    }
}