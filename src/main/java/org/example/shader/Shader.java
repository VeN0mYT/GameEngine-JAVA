package org.example.shader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private String Name;
    private final int programID;
    private int vertexID;
    private int fragmentID;

    private String vertexSource = "";
    private String fragmentSource = "";

    private Uniform uniform;

    public Shader(String name) {
        this.Name = name;
        programID = glCreateProgram();
        uniform = new Uniform(this);
    }

    public void readShaderFile(String filepath) {
        String source;
        try {
            source = Files.readString(Path.of(filepath));
        } catch (IOException e) {
            System.err.println("File not found: " + filepath);
            return;
        }

        // Split by our custom tags
        String[] parts = source.split("#fragment");

        if (parts.length < 1) {
            System.err.println("Missing #fragment section in shader file.");
            return;
        }

        // Extract vertex and fragment sections
        String vertexPart = parts[0].replace("#vertex", "").trim();
        String fragmentPart = (parts.length > 1) ? parts[1].trim() : "";

        if (vertexPart.isEmpty() || fragmentPart.isEmpty()) {
            System.err.println("Shader file missing vertex or fragment code.");
            return;
        }

        // Compile both
        vertexSource = vertexPart;
        fragmentSource = fragmentPart;

        createShaderVS();
        createShaderFG();
        compileShader();
    }

    // Load from file
    public void readShader(ShaderType type, String filepath) {
        String source;
        try {
            source = Files.readString(Path.of(filepath));
        } catch (IOException e) {
            System.err.println("File not found: " + filepath);
            return;
        }

        createShader(type, source);
    }

    // Load from string
    public void createShader(ShaderType type, String source) {
        if (type == ShaderType.VERTEX) {
            vertexSource = source;
            createShaderVS();
        } else if (type == ShaderType.FRAGMENT) {
            fragmentSource = source;
            createShaderFG();
        } else {
            System.err.println("Invalid shader type");
        }
    }

    private void createShaderVS() {
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile vertex shader");
            System.err.println(glGetShaderInfoLog(vertexID));
            glDeleteShader(vertexID);
        }
    }

    private void createShaderFG() {
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile fragment shader");
            System.err.println(glGetShaderInfoLog(fragmentID));
            glDeleteShader(fragmentID);
        }
    }

    public void compileShader() {
        glAttachShader(programID, vertexID);
        glAttachShader(programID, fragmentID);
        glLinkProgram(programID);

        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Failed to link shader program");
            System.err.println(glGetProgramInfoLog(programID));
        }

        // After linking, shaders can be deleted
        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
    }

    public void use() {
        glUseProgram(programID);
    }

    public void delete() {
        glDeleteProgram(programID);
    }

    public int getShaderID() {
        return programID;
    }

    public Uniform getUniforms() {
        return uniform;
    }

    public String getName() {
        return Name;
    }
}
