package org.example.camera;

import org.example.transform.Transform;
import org.joml.*;
import org.lwjgl.glfw.GLFW;

import org.example.input.Input;
import org.example.input.Mouse;

import java.lang.Math;

public class ECamera {
    private Transform transform = new Transform();
    private Matrix4f viewMatrix = new Matrix4f();
    private Matrix4f projectionMatrix = new Matrix4f();

    private float fov = 70f;
    private float aspect;
    private float near = 0.01f;
    private float far = 1000f;

    public float speed = 5f;
    public float sensitivity = 0.1f;

    private double lastMouseX, lastMouseY;
    private boolean firstMouse = true;

    public ECamera(float aspect) {
        this.aspect = aspect;
        updateProjection();
    }

    public void updateProjection() {
        projectionMatrix.identity().perspective((float)Math.toRadians(fov), aspect, near, far);
    }

    private void updateViewPriv() {
        viewMatrix.identity()
                .rotateX((float)Math.toRadians(transform.rotation.x))
                .rotateY((float)Math.toRadians(transform.rotation.y))
                .translate(-transform.position.x, -transform.position.y, -transform.position.z);
    }

    public void updateView() {
        if(transform.getParent() == null)
        {
            updateViewPriv();
            return;
        }
        // Compute world matrix (includes parent position + rotation)
        Matrix4f worldMatrix = transform.getModelMatrix();

        // Invert to get view matrix
        worldMatrix.invert(viewMatrix);
    }



    public void update(float deltaTime) {
        float velocity = speed * deltaTime;

        // Movement
        if (Input.isKeyDown(GLFW.GLFW_KEY_W))
            moveForward(velocity);
        if (Input.isKeyDown(GLFW.GLFW_KEY_S))
            moveForward(-velocity);
        if (Input.isKeyDown(GLFW.GLFW_KEY_A))
            moveRight(-velocity);
        if (Input.isKeyDown(GLFW.GLFW_KEY_D))
            moveRight(velocity);
        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE))
            transform.position.y += velocity;
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
            transform.position.y -= velocity;

        // Mouse look
        double mouseX = Mouse.getX();
        double mouseY = Mouse.getY();

        if (firstMouse) {
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            firstMouse = false;
        }

        double dx = mouseX - lastMouseX;
        double dy = lastMouseY - mouseY;
        lastMouseX = mouseX;
        lastMouseY = mouseY;

        transform.rotation.y += dx * sensitivity;
        transform.rotation.x -= dy * sensitivity;

        // Clamp pitch
        if (transform.rotation.x > 89.0f) transform.rotation.x = 89.0f;
        if (transform.rotation.x < -89.0f) transform.rotation.x = -89.0f;

        updateView();
    }

    private void moveForward(float amount) {
        Vector3f forward = new Vector3f(
                (float)Math.sin(Math.toRadians(transform.rotation.y)),
                0,
                (float)-Math.cos(Math.toRadians(transform.rotation.y))
        );
        transform.position.add(forward.mul(amount));
    }

    private void moveRight(float amount) {
        Vector3f right = new Vector3f(
                (float)Math.cos(Math.toRadians(transform.rotation.y)),
                0,
                (float)Math.sin(Math.toRadians(transform.rotation.y))
        );
        transform.position.add(right.mul(amount));
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Transform getTransform() {
        return transform;
    }

    public float GetFov() {
        return fov;
    }

    public void SetFov(float fov) {
        this.fov = fov;
        updateProjection();
    }
}
