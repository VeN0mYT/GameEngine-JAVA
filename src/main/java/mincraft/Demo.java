package mincraft;

import org.example.camera.FPSCamera;
import org.example.render.*;
import org.example.shader.Shader;
import org.example.shader.*;
import org.example.shader.ShaderManager;
import static org.lwjgl.opengl.GL11.*;

import org.example.time.Time;
import org.joml.Vector3f;

public class Demo {
    private FPSCamera camera;
    private VertexArray vao;
    private VertexBuffer vbo;
    private ElementBuffer ebo;

    Demo() {
        Render renderer = Render.getInstance();
        renderer.setInit(this::init);
        renderer.setOnRender(this::onRender);
        renderer.setOnEnd(this::onEnd);
    }

    private void initShaders() {
        Shader shader = new Shader("defuilt");
        shader.readShader(ShaderType.VERTEX, "i:\\Java Projects\\OpenGl\\ShadersFiles\\cvertex");
        shader.readShader(ShaderType.FRAGMENT, "i:\\Java Projects\\OpenGl\\ShadersFiles\\cfragment");
        shader.compileShader();
        ShaderManager.getInstance().addShader(shader);

        camera = new FPSCamera(new Vector3f(0, 0, 3), 5f);
        camera.setPerspective(45, 1, 0.1f, 100f);
       // camera.enable(true);
    }

    private void init() {
        initShaders();

        float[] vertices = {
            // positions         // colors
            0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,  // bottom right
            -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f,  // bottom left
            -0.5f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f   // top
        };

        int[] indices = {
            0, 1, 2
        };

         vao = new VertexArray();
         vbo = new VertexBuffer(vertices);


        VertexLayout vl = new VertexLayout();
        vl.pushFloat(3);
        vl.pushFloat(3);

        vao.addBuffer(vbo, vl, 0);

        ebo = new ElementBuffer(indices);

        vao.unbind();
        vbo.unbind();
        ebo.unbind();

        ShaderManager.getInstance().getShader("defuilt").use();

    }



    private void onRender() {
        float delta  = Time.getDeltaTime();
        camera.update(delta);
        Shader shader = ShaderManager.getInstance().getShader("defuilt");
        shader.getUniforms().setMat4("model", new org.joml.Matrix4f().identity());

        // View matrix: from camera
        shader.getUniforms().setMat4("view", camera.getViewMatrix());

        // Projection matrix: from camera
        shader.getUniforms().setMat4("projection", camera.getProjectionMatrix());

        vao.bind();
        glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0);
        vao.unbind();
    }

    private void onEnd() {
        ShaderManager.getInstance().Destroy();
        vao.destroy();
        vbo.destroy();
        ebo.destroy();
    }

}
