package xyz.azide.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import xyz.azide.trait.Util;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_DEPTH24_STENCIL8;
import static org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT;

public final class StencilUtil implements Util {
    private StencilUtil() {

    }

    /**
     * Instantiates the stencil buffer and starts stenciling
     */
    public static void initStencil() {
        // bind the framebuffer
        mc.getFramebuffer().bindFramebuffer(false);

        // setup FBO
        setupFramebufferObject(mc.getFramebuffer());

        // clear the stencil buffer
        glClear(GL_STENCIL_BUFFER_BIT);

        // enable stencil
        glEnable(GL_STENCIL_TEST);

        // set the stencil function
        glStencilFunc(GL_ALWAYS, GL_ONE, GL_ONE);

        // set the stencil operation
        glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);

        // disable color mask
        glColorMask(false, false, false, false);
    }

    /**
     * Erases the shape from the stencil buffer
     *
     * @param ref the ref is the reference to the shape it is usually 1
     */
    public static void eraseStencil(final int ref) {

        // mask the color buffer
        glColorMask(true, true, true, true);

        // enable stencil function
        glStencilFunc(GL_EQUAL, ref, GL_ONE);

        // set the stencil operation
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
    }


    /**
     * Disposes the stencil buffer (disables stenciling)
     */
    public static void disposeStencil() {
        // disable stenciling
        glDisable(GL_STENCIL_TEST);
    }

    /**
     * Sets up the FBO
     *
     * @param framebuffer the framebuffer to set up
     */
    public static void setupFramebufferObject(final Framebuffer framebuffer) {
        // null check
        if (framebuffer == null) {
            System.out.println("Failed to setup Framebuffer");
            return;
        }

        // setup fbo if depth buffer is greater than -1
        if (framebuffer.depthBuffer > -1) {
            setupStencilBuffers(framebuffer);

            // prevents multiple FBO's being created.
            framebuffer.depthBuffer = -1;
        }
    }


    /**
     * Sets up the FBO with depth and stencil
     *
     * @param framebuffer Framebuffer
     */
    private static void setupStencilBuffers(final Framebuffer framebuffer) {
        final int stencilBufferID = generateStencilBufferAndExtention();

        // copilot wrote the following code :sob:

        // bind the stencil buffer
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, stencilBufferID);

        // allocate storage for the stencil buffer
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH24_STENCIL8, framebuffer.framebufferTextureWidth, framebuffer.framebufferTextureHeight);

        // attach stencil buffer
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER_EXT, stencilBufferID);

        // unbind
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, 0);
    }

    /**
     * Generates the stencil buffer and returns the stencil buffer id
     *
     * @return the generated stencil buffer id
     */
    private static int generateStencilBufferAndExtention() {
        return glGenRenderbuffersEXT();
    }

    public static void renderStencil(final Runnable init, final Runnable end) {
        initStencil();
        init.run();
        eraseStencil(1);
        end.run();
        disposeStencil();
    }
}
