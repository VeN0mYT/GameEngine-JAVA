package org.example;

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
import org.example.imgui.SceneHierarchyPanel;
import org.example.input.Input;
import org.example.input.Mouse;
import org.example.render.Render;
import org.example.render.SyncMode;
import org.example.scripts.newscriptsystem.ECStest;
import org.example.scripts.newscriptsystem.LightFinder;
import org.example.scripts.newscriptsystem.MoveCube;
import org.example.scripts.newscriptsystem.testNewMaterial;
import org.example.scripts.script;
import org.example.shader.Shader;
import org.example.shader.ShaderType;
import org.example.texture.Texture;
import org.example.time.Time;
import org.example.time.TimeFix;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL11.*;

public class TestECS {
    public static SceneFix sc;
    public static Material mat;

    public static SceneHierarchyPanel sceneHierarchyPanel;
    public static void main(String[] args) {
        Render render = Render.getInstance(800, 600, "objectTest", SyncMode.VSYNC_ON);

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
        sc.render();
        sceneHierarchyPanel.render();
    }

    static void onEnd()
    {

    }
}
