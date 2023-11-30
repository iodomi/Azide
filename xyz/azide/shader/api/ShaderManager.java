package xyz.azide.shader.api;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import org.reflections.Reflections;
import xyz.azide.shader.AbstractShader;
import xyz.azide.shader.Shader;
import xyz.azide.trait.Manager;

import java.util.HashMap;

public final class ShaderManager implements Manager {
    private ClassToInstanceMap<AbstractShader> classToInstanceMap;

    @Override
    public void initialize() {
        final HashMap<Class<? extends AbstractShader>, AbstractShader> classShaderMap = new HashMap<>();
        for (final Class<? extends AbstractShader> clazz : new Reflections().getSubTypesOf(AbstractShader.class)) {
            try {
                classShaderMap.put(clazz, clazz.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }

        classToInstanceMap = ImmutableClassToInstanceMap.copyOf(classShaderMap);
    }

    public Shader get(final Class<? extends Shader> shader) {
        return classToInstanceMap.get(shader);
    }
}