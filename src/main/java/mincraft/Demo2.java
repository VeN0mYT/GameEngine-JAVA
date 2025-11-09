package mincraft;

import mincraft.block.BlockType;
import mincraft.block.Chunk;
import mincraft.block.ChunkManager;
import mincraft.raycast.RayCast;
import mincraft.raycast.WireFrameRender;
import org.example.render.*;
import org.example.shader.*;
import org.example.camera.*;
import org.example.texture.Texture;
import org.example.time.Time;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Demo2 {

    private static FPSCamera camera;
    private static Texture texture;
    private static ChunkManager chunkManager;

    private static BlockType selectedBlock = BlockType.STONE;

    private static boolean mouseLeftPressed = false;
    private static boolean mouseRightPressed = false;

    private static WireFrameRender wireframeRenderer;
    private static Vector3f hitBlock;

    private static boolean[] keysPressed = new boolean[350]; // GLFW keys max ~349
    private static boolean[] mousePressed = new boolean[8];  // Mouse buttons


    public static void main(String[] args) {
        Render instance = Render.getInstance(800, 600, "Minecraft Demo2", SyncMode.VSYNC_ON);
        instance.setInit(Demo2::init);
        instance.setOnRender(Demo2::onRender);
        instance.setOnEnd(Demo2::onEnd);
        instance.loop();
    }

    private static void onRender() {
        float delta = Time.getDeltaTime();
        camera.update(delta);

        Shader shader = ShaderManager.getInstance().getShader("min");
        shader.use();

        shader.getUniforms().setMat4("u_model", new org.joml.Matrix4f().identity());
        shader.getUniforms().setMat4("u_view", camera.getViewMatrix());
        shader.getUniforms().setMat4("u_projection", camera.getProjectionMatrix());

        shader.getUniforms().setVec3("lightDir", new Vector3f(-0.3f, -1.0f, -0.5f).normalize()); // direction of light
        shader.getUniforms().setVec3("lightColor", new Vector3f(1.0f, 1.0f, 1.0f));             // white light
        shader.getUniforms().setVec3("ambientColor", new Vector3f(0.3f, 0.3f, 0.3f));
        shader.getUniforms().setInt("u_texture", 0);

        handleMouse();
        handleBlockSelection();

        // Handle mouse clicks


        Vector3f camPos = camera.getPosition();
        Vector3f dir = camera.getFront();

        // Update chunks around player
        chunkManager.update(camPos);
        chunkManager.uploadReadyChunks();

        // Block interaction
        hitBlock = RayCast.getHitBlock(camPos, dir, chunkManager);
        if (hitBlock != null && mouseRightPressed) {
            Chunk c = chunkManager.getChunkFromWorld((int)hitBlock.x,(int)hitBlock.y, (int)hitBlock.z);
            if (c != null) {
                c.setBlock((int)hitBlock.x - c.getWorldX(), (int)hitBlock.y-c.getWorldY(), (int)hitBlock.z - c.getWorldZ(), BlockType.AIR);
                c.generateChunk();
                c.uploadData();
            }
        }

        Vector3f placePos = RayCast.getPlacePosition(camPos, dir, chunkManager);
        if (placePos != null && mouseLeftPressed) {
            Chunk c = chunkManager.getChunkFromWorld((int) placePos.x,(int)placePos.y, (int) placePos.z);
            if (c != null) {
                c.setBlock((int) placePos.x - c.getWorldX(), (int) placePos.y-c.getWorldY(), (int) placePos.z - c.getWorldZ(), selectedBlock);
                c.generateChunk();
                c.uploadData();
            }
        }

        // Render chunks
        texture.use(0);
        shader.getUniforms().setFloat("u_texture", 0);

        texture.bind();
        chunkManager.render();
        texture.unbind();

        // Render wireframe on hit block
        if (hitBlock != null) {
            wireframeRenderer.renderWireCube(hitBlock.x, hitBlock.y, hitBlock.z,
                    camera.getViewMatrix(), camera.getProjectionMatrix());
        }
    }

    private static void init() {
        Shader shader = new Shader("min");
        shader.readShader(ShaderType.VERTEX, "i:\\Java Projects\\OpenGl\\ShadersFiles\\Mvertex");
        shader.readShader(ShaderType.FRAGMENT, "i:\\Java Projects\\OpenGl\\ShadersFiles\\Mfragment");
        shader.compileShader();
        ShaderManager.getInstance().addShader(shader);

        texture = new Texture();
        texture.loadImage("I:\\Java Projects\\OpenGl\\Assits\\BlocksTexture.png", GL_TEXTURE_2D, false);

        camera = new FPSCamera(new Vector3f(0, 50, 0), 5f);
        camera.setPerspective(90, 1, 0.1f, 1000f);

        wireframeRenderer = new WireFrameRender(3f);

        chunkManager = new ChunkManager();

        glEnable(GL_DEPTH_TEST);
//        glEnable(GL_CULL_FACE);
//        glCullFace(GL_BACK);
//        glFrontFace(GL_CW);

        shader.use();
    }

    private static void onEnd() {

        chunkManager.shutdown();
    }

    private static void handleMouse() {
        long window = Render.getInstance().getWindow();

        boolean left = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS;
        if (left && !mousePressed[GLFW_MOUSE_BUTTON_LEFT]) mouseLeftPressed = true;
        else mouseLeftPressed = false;
        mousePressed[GLFW_MOUSE_BUTTON_LEFT] = left;

        boolean right = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS;
        if (right && !mousePressed[GLFW_MOUSE_BUTTON_RIGHT]) mouseRightPressed = true;
        else mouseRightPressed = false;
        mousePressed[GLFW_MOUSE_BUTTON_RIGHT] = right;
    }

    private static void handleBlockSelection() {
        long window = Render.getInstance().getWindow();

        checkKey(window, GLFW_KEY_1, () -> selectedBlock = BlockType.STONE);
        checkKey(window, GLFW_KEY_2, () -> selectedBlock = BlockType.DIRT);
        checkKey(window, GLFW_KEY_3, () -> selectedBlock = BlockType.GRASS);
        checkKey(window, GLFW_KEY_4, () -> selectedBlock = BlockType.WOOD);
        checkKey(window, GLFW_KEY_5, () -> selectedBlock = BlockType.LEAVES);
    }

    private static void checkKey(long window, int key, Runnable action) {
        boolean isPressed = glfwGetKey(window, key) == GLFW_PRESS;
        if (isPressed && !keysPressed[key]) { // only trigger once
            action.run();
        }
        keysPressed[key] = isPressed;
    }
}
