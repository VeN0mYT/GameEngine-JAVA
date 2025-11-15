package org.example.component;


import org.example.SkyBox.CubemapTexture;
import org.example.camera.ECamera;
import org.example.light.Light;
import org.example.shader.*;
import org.example.texture.Texture;
import org.example.transform.Color;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL13.*;

public final class Material extends Component {
    private Shader shader;
    private Map<String, Texture> textures = new HashMap<>();


    private Map<String, Object> properties = new HashMap<>();


    public Material()
    {
        shader = ShaderManager.getInstance().getShader("Stander");
        CreateUniforms(shader);
    }

    public Material(String name,String path)
    {
        shader = new Shader(name,path);
        ShaderManager.getInstance().addShader(shader);
        CreateUniforms(shader);
    }

    public Material(String name)
    {
        shader = ShaderManager.getInstance().getShader(name);
        CreateUniforms(shader);
    }
    public Material(Shader shader) {
        this.shader = shader;
        CreateUniforms(shader);
    }

    private void CreateUniforms(Shader shader)
    {
        Map<String, ShaderParser.UniformData> uniformTypes  = shader.getUniformsParsed();

        System.out.println("-----------------------");
        for (Map.Entry<String, ShaderParser.UniformData> entry : uniformTypes.entrySet()) {
            String name = entry.getKey();
            ShaderParser.UniformData type = entry.getValue();

            if(name.equals("model")||name.equals("view")||name.equals("projection")
                    ||name.equals("lightPosition")||name.equals("lightColor")
                    ||name.equals("lightIntensity")||name.equals("viewPos")
                    ||name.equals("_Time"))
                continue;

            System.out.println(name+" "+type.type + " "+type.defaultValue);
            switch (type.type) {
                case "float" -> properties.put(name, type.defaultValue != null ? type.defaultValue : 0f);
                case "vec2" -> properties.put(name, type.defaultValue != null ? type.defaultValue : new Vector2f());
                case "vec3" -> properties.put(name, type.defaultValue != null ? type.defaultValue : new Vector3f());
                case "vec4" -> properties.put(name, type.defaultValue != null ? type.defaultValue : new Vector4f());
                case "mat4" -> properties.put(name, type.defaultValue != null ? type.defaultValue : new Matrix4f());
                case "bool" -> properties.put(name,type.defaultValue != null ? type.defaultValue : false);
                case "int" -> properties.put(name, type.defaultValue != null ? type.defaultValue : 0);
                case "sampler2D" -> properties.put(name, null); // Texture
                case "samplerCube" -> properties.put(name, null); // CubeMap
                default -> System.err.println("Unknown uniform type: " + type);
            }
        }
    }

    public void setTexture(String name, Texture texture) {
        textures.put(name, texture);
    }

    public Object getUniform(String name) {
        if(!properties.containsKey(name))
            System.out.println("Uniform " + name + " not found");
        return properties.get(name);
    }

    public void setUniform(String name,Object value) {
        if(!properties.containsKey(name))
            System.out.println("Uniform " + name + " not found");

        properties.put(name, value);
    }



    public void bind() {
        shader.use();

        ShaderGlobalOverride.getInstance().upload(shader);

        Map<String, ShaderParser.UniformData> uniformTypes = shader.getUniformsParsed();

        if(shader.getUniformsParsed().containsKey("model"))
            properties.put("model", transform.getModelMatrix());

        ShaderGlobalContext.get().uploadGlobals(shader);


        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (value == null) continue;


            switch (uniformTypes.get(name).type) {
                case "float" -> shader.getUniforms().setFloat(name, (Float) value);
                case "bool" -> shader.getUniforms().setInt(name, (Boolean) value ? 1 : 0);
                case "int" -> shader.getUniforms().setInt(name, (Integer) value);
                case "vec2" -> shader.getUniforms().setVec2(name, (Vector2f) value);
                case "vec3" -> shader.getUniforms().setVec3(name, (Vector3f) value);
                case "vec4" -> shader.getUniforms().setVec4(name, (Vector4f) value);
                case "mat4" -> shader.getUniforms().setMat4(name, (Matrix4f) value);
                case "sampler2D" -> {   //texture need more work
                    Texture tex = (Texture) value;
                    if(tex != null) {
                        int slot = 0; // You can integrate a TextureManager for dynamic slots
                        tex.bind(slot);
                        shader.getUniforms().setInt(name, slot);
                    }
                }
                case "samplerCube" -> {
                    CubemapTexture cubeMap = (CubemapTexture) value;
                    if(cubeMap != null) {
                        int slot = 0;
                        cubeMap.use(slot);
                        shader.getUniforms().setInt(name, slot);
                    }
                }
            }
        }
    }


    public Shader getShader() {

        return shader;
    }



    public String getName() {
        return "Material";
    }
}
