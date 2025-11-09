package org.example.scripts;

import org.example.component.behaviour.Behaviour;
import org.example.time.TimeFix;

public class ElFathy extends Behaviour {

    @Override
    public void start() {
        System.out.println("error");
        transform.position.x = 9;
    }

    @Override
    public void update() {
        transform.position.y = (float)Math.sin(TimeFix.getTime() * 3.0f) * 0.8f;

        transform.rotation.y += 40 * TimeFix.getDeltaTime();

    }
}
