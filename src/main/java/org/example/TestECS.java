package org.example;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import org.example.ECS.EngineObjectFix;
import org.example.ECS.SceneFix;
import org.example.ECS.systems.BehaviourSystem;
import org.example.ECS.systems.RenderSystem;
import org.example.component.Material;
import org.example.component.Mesh;
import org.example.component.MeshRender;
import org.example.geometry.CapsuleGeo;
import org.example.geometry.CubeGeo;
import org.example.geometry.SphereGeo;
import org.example.imgui.enginewindow.SceneHierarchyPanel;
import org.example.input.Input;
import org.example.input.Mouse;
import org.example.render.Render;
import org.example.render.SyncMode;
import org.example.scripts.newscriptsystem.ECStest;
import org.example.scripts.newscriptsystem.LightFinder;
import org.example.scripts.newscriptsystem.testNewMaterial;
import org.example.scripts.script;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class TestECS {
    public static SceneFix sc;
    public static Material mat;

    private static int framebuffer;
    private static int textureColor;
    private static int depthBuffer;

    public static SceneHierarchyPanel sceneHierarchyPanel;
    public static void main(String[] args) {
        //800 600
        Render render = Render.getInstance(1920, 1080, "objectTest", SyncMode.VSYNC_ON);

        render.setInit(TestECS::init);
        render.setOnRender(TestECS::onRender);
        render.setOnEnd(TestECS::onEnd);
        render.loop();
    }

    static void init()
    {
        sc = new SceneFix();
        EngineObjectFix e1 = sc.createEngineObject("Shape");
        EngineObjectFix e2 = sc.createEngineObject("Light");
        EngineObjectFix e3 = sc.createEngineObject("abdo");
        EngineObjectFix e4 = sc.createEngineObject("cube");
        EngineObjectFix e5 = sc.createEngineObject("capsule");


        sceneHierarchyPanel = new SceneHierarchyPanel(sc);

        sc.addSystem(new BehaviourSystem());
        sc.addSystem(new RenderSystem());



        e1.AddComponent(new Mesh(new SphereGeo(5,30,30)));
        e1.AddComponent(new testNewMaterial());
        e1.AddComponent(new MeshRender());
        e1.AddComponent(new ECStest());


        e2.AddComponent(new LightFinder());

        e3.AddComponent(new Mesh(new SphereGeo()));
        e3.AddComponent(new Material("reflect","I:\\Java Projects\\OpenGl\\ShadersFiles\\ReflectShader"));
        e3.AddComponent(new MeshRender());
//        e3.AddComponent(new MoveCube());
        e3.AddComponent(new script());

        e3.transform.scale = new Vector3f(3,3,3);
        e3.transform.position = new Vector3f(0,0,3);

        e4.AddComponent(new Mesh(new CubeGeo()));
        e4.AddComponent(new Material("reflect"));
        e4.AddComponent(new MeshRender());
        e4.AddComponent(new script());

        e4.transform.scale = new Vector3f(3,3,3);
        e4.transform.position = new Vector3f(0,0,9);

        e5.AddComponent(new Mesh(new CapsuleGeo()));
        e5.AddComponent(new Material("reflect"));
        e5.AddComponent(new MeshRender());
        e5.AddComponent(new script());

        e5.transform.scale = new Vector3f(3,3,3);
        e5.transform.position = new Vector3f(0,0,15);




//        Texture tex = new Texture();
//        tex.loadImage("I:\\Java Projects\\OpenGl\\Assits\\skybox\\back.jpg",GL_TEXTURE_2D,false);
//        e3.GetComponent(Material.class).setUniform("texture_diffuse1",tex);









        sc.start();
        glEnable(GL_DEPTH_TEST);

        int width = 1920, height = 1080;

        framebuffer = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);

        // Color texture
        textureColor = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureColor);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureColor, 0);

        // Depth renderbuffer
        depthBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
            System.err.println("Framebuffer not complete!");

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    static void onRender()
    {
        if(Input.isKeyPressed(GLFW.GLFW_KEY_M))
        {
            if(Mouse.isCursorVisible())
                Mouse.hideCursor();
            else
                Mouse.showCursor();
        }


        sc.update();

        ImGui.setNextWindowPos(0, 0);
        ImGui.setNextWindowSize(1920, 1080);
        ImGui.begin("DockSpace Demo",
                ImGuiWindowFlags.NoTitleBar |
                        ImGuiWindowFlags.NoResize |
                        ImGuiWindowFlags.NoMove |
                        ImGuiWindowFlags.NoCollapse |
                        ImGuiWindowFlags.MenuBar |
                        ImGuiWindowFlags.NoBringToFrontOnFocus);
        int dockspaceID = ImGui.getID("MainDockSpace");
        ImGui.dockSpace(dockspaceID, 0, 0,  ImGuiDockNodeFlags.PassthruCentralNode);

        // Scene hierarchy
        sceneHierarchyPanel.render();

        ImGui.end();

        // ---------- Render scene to framebuffer ----------
        glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
        glViewport(0, 0, 1920, 1080);
        glClearColor(0.1f, 0.1f, 0.1f, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        sc.render();

        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        // ---------- Show framebuffer in ImGui ----------
        ImGui.begin("OpenGL Preview");

        ImGui.image(textureColor, ImGui.getWindowWidth(), ImGui.getWindowHeight(), 0, 1, 1, 0);

        ImGui.end();
    }

    static void onEnd()
    {

    }
}
