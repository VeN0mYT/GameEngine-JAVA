package org.example.shader;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShaderParser {

    public static class UniformData {
        public final String type;
        public final String name;
        public final Object defaultValue; // Parsed Java object

        public UniformData(String type, String name, Object defaultValue) {
            this.type = type;
            this.name = name;
            this.defaultValue = defaultValue;
        }

        @Override
        public String toString() {
            return "UniformData{" +
                    "type='" + type + '\'' +
                    ", name='" + name + '\'' +
                    ", defaultValue=" + defaultValue +
                    '}';
        }
    }

    public static Map<String, UniformData> parseUniforms(String shaderSource) {
        Map<String, UniformData> uniforms = new LinkedHashMap<>();

        Pattern pattern = Pattern.compile(
                "uniform\\s+(\\w+)\\s+(\\w+)\\s*(?:=\\s*([^;]+))?;",
                Pattern.MULTILINE
        );

        Matcher matcher = pattern.matcher(shaderSource);
        while (matcher.find()) {
            String type = matcher.group(1);
            String name = matcher.group(2);
            String defaultValueText = matcher.group(3) != null ? matcher.group(3).trim() : null;

            Object parsedValue = parseValue(type, defaultValueText);

            uniforms.put(name, new UniformData(type, name, parsedValue));
        }

        return uniforms;
    }

    private static Object parseValue(String type, String text) {
        if (text == null) return null;

        try {
            switch (type) {
                case "float":
                    return Float.parseFloat(text);
                case "int":
                    return Integer.parseInt(text);
                case "bool":
                    return text.equalsIgnoreCase("true");
                case "vec2":
                    return parseVec(text, 2);
                case "vec3":
                    return parseVec(text, 3);
                case "vec4":
                    return parseVec(text, 4);
                default:
                    return text; // sampler2D, unknown, etc.
            }
        } catch (Exception e) {
            return text; // fallback if parsing fails
        }
    }

    private static Object parseVec(String text, int size) {
        // Example: vec3(1.0, 0.2, 0.1)
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(text);
        if (!m.find()) return null;

        String[] parts = m.group(1).split(",");
        float[] values = new float[Math.min(size, parts.length)];
        for (int i = 0; i < values.length; i++) {
            values[i] = Float.parseFloat(parts[i].trim());
        }

        switch (size) {
            case 2:
                return new Vector2f(values[0], values[1]);
            case 3:
                return new Vector3f(values[0], values[1], values[2]);
            case 4:
                return new Vector4f(values[0], values[1], values[2], values[3]);
            default:
                return null;
        }
    }
}