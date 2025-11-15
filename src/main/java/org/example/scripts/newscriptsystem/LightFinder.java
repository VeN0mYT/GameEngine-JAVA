package org.example.scripts.newscriptsystem;

import org.example.component.Material;
import org.example.component.Mesh;
import org.example.component.MeshRender;
import org.example.component.behaviour.Behaviour;
import org.example.geometry.SphereGeo;
import org.joml.Vector3f;

public class LightFinder extends Behaviour {

    @Override
    public void start() {
        engineObjectFix.transform.setParent(engineObjectFix.getLight().getTransform());
        Material m = new Material();
        m.setUniform("hasLight",true);
        m.setUniform("ambientColor",new Vector3f(0.15f,0.15f,0.2f));
        m.setUniform("hasColor",true);
        m.setUniform("isDirectional",true);

        engineObjectFix.AddComponent(m);
        engineObjectFix.AddComponent(new Mesh(new SphereGeo(0.3f,30,30)));
        engineObjectFix.AddComponent(new MeshRender());
    }

    @Override
    public void update() {

    }
}
