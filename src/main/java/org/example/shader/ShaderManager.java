package org.example.shader;

import java.util.HashMap;
import java.util.Map;

public class ShaderManager {
    private static ShaderManager instance;
    private Map<String, Shader> shaders;
    private ShaderManager() {
        shaders = new HashMap<>();
    }

    public static ShaderManager getInstance() {
        if (instance == null) {
            instance = new ShaderManager();
        }
        return instance;
    }

    public void addShader(Shader shader) {
        if (shaders.containsKey(shader.getName())) return;
        shaders.put(shader.getName(), shader);
    }

    public Shader getShader(String name) {
        if (!shaders.containsKey(name))
        {
            throw new RuntimeException("Shader with name " + name + " not found");
        }
        return shaders.get(name);
    }

    public void Destroy() {
        for (Shader shader : shaders.values()) {
            shader.delete();
        }
        shaders.clear();
    }
}
