package org.example.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Camera {
    protected Vector3f position;
    protected Vector3f front;
    protected Vector3f up;
    protected Vector3f right;
    protected Vector3f worldUp;

    protected float yaw;
    protected float pitch;

    protected Matrix4f projection;

    public Camera(Vector3f position, Vector3f up, float yaw, float pitch) {
        this.position = new Vector3f(position);
        this.worldUp = new Vector3f(up);
        this.yaw = yaw;
        this.pitch = pitch;
        this.front = new Vector3f(0.0f, 0.0f, -1.0f);
        updateVectors();
        projection = new Matrix4f();
    }

    public abstract void update(float deltaTime);
    public abstract void processKeyboard(float deltaTime);
    public abstract void processMouse(float xoffset, float yoffset);

    public Matrix4f getViewMatrix() {
        Vector3f center = new Vector3f(position).add(front);
        return new Matrix4f().lookAt(position, center, up);
    }

    public Matrix4f getProjectionMatrix() {
        return projection;
    }

    public void setPerspective(float fov, float aspect, float near, float far) {
        projection.identity().perspective((float) Math.toRadians(fov), aspect, near, far);
    }

    public Vector3f getPosition() {
        return new Vector3f(position);
    }

    public Vector3f getFront() {
        return new Vector3f(front);
    }

    protected void updateVectors() {
        Vector3f newFront = new Vector3f();
        newFront.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        newFront.y = (float) Math.sin(Math.toRadians(pitch));
        newFront.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front = newFront.normalize();

        right = front.cross(worldUp, new Vector3f()).normalize();
        up = right.cross(front, new Vector3f()).normalize();
    }
}
