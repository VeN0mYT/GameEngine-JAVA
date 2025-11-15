package org.example.shader;

import org.example.SkyBox.CubemapTexture;
import org.example.texture.Texture;
import org.example.transform.Color;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;

public final class ShaderGlobalOverride {
    private static ShaderGlobalOverride instance;
    private final Map<String, Object> globals = new HashMap<>();

    private final Map<String, String> paramRemaps = new HashMap<>();
    private boolean active = false;

    private ShaderGlobalOverride() {}
    public static ShaderGlobalOverride getInstance() {
        if (instance == null) {
            instance = new ShaderGlobalOverride();
        }
        return instance;
    }

    public void addGlobal(String name, Object value) {
        globals.put(name, value);
        active = true;
    }

    public Object getGlobal(String name) {
        return globals.get(name);
    }

    public void removeGlobal(String name) {
        globals.remove(name);
        if (globals.isEmpty()) active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void addRemap(String oldName, String newName) {
        paramRemaps.put(oldName, newName);
    }

    public String resolveName(String name) {
        if (paramRemaps.isEmpty() || !paramRemaps.containsKey(name)) return name;
        return paramRemaps.getOrDefault(name, name);
    }

    public void removeRemap(String oldName) {
        paramRemaps.remove(oldName);
    }

    public void upload(Shader shader) {
        if (!active) return;

        var uniforms = shader.getUniforms();
        int textureUnit = 0;

        for (Map.Entry<String, Object> entry : globals.entrySet()) {
            String originalName = entry.getKey();
            String uniformName = resolveName(originalName);
            Object value = entry.getValue();

            if (!shader.hasUniform(uniformName)) continue;


            switch (value) {
                case Float f -> uniforms.setFloat(uniformName, f);
                case Integer i -> uniforms.setInt(uniformName, i);
                case Boolean b -> uniforms.setBoolean(uniformName, b);
                case Vector3f v3 -> uniforms.setVec3(uniformName, v3);
                case Vector4f v4 -> uniforms.setVec4(uniformName, v4);
                case Matrix4f m4 -> uniforms.setMat4(uniformName, m4);
                case Texture tex -> {
                    glActiveTexture(GL_TEXTURE0 + textureUnit);
                    tex.bind();
                    uniforms.setInt(uniformName, textureUnit);
                    textureUnit++;
                }
                case CubemapTexture cubeMap -> {
                    cubeMap.use(0);
                    uniforms.setInt(uniformName, 0);
                }
                default -> System.err.println("[ShaderGlobalOverride] Unsupported uniform type for " + uniformName);
            }
        }
    }

    public void clear() {
        globals.clear();
        active = false;
    }
}
