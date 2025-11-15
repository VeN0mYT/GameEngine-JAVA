package org.example.shader;

import org.example.camera.ECamera;
import org.example.light.Light;
import org.example.time.TimeFix;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class ShaderGlobalContext {
    private static ShaderGlobalContext instance;

    private ECamera camera;
    private Light mainLight;


    private ShaderGlobalContext() {}

    public static ShaderGlobalContext get() {
        if (instance == null)
            instance = new ShaderGlobalContext();
        return instance;
    }

    public void setCamera(ECamera camera) {
        this.camera = camera;
    }

    public void setMainLight(Light light) {
        this.mainLight = light;
    }



    public void uploadGlobals(Shader shader) {
        if (camera != null) {
            if (shader.hasUniform("view"))
                shader.getUniforms().setMat4("view", camera.getViewMatrix());
            if (shader.hasUniform("projection"))
                shader.getUniforms().setMat4("projection", camera.getProjectionMatrix());
            if (shader.hasUniform("viewPos"))
                shader.getUniforms().setVec3("viewPos", camera.getTransform().position);
        }

        if (mainLight != null) {
            if (shader.hasUniform("lightPosition"))
                shader.getUniforms().setVec3("lightPosition", mainLight.getPosition());
            if (shader.hasUniform("lightColor"))
                shader.getUniforms().setVec3("lightColor", mainLight.getColor());
            if (shader.hasUniform("lightIntensity"))
                shader.getUniforms().setFloat("lightIntensity", mainLight.getIntensity());
        }

        if (shader.hasUniform("_Time"))
            shader.getUniforms().setFloat("_Time", (float)TimeFix.getTime());
    }
}

