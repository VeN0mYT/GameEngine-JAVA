package org.example.scripts;

import org.example.component.behaviour.Behaviour;
import org.example.imgui.reflectfield.HidePublic;
import org.example.imgui.reflectfield.Serialized;
import org.example.time.TimeFix;
import org.joml.Vector3f;

public class script extends Behaviour {

    public Vector3f position = new Vector3f();
    @HidePublic
    public int speed = 5;

    @Serialized
    private Vector3f rotation = new Vector3f();
    public float rotationSpeed = 5;

    public int abdo = 5;

    @Override
    public void start() {
        System.out.println("start");
        transform.position.x = -5;
    }

    @Override
    public void update() {
        transform.position.y = (float)Math.sin(TimeFix.getTime() * 2.0f) * 0.5f;

//        transform.rotation.y += 20 * TimeFix.getDeltaTime();

    }
}
