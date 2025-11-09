package org.example.render;

import org.example.imgui.theme.ImGuiTheme;
import org.example.input.Input;
import org.example.input.Mouse;
import org.example.time.TimeFix;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;


public final class Render {
     private long window;
     private static Render instance;

    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

     private int width = 800;
     private int height = 600;
     private String title = "LWJGL Window";
     private SyncMode syncMode = SyncMode.VSYNC_ON;

     private Runnable init;
     private Runnable onRender;
     private Runnable onEnd;

    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;

     public Render(int width, int height, String title, SyncMode syncMode) {
         GLFWErrorCallback.createPrint(System.err).set();

         this.width = width;
         this.height = height;
         this.title = title;
         this.syncMode = syncMode;

         if (!glfwInit()) {
             throw new IllegalStateException("Unable to initialize GLFW");
         }

         glfwDefaultWindowHints();
         glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
         glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

         window = glfwCreateWindow(width, height, title, NULL, NULL);
         if (window == NULL) {
             throw new RuntimeException("Failed to create the GLFW window");
         }

         glfwMakeContextCurrent(window);
         glfwSwapInterval(syncMode.getGlfwValue()); // vSync: 0 = off, 1 = on
         glfwShowWindow(window);

         GLFW.glfwSetKeyCallback(window, Input::keyCallback);

         GL.createCapabilities();

         ImGui.createContext();
         ImGuiIO io = ImGui.getIO();

         imGuiGlfw.init(window, true);
         imGuiGl3.init("#version 330 core");

         ImGuiTheme.applyDarkTheme();



     }

     public static Render getInstance(int width, int height, String title, SyncMode syncMode){
         if(instance == null)
         {
             instance = new Render(width, height, title, syncMode);

         }
         return instance;
     }

    public static Render getInstance(){
        if(instance == null)
        {
            instance = new Render(800, 600, "LWJGL Window", SyncMode.VSYNC_ON);

        }
        return instance;
    }

     private void update()
     {
         glfwPollEvents();
         glfwSwapBuffers(window);
     }

     public void loop()
     {
         init.run();
         Mouse.init();
         while (!glfwWindowShouldClose(window)) {


             TimeFix.update();

             glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


             imGuiGlfw.newFrame();
             ImGui.newFrame();

             onRender.run();

             ImGui.render();
             imGuiGl3.renderDrawData(ImGui.getDrawData());

             Mouse.update();
             Input.update();
             update();
         }
         onEnd.run();
         destroy();
         imGuiGl3.dispose();
         imGuiGlfw.dispose();
         ImGui.destroyContext();
         GLFW.glfwDestroyWindow(window);
         org.lwjgl.glfw.GLFW.glfwTerminate();
         if (cursorPosCallback != null) {
             cursorPosCallback.close();
         }
         if (mouseButtonCallback != null) {
             mouseButtonCallback.close();
         }
     }


     public void destroy()
     {
         glfwFreeCallbacks(window);
         glfwDestroyWindow(window);
         glfwTerminate();
     }

    public void setInit(Runnable init) {
        this.init = init;
    }

    public void setOnRender(Runnable onRender) {
        this.onRender = onRender;
    }

    public void setOnEnd(Runnable onEnd) {
        this.onEnd = onEnd;
    }

    public long getWindow() {
         return window;
    }

    public int getWidth() {
         return width;
    }

    public int getHeight() {
         return height;
    }

    public String getTitle() {
         return title;
    }

    public SyncMode getSyncMode() {
         return syncMode;
    }
}
