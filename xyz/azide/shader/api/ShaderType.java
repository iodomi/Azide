package xyz.azide.shader.api;

import xyz.azide.shader.api.ShaderProvider;

public enum ShaderType {
    ROUNDED_RECTANGLE(ShaderProvider.ROUNDED_RECTANGLE),
    GAUSSIAN(ShaderProvider.GAUSSIAN);

    /**
     * The shader string (code).
     */
    private final String shader;

    ShaderType(final String shader) {
        this.shader = shader;
    }

    /**
     * This method returns the shader string (code).
     * @return The shader string (code).
     */
    public String getShader() {
        return this.shader;
    }
}
