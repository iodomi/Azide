package xyz.azide.shader;

public interface Shader {

    /**
     * This method sets uniform(s) for the shader using floats.
     * @param name The shader name.
     * @param floats The float(s) to set.
     */
    void setUniformFloat(final String name, float... floats);

    /**
     * Starts the shader program
     */
    void start();

    /**
     * Stops the shader program
     */
    void stop();


    /**
     * This method sets the uniform for the shader.
     * @param name The shader name.
     */
    void setUniform(final String name);

    /**
     * This method draws the quads, which is the background of the shader.
     * @param x The x position.
     * @param y The y position.
     * @param width The width.
     * @param height The height.
     */
    void drawQuads(final double x, final double y, final double width, final double height);

    /**
     * The update uniforms method is a abstract method that must be implemented by each shader. It is responsible for linking the uniforms of the shaders together
     */
    void updateUniforms();


    /**
     * Sets up a uniform using ints.
     * @param name The name of the uniform.
     * @param ints The ints to set.
     */
    void setUniformInt(String name, int... ints);

    /**
     * Binds a texture to the shader.
     * @param texture The texture to bind.
     */
    void bindTexture(int texture);

    /**
     * Draws a quad using scaled resolution
     */
    void drawScaledResolutionQuads();
}
