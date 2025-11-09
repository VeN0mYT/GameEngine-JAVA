package org.example.scripts.newscriptsystem;

import org.example.component.behaviour.Behaviour;
import org.example.time.TimeFix;

public class MoveCube extends Behaviour {

    @Override
    public void start() {
        transform.position.x = 5;
    }

    @Override
    public void update() {
        transform.position.y = (float)Math.sin(TimeFix.getTime() * 2.0f) * 0.5f;
        transform.rotation.y += 20 * TimeFix.getDeltaTime();
        transform.scale.x = (float)Math.sin(TimeFix.getTime() * 2.0f) * 0.5f;
    }
}
