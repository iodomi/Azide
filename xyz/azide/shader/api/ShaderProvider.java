package xyz.azide.shader.api;

public final class ShaderProvider {
    public static final String VERTEX = "#version 120\n" +
            "\n" +
            "void main() {\n" +
            "    gl_Position = ftransform();\n" +
            "    gl_TexCoord[0] = gl_MultiTexCoord0;\n" +
            "}";

    public static final String ROUNDED_RECTANGLE = "#version 130\n" +
            "\n" +
            "uniform float width;\n" +
            "uniform float height;\n" +
            "uniform vec4 color;\n" +
            "uniform float radius;\n" +
            "\n" +
            "float calc(vec2 p, vec2 b, float r) {\n" +
            "   return length(max(abs(p) - b, 0.0)) - r;\n" +
            "}\n" +
            "\n" +
            "out vec4 FragColor;\n" +
            "\n" +
            "void main() {\n" +
            "vec2 size = vec2(width, height);\n" +
            "vec2 pixel = gl_TexCoord[0].st * size;\n" +
            "vec2 centre = 0.5 * size;\n" +
            "float sa = smoothstep(0.0, 1, calc(centre - pixel, centre - radius - 1, radius));\n" +
            "vec4 c = mix(vec4(color.rgb, 1), vec4(color.rgb, 0), sa);\n" +
            "FragColor = vec4(c.rgb, color.a * c.a);\n" +
            "}";

    public static final String GAUSSIAN = "#version 120\n" +
            "\n" +
            "uniform sampler2D textureIn;\n" +
            "uniform vec2 texelSize, direction;\n" +
            "uniform float radius;\n" +
            "uniform float weights[256];\n" +
            "\n" +
            "#define offset texelSize * direction\n" +
            "\n" +
            "void main() {\n" +
            "    if (radius == 0.0 || texelSize == vec2(0.0, 0.0) || direction == vec2(0.0, 0.0)) return;\n" +
            "\n" +
            "    vec3 blr = texture2D(textureIn, gl_TexCoord[0].st).rgb * weights[0];\n" +
            "\n" +
            "    for (float f = 1.0; f <= radius; f++) {\n" +
            "        blr += texture2D(textureIn, gl_TexCoord[0].st + f * offset).rgb * (weights[int(abs(f))]);\n" +
            "        blr += texture2D(textureIn, gl_TexCoord[0].st - f * offset).rgb * (weights[int(abs(f))]);\n" +
            "    }\n" +
            "\n" +
            "    gl_FragColor = vec4(blr, 1.0);\n" +
            "}";
}
