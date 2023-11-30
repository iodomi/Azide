package xyz.azide.shader.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import xyz.azide.shader.AbstractShader;
import xyz.azide.shader.api.ShaderType;

import java.awt.*;
import java.nio.FloatBuffer;

import static net.minecraft.client.renderer.OpenGlHelper.glUniform1;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public final class GaussianShader extends AbstractShader {
    private final Minecraft mc = Minecraft.getMinecraft();
    private Framebuffer frameBuffer = new Framebuffer(1, 1, false); // the framebuffer the blur is rendered to
    private FloatBuffer weightBuffer; // buffer that stores weights
    private float oldRadius = -1; // used to check if weights have to be recalculated

    public GaussianShader() {
        super(ShaderType.GAUSSIAN);
    }

    public void render(final float radius) {
        final ScaledResolution sr = new ScaledResolution(mc);

        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(1, 1, 1, 1);

        frameBuffer = createFrameBuffer(frameBuffer, mc.displayWidth, mc.displayHeight);
        frameBuffer.framebufferClear();

        frameBuffer.bindFramebuffer(true);
        use(pid -> setUniforms(1, 0, radius, pid));
        glBindTexture(GL_TEXTURE_2D, mc.getFramebuffer().framebufferTexture);
        drawQuads(0, 0, sr.getScaledWidth(), sr.getScaledHeight());
        frameBuffer.unbindFramebuffer();
        stop();

        mc.getFramebuffer().bindFramebuffer(true);
        use(pid -> setUniforms(0, 1, radius, pid));
        glBindTexture(GL_TEXTURE_2D, frameBuffer.framebufferTexture);
        drawQuads(0, 0, sr.getScaledWidth(), sr.getScaledHeight());
        stop();

        glColor4f(1, 1, 1, 1);
        GlStateManager.bindTexture(0);
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

    /**
     * Calculate weights for this radius
     * @param radius the radius to calculate weights for
     */
    private void calculateWeights(final float radius) {
        if (radius != oldRadius) {
            weightBuffer = BufferUtils.createFloatBuffer(256);
            for (int i = 0; i <= radius; i++) {
                weightBuffer.put(calculateGaussianValue(i, radius / 2));
            }

            weightBuffer.rewind();
        }
        oldRadius = radius;
    }

    /**
     * set shader uniforms
     *
     * @param horiz blur horizontally
     * @param vert blur vertically
     * @param radius blur radius
     * @param pid shader program id
     */
    private void setUniforms(final int horiz, final int vert, final float radius, final int pid) {
        glUniform1i(glGetUniformLocation(pid, "textureIn"), 0);
        glUniform2f(glGetUniformLocation(pid, "texelSize"), 1.0F / (float) mc.displayWidth, 1.0F / (float) mc.displayHeight);
        glUniform2f(glGetUniformLocation(pid, "direction"), horiz, vert);
        glUniform1f(glGetUniformLocation(pid, "radius"), radius);

        calculateWeights(radius);
        glUniform1(glGetUniformLocation(pid, "weights"), weightBuffer);
    }

    /**
     * calculates gaussian value for blur
     * @see <a href="https://en.wikipedia.org/wiki/Gaussian_function">Gaussian function</a>
     * @see <a href="https://en.wikipedia.org/wiki/Gaussian_blur">Gaussian blur</a>
     *
     * @param x is value passed to gaussian function
     * @param sigma horizontal/vertical distance
     * @return output of gaussian function
     */
    private float calculateGaussianValue(final int x, final float sigma) {
        final double output = 1.0 / Math.sqrt(2.0 * Math.PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    /**
     * utility function to create/update buffer when screen size changes
     * TODO: move function to separate class for FrameBuffer utilities
     *
     * @param framebuffer the buffer to be updated
     * @return new buffer
     */
    public static Framebuffer createFrameBuffer(final Framebuffer framebuffer, final float width, final float height) {
        if (framebuffer == null || framebuffer.framebufferWidth != width || framebuffer.framebufferHeight != height) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer((int) width, (int) height, true);
        }
        return framebuffer;
    }
}
