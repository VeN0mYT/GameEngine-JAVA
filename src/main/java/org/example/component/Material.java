package org.example.component;


import org.example.camera.ECamera;
import org.example.light.Light;
import org.example.shader.Shader;
import org.example.shader.ShaderType;
import org.example.texture.Texture;
import org.joml.Vector3f;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL13.*;

public final class Material extends Component {
    private Shader shader;
    private Map<String, Texture> textures = new HashMap<>();
    private Map<String, Float> floats = new HashMap<>();
    private Map<String, Vector3f> vectors = new HashMap<>();
    private Map<String, Boolean> booleans = new HashMap<>();

    public Material()
    {
        shader = new Shader("Stander");
        shader.readShader(ShaderType.VERTEX, "i:\\Java Projects\\OpenGl\\ShadersFiles\\StanderVertex");
        shader.readShader(ShaderType.FRAGMENT, "i:\\Java Projects\\OpenGl\\ShadersFiles\\StanderFrag");
        shader.compileShader();
    }
    public Material(Shader shader) {
        this.shader = shader;
    }

    public void setTexture(String name, Texture texture) {
        textures.put(name, texture);
    }

    public void setFloat(String name, float value) {
        floats.put(name, value);
    }

    public void setVector3(String name, Vector3f value) {
        vectors.put(name, value);
    }

    public void setBoolean(String name, boolean value) {
        booleans.put(name, value);
    }

    public void use() {
        shader.use();

        // Upload uniforms
        for (Map.Entry<String, Float> entry : floats.entrySet()) {
            shader.getUniforms().setFloat(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Vector3f> entry : vectors.entrySet()) {
            shader.getUniforms().setVec3(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Boolean> entry : booleans.entrySet()) {
            shader.getUniforms().setInt(entry.getKey(), entry.getValue() ? 1 : 0);
        }

        // Bind textures
        int unit = 0;
        for (Map.Entry<String, Texture> entry : textures.entrySet()) {
            glActiveTexture(GL_TEXTURE0 + unit);
            entry.getValue().bind();
            shader.getUniforms().setInt(entry.getKey(), unit);
            unit++;
        }
    }

    public void prepareForRender(Light light) {
        setBoolean("hasTexture", !textures.isEmpty());
        setBoolean("hasLight", light != null);

        if (light != null) {
            setVector3("lightPosition", light.getPosition());
            setVector3("lightColor", light.getColor());
            setFloat("lightIntensity", light.getIntensity());
            setVector3("ambientColor", new Vector3f(0.15f, 0.15f, 0.2f));
        }
    }


    public Shader getShader() {

        return shader;
    }



    public String getName() {
        return "Material";
    }
}
