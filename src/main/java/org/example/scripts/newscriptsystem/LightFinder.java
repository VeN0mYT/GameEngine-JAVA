package org.example.scripts.newscriptsystem;

import org.example.component.Material;
import org.example.component.Mesh;
import org.example.component.MeshRender;
import org.example.component.behaviour.Behaviour;
import org.example.geometry.SphereGeo;

public class LightFinder extends Behaviour {

    @Override
    public void start() {
        engineObjectFix.transform.setParent(engineObjectFix.getLight().getTransform());
        engineObjectFix.AddComponent(new Material());
        engineObjectFix.AddComponent(new Mesh(new SphereGeo(0.3f,30,30)));
        engineObjectFix.AddComponent(new MeshRender());
    }

    @Override
    public void update() {

    }
}
