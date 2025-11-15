package org.example.shader;

import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public final class Uniform {
    private final Shader shader;
    private final Map<String, Integer> uniformLocations = new HashMap<>();

    public Uniform(Shader shader) {
        this.shader = shader;
    }

    private int getUniformLocation(String name) {
        if (uniformLocations.containsKey(name)) {
            return uniformLocations.get(name);
        }

        int location = GL20.glGetUniformLocation(shader.getShaderID(), name);
        if (location == -1) {
            System.err.println("[Warning] Uniform '" + name + "' not found in shader.");
        }

        uniformLocations.put(name, location);
        return location;
    }


    public void setBoolean(String name, boolean value) {
        GL20.glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }

    public void setInt(String name, int value) {
        GL20.glUniform1i(getUniformLocation(name), value);
    }

    public void setFloat(String name, float value) {
        GL20.glUniform1f(getUniformLocation(name), value);
    }

    public void setVec2(String name, Vector2f value) {
        GL20.glUniform2f(getUniformLocation(name), value.x, value.y);
    }

    public void setVec3(String name, Vector3f value) {
        GL20.glUniform3f(getUniformLocation(name), value.x, value.y, value.z);
    }

    public void setVec4(String name, Vector4f value) {
        GL20.glUniform4f(getUniformLocation(name), value.x, value.y, value.z, value.w);
    }

    public void setMat4(String name, Matrix4f matrix) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            matrix.get(fb); // JOML stores in column-major order (correct for OpenGL)
            GL20.glUniformMatrix4fv(getUniformLocation(name), false, fb);
        }
    }



    // Debug utility: print matrix to console
    public void printMatrix(Matrix4f mat, String name) {
        float[] arr = new float[16];
        mat.get(arr);
        System.out.println(name + ":");
        for (int i = 0; i < 4; i++) {
            System.out.printf("%.3f, %.3f, %.3f, %.3f\n",
                    arr[i], arr[i + 4], arr[i + 8], arr[i + 12]);
        }
        System.out.println();
    }

    public boolean hasUniform(String name) {
        if (uniformLocations.containsKey(name)) {

            return uniformLocations.get(name) != -1;
        }

        int location = GL20.glGetUniformLocation(shader.getShaderID(), name);
        uniformLocations.put(name, location);

        return location != -1;
    }

}
