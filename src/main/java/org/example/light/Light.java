package org.example.light;

import org.example.transform.Transform;
import org.joml.Vector3f;

public class Light {
    private Transform transform;
    private Vector3f color;
    private float intensity;

    public Light(Vector3f position, Vector3f color, float intensity) {
        this.transform = new Transform();
        this.transform.position = position;
        this.color = color;
        this.intensity = intensity;
    }

    public Vector3f getPosition() {
        return transform.position;
    }

    public Vector3f getColor() {
        return color;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setPosition(Vector3f position) {
        this.transform.position = position;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public  Transform getTransform() {
        return transform;
    }
    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}
