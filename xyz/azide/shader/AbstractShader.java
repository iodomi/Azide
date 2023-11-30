package xyz.azide.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import xyz.azide.shader.api.ShaderProvider;
import xyz.azide.shader.api.ShaderType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUniform2f;

public abstract class AbstractShader implements Shader {
    private final Map<String, Integer> shaderUniformMap = new HashMap<>();
    private int shaderProgramID;

    protected AbstractShader(final ShaderType shaderType) {
        this.compileVertexAndFragment(shaderType.getShader());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void updateUniforms();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUniform(final String name) {
        this.shaderUniformMap.put(name, GL20.glGetUniformLocation(this.shaderProgramID, name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        GL20.glUseProgram(this.shaderProgramID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        GL20.glUseProgram(GL11.GL_ZERO);
    }

    /**
     * This method compiles the vertex and fragment shaders.
     * @param fragmentShader The fragment shader.
     */
    private void compileVertexAndFragment(final String fragmentShader) {
        final int programId = GL20.glCreateProgram();

        final int vertexShaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        final int fragmentShaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        // Compile vertex shader
        GL20.glShaderSource(vertexShaderId, ShaderProvider.VERTEX);
        GL20.glCompileShader(vertexShaderId);

        // Compile fragment shader
        GL20.glShaderSource(fragmentShaderId, fragmentShader);
        GL20.glCompileShader(fragmentShaderId);

        // Create shader program
        GL20.glAttachShader(programId, vertexShaderId);
        GL20.glAttachShader(programId, fragmentShaderId);
        GL20.glLinkProgram(programId);

        this.shaderProgramID = programId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUniformFloat(final String name, final float... floats) {
        this.setUniform(name);

        switch (floats.length) {
            case 1:
                GL20.glUniform1f(this.shaderUniformMap.get(name), floats[0]);
                break;
            case 2:
                GL20.glUniform2f(this.shaderUniformMap.get(name), floats[0], floats[1]);
                break;
            case 3:
                GL20.glUniform3f(this.shaderUniformMap.get(name), floats[0], floats[1], floats[2]);
                break;
            case 4:
                GL20.glUniform4f(this.shaderUniformMap.get(name), floats[0], floats[1], floats[2], floats[3]);
                break;
            default:
                throw new IllegalArgumentException("Invalid float array length: " + floats.length);
        }
    }

    @Override
    public void setUniformInt(final String name, final int... ints) {
        this.setUniform(name);

        switch (ints.length) {
            case 1:
                GL20.glUniform1i(this.shaderUniformMap.get(name), ints[0]);
                break;
            case 2:
                GL20.glUniform2i(this.shaderUniformMap.get(name), ints[0], ints[1]);
                break;
            case 3:
                GL20.glUniform3i(this.shaderUniformMap.get(name), ints[0], ints[1], ints[2]);
                break;
            case 4:
                GL20.glUniform4i(this.shaderUniformMap.get(name), ints[0], ints[1], ints[2], ints[3]);
                break;
            default:
                throw new IllegalArgumentException("Invalid float array length: " + ints.length);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void drawQuads(final double x, final double y, final double width, final double height) {
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(GL11.GL_ZERO, GL11.GL_ZERO);
        GL11.glVertex2d(x, y);

        GL11.glTexCoord2f(GL11.GL_ZERO, GL11.GL_ONE);
        GL11.glVertex2d(x, y + height);

        GL11.glTexCoord2f(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glVertex2d(x + width, y + height);

        GL11.glTexCoord2f(GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glVertex2d(x + width, y);

        GL11.glEnd();
    }


    @Override
    public void bindTexture(final int texture) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
    }

    @Override
    public void drawScaledResolutionQuads() {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(GL11.GL_ZERO, GL11.GL_ONE);
        GL11.glVertex2f(GL11.GL_ZERO, GL11.GL_ZERO);
        GL11.glTexCoord2f(GL11.GL_ZERO, GL11.GL_ZERO);
        GL11.glVertex2f(GL11.GL_ZERO, scaledResolution.getScaledHeight());
        GL11.glTexCoord2f(GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glVertex2f(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        GL11.glTexCoord2f(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glVertex2f(scaledResolution.getScaledWidth(), GL11.GL_ZERO);
        GL11.glEnd();
    }

    public void use() throws IllegalArgumentException {
        if (!glIsProgram(shaderProgramID)) {
            throw new IllegalArgumentException("Shader has been deleted");
        }

        glUseProgram(shaderProgramID);

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        // default uniforms for every shader
        glUniform1f(glGetUniformLocation(shaderProgramID, "time"), (System.currentTimeMillis() - shaderProgramID) / 1000.0f);
        glUniform2f(glGetUniformLocation(shaderProgramID, "resolution"), sr.getScaledWidth(), sr.getScaledHeight());
    }

    public void use(Consumer<Integer> uniforms) {
        use();
        uniforms.accept(shaderProgramID);
    }

    public int getUniform(final String name) {
        return GL20.glGetUniformLocation(shaderProgramID, name);
    }
}
