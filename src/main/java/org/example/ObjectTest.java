package org.example;

import org.example.camera.ECamera;
import org.example.camera.FPSCamera;
import org.example.component.MeshRender;
import org.example.component.EngineObject;
import org.example.geometry.CapsuleGeo;
import org.example.geometry.CubeGeo;
import org.example.geometry.IGeo;
import org.example.geometry.SphereGeo;
import org.example.imgui.ImGuiWindow;
import org.example.imgui.color.ImGuiColorPicker3;
import org.example.imgui.input.*;
import org.example.imgui.list.ImGuiListBox;
import org.example.input.Input;
import org.example.input.Mouse;
import org.example.light.Light;
import org.example.render.Render;
import org.example.render.SyncMode;
import org.example.scene.Scene;
import org.example.component.Material;
import org.example.component.Mesh;
import org.example.scripts.ElFathy;
import org.example.scripts.MoveMent;
import org.example.scripts.ShapeChanger;
import org.example.scripts.script;
import org.example.time.Time;
import org.example.time.TimeFix;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

public class ObjectTest {

    public static FPSCamera camera;
    public static ECamera cam;
    public  static Scene sc;
    public  static ImGuiWindow win;

    public static Light lig;

    public static boolean isLight = false;

    public static EngineObject t = null;
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


        cam = new ECamera(1f);
        cam.getTransform().position = new Vector3f(0,3,8);
        cam.SetFov(90);

//        camera = new FPSCamera(new Vector3f(0, 0, 3), 5f);
//        camera.setPerspective(90f,1f,0.1f,100f);
//        camera.enable(true);

        sc = new Scene(cam);

        IGeo cap = new CapsuleGeo(1,2,100,30);
        IGeo cub = new CubeGeo();
        IGeo sphere = new SphereGeo(1,30,30);

        Material mat = new Material();
        Material matGreen = new Material();
        Material matRed = new Material();

        mat.setVector3("color",new Vector3f(1,0,0));
        matGreen.setVector3("color",new Vector3f(0,1,0));
        matRed.setVector3("color",new Vector3f(1,0,0));

        Mesh mesh = new Mesh(cap);
        Mesh sph = new Mesh(sphere);
        Mesh cubs = new Mesh(cub);

        Mesh LightMesh = new Mesh(new SphereGeo(1,30,30));
        Material LightMat = new Material();
        LightMat.setVector3("color",new Vector3f(1,1,1));



        EngineObject en = new EngineObject("Capsul");
        en.addComponent(mesh);
        en.addComponent(mat);
        en.addComponent(new MeshRender());
        en.addComponent(new MoveMent());
        en.addComponent(new ElFathy());

        EngineObject cp = new EngineObject("Shpere");
        cp.addComponent(sph);
        cp.addComponent(matGreen);
        cp.addComponent(new MeshRender());
//        cp.addComponent(script.class);

        EngineObject ca = new EngineObject("Cube");
        ca.addComponent(cubs);
        ca.addComponent(matRed);
        ca.addComponent(new MeshRender());
        ca.addComponent(new script());

        EngineObject el = new EngineObject("ElFathy");
        el.addComponent(new ElFathy());
        el.addComponent(new MeshRender());
        el.addComponent(new Mesh(new SphereGeo(1,30,30)));
        el.addComponent(new Material());
        el.addComponent(new ShapeChanger());


        EngineObject light = new EngineObject("Light");
        light.addComponent(LightMesh);
        light.addComponent(LightMat);
        light.addComponent(new MeshRender());

//        light.getMaterial().setBoolean("hasLight",false);
        light.getComponent(Material.class,"Material").setBoolean("hasLight",false);
        light.getTransform().position = new Vector3f(0,0,0);
        light.getTransform().scale = new Vector3f(0.2f,0.2f,0.2f);

        en.getTransform().position = new Vector3f(0,0,0);
        en.getTransform().rotation = new Vector3f(0,0,0);
        cp.getTransform().position = new Vector3f(2,0,0);
        ca.getTransform().position = new Vector3f(-2,2,0);

//        cam.getTransform().setParent(en.getTransform());
        cp.getTransform().setParent(en.getTransform());
        ca.getTransform().setParent(cp.getTransform());

        light.getTransform().setParent(sc.GetLight().getTransform());

        sc.addObject(en);
        sc.addObject(cp);
        sc.addObject(ca);
        sc.addObject(light);
        sc.addObject(el);

        lig = sc.GetLight();

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


        Vector3f dir = new Vector3f();
        if(Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            dir.add(cam.getTransform().forward().mul(0.2f));
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            dir.add(cam.getTransform().forward().mul(-0.2f));
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            dir.add(cam.getTransform().right().mul(-0.2f));
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            dir.add(cam.getTransform().right().mul(0.2f));
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
            dir.add(cam.getTransform().up().mul(0.2f));
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            dir.add(cam.getTransform().up().mul(-0.2f));      //need a better desiegn
        }



        float sensitivity = 0.1f;

        cam.getTransform().position.add(dir);
        cam.getTransform().rotate(new Vector3f((float)Mouse.getDeltaY() * sensitivity,(float)Mouse.getDeltaX() * sensitivity,0));
       // tem.getTransform().rotate(new Vector3f(0,(float)Mouse.getDeltaX() * sensitivity,0));


//        if(tem.getTransform().equals(sc.GetObject("Cube").getTransform()))
//        {
//            tem.setMaterial(sc.GetObject("Cube").getMaterial());
//        }
//
//        if(tem.getTransform().equals(sc.GetObject("Shpere").getTransform()))
//        {
//            tem.setMaterial(sc.GetObject("Shpere").getMaterial());
//        }



        ImGuiListBox l = (ImGuiListBox)win.getElement("List");
        ImGuiColorPicker3 cp = (ImGuiColorPicker3)win.getElement("Color");
        ImGuiCheckbox cb = (ImGuiCheckbox)win.getElement("ORColor");
        ImGuiInputFloat3 x = (ImGuiInputFloat3)win.getElement("position");
        ImGuiInputFloat3 y = (ImGuiInputFloat3)win.getElement("rotation");
        ImGuiInputFloat3 z = (ImGuiInputFloat3)win.getElement("scale");
        ImGuiInputFloat intensity = (ImGuiInputFloat)win.getElement("Intensity");

        if(!l.getSelectedItem().equals("Light")) {
            if (t != sc.GetObject(l.getSelectedItem())) {
                t = sc.GetObject(l.getSelectedItem());


                x.setValue(t.getTransform().position);
                y.setValue(t.getTransform().rotation);
                z.setValue(t.getTransform().scale);

            }



            t.getComponent(Material.class,"Material").setVector3("color", cp.getColor());
            t.getComponent(Material.class,"Material").setBoolean("hasColor", cb.isChecked());

            t.getTransform().position = x.getVectorValue();
            t.getTransform().rotation = y.getVectorValue();
            t.getTransform().scale = z.getVectorValue();
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
