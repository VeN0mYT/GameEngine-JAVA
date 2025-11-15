package org.example.scripts.newscriptsystem;

import org.example.component.Material;
import org.example.component.behaviour.Behaviour;
import org.example.shader.Shader;
import org.joml.Vector3f;

public class testNewMaterial extends Behaviour {
    @Override
    public void start() {
        Shader shader = new Shader("waver");
        shader.readShaderFile("I:\\Java Projects\\OpenGl\\ShadersFiles\\funtest\\wavVertex");
        engineObjectFix.AddComponent(new Material(shader));
        Material m = engineObjectFix.GetComponent(Material.class);
        m.setUniform("amplitude",0.3f);
        m.setUniform("frequency",0.5f);
        m.setUniform("isDirectional",true);
        m.setUniform("hasLight",true);
        m.setUniform("ambientColor",new Vector3f(0.15f,0.15f,0.2f));
        m.setUniform("hasColor",true);

    }

    @Override
    public void update() {

    }
}
