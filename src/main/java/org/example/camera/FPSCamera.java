package org.example.camera;

import org.example.input.Input;
import org.example.input.Mouse;
import org.example.time.Time;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class FPSCamera extends Camera {

    private float moveSpeed = 2.5f;
    private float sensitivity = 0.1f;
    private boolean enabled = true;

    public FPSCamera(Vector3f position, float speed) {
        super(position, new Vector3f(0.0f, 1.0f, 0.0f), -90.0f, 0.0f);
        this.moveSpeed = speed;
        Mouse.hideCursor(); // lock mouse at start
    }

    @Override
    public void update(float deltaTime) {

        if(Input.isKeyDown(GLFW.GLFW_KEY_M))
            enable(!enabled);

        if (!enabled) return;
        processKeyboard(deltaTime);
        processMouse();
    }

    public void processKeyboard(float deltaTime) {
        float velocity = moveSpeed * deltaTime;

        if (Input.isKeyDown(GLFW.GLFW_KEY_W))
            position.add(new Vector3f(front).mul(velocity));
        if (Input.isKeyDown(GLFW.GLFW_KEY_S))
            position.sub(new Vector3f(front).mul(velocity));
        if (Input.isKeyDown(GLFW.GLFW_KEY_A))
            position.sub(new Vector3f(right).mul(velocity));
        if (Input.isKeyDown(GLFW.GLFW_KEY_D))
            position.add(new Vector3f(right).mul(velocity));
        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE))
            position.add(new Vector3f(up).mul(velocity));
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL))
            position.sub(new Vector3f(up).mul(velocity));


    }

    private void processMouse() {
        if (Mouse.isCursorVisible() || !enabled) return;

        float xoffset = (float) Mouse.getDeltaX() * sensitivity;
        float yoffset = (float) -Mouse.getDeltaY() * sensitivity;

        yaw += xoffset;
        pitch += yoffset;

        if (pitch > 89.0f) pitch = 89.0f;
        if (pitch < -89.0f) pitch = -89.0f;

        updateVectors();
    }

    @Override
    public void processMouse(float xoffset, float yoffset) {
        if (!enabled) return;

        xoffset *= sensitivity;
        yoffset *= sensitivity;

        yaw += xoffset;
        pitch += yoffset;

        // Constrain pitch to avoid screen flipping
        if (pitch > 89.0f) pitch = 89.0f;
        if (pitch < -89.0f) pitch = -89.0f;

        // Update front, right and up vectors
        updateVectors();
    }

    public void enable(boolean enable) {
        this.enabled = enable;
        if (enable) Mouse.hideCursor();
        else Mouse.showCursor();
    }
}
