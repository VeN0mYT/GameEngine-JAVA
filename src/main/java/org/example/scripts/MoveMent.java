package org.example.scripts;

import org.example.component.behaviour.Behaviour;
import org.example.input.Input;
import org.lwjgl.glfw.GLFW;

public class MoveMent extends Behaviour {


    @Override
    public void start() {

    }

    @Override
    public void update() {

        if(Input.isKeyDown(GLFW.GLFW_KEY_X)) {
            transform.position.x += 0.2f;
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_Z)) {
            transform.position.x -= 0.2f;
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_C)) {
            transform.position.z += 0.2f;
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_V)) {
            transform.position.z -= 0.2f;
        }

        if(Input.isKeyDown(GLFW.GLFW_KEY_E))
        {
            transform.rotation.y += 10f;
        }
        if(Input.isKeyDown(GLFW.GLFW_KEY_Q))
        {
            transform.rotation.y -= 10f;
        }
    }
}
