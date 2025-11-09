package org.example;

import mincraft.block.BlockType;
import mincraft.block.Chunk;
import mincraft.block.WorldGenerator;
import mincraft.raycast.RayCast;
import mincraft.raycast.WireFrameRender;
import org.example.render.*;
import org.example.shader.*;
import org.example.camera.*;
import org.example.texture.Texture;
import org.example.time.Time;
import org.joml.Vector3f;
import org.joml.Vector3i;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {


    private static FPSCamera camera;
    private static Chunk chunk;
    private static Texture texture;


    private static BlockType selectedBlock = BlockType.STONE; // default

    private static WorldGenerator generator = new WorldGenerator();

    private static boolean mouseLeftPressed = false;
    private static boolean mouseRightPressed = false;

    private static WireFrameRender wireframeRenderer;
    private static Vector3i hitBlock;

    public static void main(String[] args) {


        Render instance = Render.getInstance(800, 600, "Minecraft", SyncMode.VSYNC_ON);
       // Demo demo = new Demo();
        instance.setInit(Main::init);
        instance.setOnRender(Main::onRender);
        instance.setOnEnd(Main::onEnd);
        instance.loop();

    }

    private static void onRender()
    {
//        glBegin(GL_TRIANGLES);
//        glVertex2f(-0.5f, -0.5f);
//        glVertex2f(0.5f, -0.5f);
//        glVertex2f(0.0f, 0.5f);
//        glEnd();

        float delta = Time.getDeltaTime();
        camera.update(delta);

        Shader shader = ShaderManager.getInstance().getShader("min");

        shader.getUniforms().setMat4("u_model", new org.joml.Matrix4f().identity());
        shader.getUniforms().setMat4("u_view", camera.getViewMatrix());
        shader.getUniforms().setMat4("u_projection", camera.getProjectionMatrix());


        shader.getUniforms().setVec3("lightDir", new Vector3f(-0.3f, -1.0f, -0.5f).normalize()); // direction of light
        shader.getUniforms().setVec3("lightColor", new Vector3f(1.0f, 1.0f, 1.0f));             // white light
        shader.getUniforms().setVec3("ambientColor", new Vector3f(0.3f, 0.3f, 0.3f));
        shader.getUniforms().setInt("u_texture", 0);

        Vector3f camPos = camera.getPosition();
        Vector3f dir = camera.getFront();

        handleBlockSelection();

        glfwSetMouseButtonCallback(Render.getInstance().getWindow(), (windowHandle, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT) {
                if (action == GLFW_PRESS) {
                    // Left mouse pressed → place block
                    mouseLeftPressed = true;
                } else if (action == GLFW_RELEASE) {
                    mouseLeftPressed = false;
                }
            }

            if (button == GLFW_MOUSE_BUTTON_RIGHT) {
                if (action == GLFW_PRESS) {
                    // Right mouse pressed → remove block
                    mouseRightPressed = true;
                } else if (action == GLFW_RELEASE) {
                    mouseRightPressed = false;
                }
            }
        });



        Vector3f hitBlock = RayCast.getHitBlock(camPos, dir, chunk);
        if (hitBlock != null && mouseRightPressed) {
            chunk.setBlock((int)hitBlock.x, (int)hitBlock.y, (int)hitBlock.z, BlockType.AIR);
            chunk.generateChunk();
            chunk.uploadData(); // update VBO
        }

        Vector3f placePos = RayCast.getPlacePosition(camPos, dir, chunk);
        if (placePos != null && mouseLeftPressed) {
            chunk.setBlock((int)placePos.x, (int)placePos.y, (int)placePos.z, selectedBlock);
            chunk.generateChunk();
            chunk.uploadData(); // update VBO
        }

        texture.use(0);
        shader.getUniforms().setFloat("u_texture", 0);


        texture.bind(); // Bind to texture slot 0
        chunk.renderChunk();
        texture.unbind();

        hitBlock = RayCast.getHitBlock(camera.getPosition(), camera.getFront(), chunk);

        if(hitBlock != null){
            wireframeRenderer.renderWireCube(hitBlock.x, hitBlock.y, hitBlock.z,
                    camera.getViewMatrix(), camera.getProjectionMatrix());
        }



    }

    private static void init()
    {
        Shader shader = new Shader("min");
        shader.readShader(ShaderType.VERTEX, "i:\\Java Projects\\OpenGl\\ShadersFiles\\Mvertex");
        shader.readShader(ShaderType.FRAGMENT, "i:\\Java Projects\\OpenGl\\ShadersFiles\\Mfragment");
        shader.compileShader();
        ShaderManager.getInstance().addShader(shader);

        texture = new Texture();
        texture.loadImage("I:\\Java Projects\\OpenGl\\Assits\\BlocksTexture.png", GL_TEXTURE_2D, false);

        camera = new FPSCamera(new Vector3f(0, 0, 3), 5f);
        camera.setPerspective(90, 1, 0.1f, 100f);

        chunk = new Chunk(0,0,0);
        chunk.initChunk(generator);
        chunk.generateChunk();
        chunk.initVAO();
        chunk.uploadData();

        wireframeRenderer = new WireFrameRender(3f);



        glEnable(GL_DEPTH_TEST);
//        glEnable(GL_CULL_FACE);
//        glCullFace(GL_BACK);
//        glFrontFace(GL_CW);

        shader.use();
    }

    private static void onEnd()
    {

    }

    private static void handleBlockSelection() {
        long window = Render.getInstance().getWindow();

        if (glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS) {
            selectedBlock = BlockType.STONE;
            System.out.println("Selected Block: STONE");
        } else if (glfwGetKey(window, GLFW_KEY_2) == GLFW_PRESS) {
            selectedBlock = BlockType.DIRT;
            System.out.println("Selected Block: DIRT");
        } else if (glfwGetKey(window, GLFW_KEY_3) == GLFW_PRESS) {
            selectedBlock = BlockType.GRASS;
            System.out.println("Selected Block: GRASS");
        } else if (glfwGetKey(window, GLFW_KEY_4) == GLFW_PRESS) {
            selectedBlock = BlockType.WOOD;
            System.out.println("Selected Block: WOOD");
        }
        else if(glfwGetKey(window,GLFW_KEY_5) == GLFW_PRESS){
            selectedBlock = BlockType.LEAVES;
            System.out.println("Selected Block: LEAVES");
        }
    }


}
