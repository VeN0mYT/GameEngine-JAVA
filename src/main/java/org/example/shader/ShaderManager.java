package org.example.shader;

import java.util.HashMap;
import java.util.Map;

public final class ShaderManager {
    private static ShaderManager instance;
    private Map<String, Shader> shaders;
    private ShaderManager() {
        shaders = new HashMap<>();
        Shader shader = new Shader("Stander");
        shader.readShader(ShaderType.VERTEX, "i:\\Java Projects\\OpenGl\\ShadersFiles\\StanderVertex");
        shader.readShader(ShaderType.FRAGMENT, "i:\\Java Projects\\OpenGl\\ShadersFiles\\StanderFrag");
        shader.compileShader();

        Shader shader2 = new Shader("StanderPBR");
        shader2.readShaderFile( "i:\\Java Projects\\OpenGl\\ShadersFiles\\StanderShader");
        shaders.put("Stander", shader);
        shaders.put("StanderPBR", shader2);
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
