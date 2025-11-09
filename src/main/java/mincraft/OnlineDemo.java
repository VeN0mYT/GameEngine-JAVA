package mincraft;



import mincraft.block.BlockType;
import mincraft.block.ChunkManager;
import mincraft.network.GameClient;
import mincraft.network.PacketAddBlock;
import mincraft.network.PacketPlayerPosition;
import mincraft.network.PacketRemoveBlock;
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

public class OnlineDemo {

    private GameClient netClient;
    private FPSCamera camera;
    private Texture texture;
    private ChunkManager chunkManager;
    private WireFrameRender wireframeRenderer;

    private BlockType selectedBlock = BlockType.STONE;
    private boolean[] keysPressed = new boolean[350];
    private boolean[] mousePressed = new boolean[8];
    private boolean mouseLeftPressed = false;
    private boolean mouseRightPressed = false;
    private Vector3f hitBlock;

    public void init() {
        try {
            netClient = new GameClient("localhost"); // connect to server
        } catch (Exception e) {
            e.printStackTrace();
        }

        Render instance = Render.getInstance(800, 600, "Minecraft Online", SyncMode.VSYNC_ON);
        instance.setInit(() -> {
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
            shader.use();
        });

        instance.setOnRender(this::gameLoop);
        instance.setOnEnd(this::shutdown);
        instance.loop();
    }

    private void gameLoop() {
        float delta = Time.getDeltaTime();
        camera.update(delta);

        Shader shader = ShaderManager.getInstance().getShader("min");
        shader.use();
        shader.getUniforms().setMat4("u_model", new org.joml.Matrix4f().identity());
        shader.getUniforms().setMat4("u_view", camera.getViewMatrix());
        shader.getUniforms().setMat4("u_projection", camera.getProjectionMatrix());
        shader.getUniforms().setVec3("lightDir", new Vector3f(-0.3f, -1.0f, -0.5f).normalize());
        shader.getUniforms().setVec3("lightColor", new Vector3f(1f, 1f, 1f));
        shader.getUniforms().setVec3("ambientColor", new Vector3f(0.3f, 0.3f, 0.3f));
        shader.getUniforms().setInt("u_texture", 0);

        handleMouse();
        handleBlockSelection();

        Vector3f camPos = camera.getPosition();
        Vector3f dir = camera.getFront();

        // Sync world updates from server
        netClient.pollUpdates(chunkManager);

        chunkManager.update(camPos);
        chunkManager.uploadReadyChunks();

        // Send player position update to server
        PacketPlayerPosition posPacket = new PacketPlayerPosition();
        posPacket.playerId = 1; // TODO: assign unique id
        posPacket.x = camPos.x;
        posPacket.y = camPos.y;
        posPacket.z = camPos.z;
        netClient.send(posPacket);

        // Block interactions
        hitBlock = RayCast.getHitBlock(camPos, dir, chunkManager);
        if (hitBlock != null && mouseRightPressed) {
            netClient.send(new PacketRemoveBlock() {{
                x = (int) hitBlock.x;
                y = (int) hitBlock.y;
                z = (int) hitBlock.z;
            }});

        }

        Vector3f placePos = RayCast.getPlacePosition(camPos, dir, chunkManager);
        if (placePos != null && mouseLeftPressed) {
            netClient.send(new PacketAddBlock() {{
                x = (int) placePos.x;
                y = (int) placePos.y;
                z = (int) placePos.z;
                blockId = selectedBlock.ordinal();
            }});
        }

        // Render chunks
        texture.use(0);
        shader.getUniforms().setFloat("u_texture", 0);
        texture.bind();
        chunkManager.render();
        texture.unbind();

        // Wireframe highlight
        if (hitBlock != null) {
            wireframeRenderer.renderWireCube(hitBlock.x, hitBlock.y, hitBlock.z,
                    camera.getViewMatrix(), camera.getProjectionMatrix());
        }
    }

    private void handleMouse() {
        long window = Render.getInstance().getWindow();

        boolean left = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS;
        mouseLeftPressed = left && !mousePressed[GLFW_MOUSE_BUTTON_LEFT];
        mousePressed[GLFW_MOUSE_BUTTON_LEFT] = left;

        boolean right = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS;
        mouseRightPressed = right && !mousePressed[GLFW_MOUSE_BUTTON_RIGHT];
        mousePressed[GLFW_MOUSE_BUTTON_RIGHT] = right;
    }

    private void handleBlockSelection() {
        long window = Render.getInstance().getWindow();
        checkKey(window, GLFW_KEY_1, () -> selectedBlock = BlockType.STONE);
        checkKey(window, GLFW_KEY_2, () -> selectedBlock = BlockType.DIRT);
        checkKey(window, GLFW_KEY_3, () -> selectedBlock = BlockType.GRASS);
        checkKey(window, GLFW_KEY_4, () -> selectedBlock = BlockType.WOOD);
        checkKey(window, GLFW_KEY_5, () -> selectedBlock = BlockType.LEAVES);
    }

    private void checkKey(long window, int key, Runnable action) {
        boolean isPressed = glfwGetKey(window, key) == GLFW_PRESS;
        if (isPressed && !keysPressed[key]) action.run();
        keysPressed[key] = isPressed;
    }

    private void shutdown() {
        netClient.disconnect();
        chunkManager.shutdown();
    }

    public static void main(String[] args) {
        new OnlineDemo().init();
    }
}
