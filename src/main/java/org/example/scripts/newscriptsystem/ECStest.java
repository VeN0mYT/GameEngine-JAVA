package org.example.scripts.newscriptsystem;

import org.example.component.Mesh;
import org.example.component.MeshRender;
import org.example.component.behaviour.Behaviour;
import org.example.geometry.CapsuleGeo;
import org.example.geometry.CubeGeo;
import org.example.geometry.SphereGeo;
import org.example.input.Input;
import org.example.time.TimeFix;
import org.lwjgl.glfw.GLFW;

public class ECStest extends Behaviour {

    Mesh[] mesh;
    int index = 0;
    int size = 3;
    MeshRender meshRender;

    @Override
    public void start() {
        mesh = new Mesh[3];
        mesh[0] = new Mesh(new SphereGeo(1,30,30));
        mesh[1] = new Mesh(new CubeGeo());
        mesh[2] = new Mesh(new CapsuleGeo(1,2,30,30));

        meshRender = engineObjectFix.GetComponent(MeshRender.class);
        meshRender.SetMesh(mesh[0]);
    }

    @Override
    public void update() {

        engineObjectFix.transform.position.y = (float)Math.sin(TimeFix.getTime()*3.0f)*0.8f;

        if(Input.isKeyPressed(GLFW.GLFW_KEY_P))
        {
            index++;
            meshRender.SetMesh(mesh[index%size]);
        }
    }
}
