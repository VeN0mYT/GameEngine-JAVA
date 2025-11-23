package org.example;

import org.example.ECS.EngineObjectFix;
import org.example.ECS.SceneFix;
import org.example.ECS.systems.BehaviourSystem;
import org.example.ECS.systems.RenderSystem;
import org.example.component.MeshRender;
import org.example.geometry.CapsuleGeo;
import org.example.geometry.CubeGeo;
import org.example.geometry.SphereGeo;
import org.example.imgui.element.ImGuiWindow;
import org.example.imgui.element.color.ImGuiColorPicker3;
import org.example.imgui.element.input.ImGuiCheckbox;
import org.example.imgui.element.input.ImGuiInputFloat;
import org.example.imgui.element.input.ImGuiInputFloat3;
import org.example.imgui.element.list.ImGuiListBox;
import org.example.input.Input;
import org.example.input.Mouse;
import org.example.light.Light;
import org.example.render.Render;
import org.example.render.SyncMode;
import org.example.component.Material;
import org.example.component.Mesh;
import org.example.scripts.ElFathy;
import org.example.scripts.MoveMent;
import org.example.scripts.ShapeChanger;
import org.example.scripts.script;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

public class ObjectTest {


    public  static SceneFix sc;
    public  static ImGuiWindow win;

    public static Light lig;

    public static boolean isLight = false;

    public static EngineObjectFix t = null;
    public static void main(String[] args) {
        Render render = Render.getInstance(800, 600, "objectTest", SyncMode.VSYNC_ON);

        render.setInit(ObjectTest::init);
        render.setOnRender(ObjectTest::onRender);
        render.setOnEnd(ObjectTest::onEnd);
        render.loop();
    }

    public static void init()
    {

        win = new ImGuiWindow("Controlles",100,100,200,200);
        win.addElement(new ImGuiColorPicker3("Color"));
        ImGuiListBox l = new ImGuiListBox("List");
        l.addItem("Capsul");
        l.addItem("Shpere");
        l.addItem("Cube");
        l.addItem("Light");
        l.addItem("ElFathy");
        win.addElement(l);
        win.addElement(new ImGuiCheckbox("ORColor",false));
        win.addElement(new ImGuiInputFloat3("position"));
        win.addElement(new ImGuiInputFloat3("rotation"));
        win.addElement(new ImGuiInputFloat3("scale"));
        win.addElement(new ImGuiInputFloat("Intensity",1));




        sc = new SceneFix();
        sc.addSystem(new BehaviourSystem());
        sc.addSystem(new RenderSystem());


        EngineObjectFix en = sc.createEngineObject("Capsul");
        en.AddComponent(new Mesh(new CapsuleGeo(1,2,100,30)));
        en.AddComponent(new Material("Stander"));
        en.AddComponent(new MeshRender());
        en.AddComponent(new MoveMent());
        en.AddComponent(new ElFathy());
        en.transform.position = new Vector3f(0,0,0);
        en.transform.rotation = new Vector3f(0,0,0);

        EngineObjectFix cp = sc.createEngineObject("Shpere");
        cp.AddComponent(new Mesh(new SphereGeo(1,30,100)));
        cp.AddComponent(new Material("Stander"));
        cp.AddComponent(new MeshRender());
        cp.transform.position = new Vector3f(2,0,0);

        EngineObjectFix ca = sc.createEngineObject("Cube");
        ca.AddComponent(new Mesh(new CubeGeo()));
        ca.AddComponent(new Material("Stander"));
        ca.AddComponent(new MeshRender());
        ca.AddComponent(new script());
        ca.transform.position = new Vector3f(-2,2,0);

        EngineObjectFix el = sc.createEngineObject("ElFathy");
        el.AddComponent(new ElFathy());
        el.AddComponent(new Material());
        el.AddComponent(new Mesh(new SphereGeo(1,30,30)));
        el.AddComponent(new MeshRender());
        el.AddComponent(new ShapeChanger());

        EngineObjectFix light = sc.createEngineObject("Light");
        light.AddComponent(new Mesh(new SphereGeo(1,30,30)));
        light.AddComponent(new Material("Stander"));
        light.AddComponent(new MeshRender());
        light.transform.position = new Vector3f(0,0,0);
        light.transform.scale = new Vector3f(0.2f,0.2f,0.2f);




        cp.transform.setParent(en.transform);
        ca.transform.setParent(cp.transform);

        light.transform.setParent(sc.getLight().getTransform());

        lig = sc.getLight();

        sc.start();
        glEnable(GL_DEPTH_TEST);

    }

    public static void onRender()
    {

//        long start = System.nanoTime();




        if(Input.isKeyPressed(GLFW.GLFW_KEY_M))
        {
            if(Mouse.isCursorVisible())
                Mouse.hideCursor();
            else
                Mouse.showCursor();
        }


        ImGuiListBox l = (ImGuiListBox)win.getElement("List");
        ImGuiColorPicker3 cp = (ImGuiColorPicker3)win.getElement("Color");
        ImGuiCheckbox cb = (ImGuiCheckbox)win.getElement("ORColor");
        ImGuiInputFloat3 x = (ImGuiInputFloat3)win.getElement("position");
        ImGuiInputFloat3 y = (ImGuiInputFloat3)win.getElement("rotation");
        ImGuiInputFloat3 z = (ImGuiInputFloat3)win.getElement("scale");
        ImGuiInputFloat intensity = (ImGuiInputFloat)win.getElement("Intensity");

        if(!l.getSelectedItem().equals("Light")) {
            if (t != sc.findEngineObject(l.getSelectedItem())) {
                t = sc.findEngineObject(l.getSelectedItem());


                x.setValue(t.transform.position);
                y.setValue(t.transform.rotation);
                z.setValue(t.transform.scale);

            }



            t.GetComponent(Material.class).setUniform("color", cp.getColor());
            t.GetComponent(Material.class).setUniform("hasColor", cb.isChecked());

            t.transform.position = x.getVectorValue();
            t.transform.rotation = y.getVectorValue();
            t.transform.scale = z.getVectorValue();
            isLight = false;
        }
        else
        {
            if(!isLight)
            {
                x.setValue(lig.getTransform().position);
                y.setValue(lig.getTransform().rotation);
                z.setValue(lig.getTransform().rotation);
                intensity.setValue(lig.getIntensity());
                isLight = true;
            }
            lig.getTransform().position = x.getVectorValue();
            lig.setColor(cp.getColor());
            lig.setIntensity(intensity.getValue());
        }


        sc.update();
        sc.render();
        win.render();



//        // ... your update logic ...
//        long elapsed = System.nanoTime() - start;
//        System.out.println("Update time: " + (elapsed / 1_000_000.0) + " ms");


    }

    public static void onEnd()
    {

    }
}
