package org.example.SkyBox;

import org.example.camera.ECamera;
import org.example.shader.Shader;
import org.example.shader.ShaderType;
import org.joml.Matrix4f;

import java.util.List;

import static org.lwjgl.opengl.GL11C.*;

public class SkyBox {
    ECamera camera;
    Shader shader;
    CubemapTexture cubemap;
    SkyboxMesh mesh;
    List<String> faces = List.of(
            "I:\\Java Projects\\OpenGl\\Assits\\skybox\\right.jpg",
            "I:\\Java Projects\\OpenGl\\Assits\\skybox\\left.jpg",
            "I:\\Java Projects\\OpenGl\\Assits\\skybox\\top.jpg",
            "I:\\Java Projects\\OpenGl\\Assits\\skybox\\bottom.jpg",
            "I:\\Java Projects\\OpenGl\\Assits\\skybox\\front.jpg",
            "I:\\Java Projects\\OpenGl\\Assits\\skybox\\back.jpg"
    );

    public SkyBox(ECamera cam) {
        camera = cam;
        shader = new Shader("skybox");
        shader.readShader(ShaderType.VERTEX, "I:\\Java Projects\\OpenGl\\ShadersFiles\\SkyboxShader\\vertex");
        shader.readShader(ShaderType.FRAGMENT, "I:\\Java Projects\\OpenGl\\ShadersFiles\\SkyboxShader\\fragment");
        shader.compileShader();

        cubemap = new CubemapTexture(faces);
        mesh = new SkyboxMesh();
    }

    public void render() {
        glDepthMask(false);      // disable writing to depth
        glDepthFunc(GL_LEQUAL);  // allow skybox to pass depth

        shader.use();

// remove camera translation
        Matrix4f viewNoTranslation = new Matrix4f(camera.getViewMatrix());
        viewNoTranslation.m30(0);
        viewNoTranslation.m31(0);
        viewNoTranslation.m32(0);

        shader.getUniforms().setMat4("view", viewNoTranslation);
        shader.getUniforms().setMat4("projection", camera.getProjectionMatrix());
        shader.getUniforms().setInt("skybox", 0);

        cubemap.use(0);
        mesh.render();

        glDepthMask(true);
        glDepthFunc(GL_LESS);

    }
}
