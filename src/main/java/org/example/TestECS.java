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
import org.example.input.Input;
import org.example.input.Mouse;
import org.example.render.Render;
import org.example.render.SyncMode;
import org.example.scripts.newscriptsystem.ECStest;
import org.example.scripts.newscriptsystem.LightFinder;
import org.example.scripts.newscriptsystem.MoveCube;
import org.example.shader.Shader;
import org.example.shader.ShaderType;
import org.example.time.Time;
import org.example.time.TimeFix;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

public class TestECS {
    public static SceneFix sc;
    public static Material mat;
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




        sc.addSystem(new BehaviourSystem());
        sc.addSystem(new RenderSystem());

        Shader shader = new Shader("wav");
        shader.readShaderFile("I:\\Java Projects\\OpenGl\\ShadersFiles\\funtest\\wavVertex");



         mat = new Material(shader);


        e1.AddComponent(new Mesh(new SphereGeo(5,30,30)));
        e1.AddComponent(mat);
        e1.AddComponent(new MeshRender());
        e1.AddComponent(new ECStest());


        e2.AddComponent(new LightFinder());

        e3.AddComponent(new Mesh(new CubeGeo()));
        e3.AddComponent(new Material());
        e3.AddComponent(new MeshRender());
        e3.AddComponent(new MoveCube());





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

        mat.setFloat("time", (float)TimeFix.getTime());


        sc.update();
        sc.render();
    }

    static void onEnd()
    {

    }
}
