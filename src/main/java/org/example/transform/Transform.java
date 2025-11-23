package org.example.transform;

import org.example.component.Component;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public final class Transform extends Component {
    private Transform parent;
    private final List<Transform> children = new ArrayList<>();

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scale;

    public Transform() {
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(1, 1, 1);
    }

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    // ---------------------
    // 🔗 Parent/Children System
    // ---------------------
//    public void setParent(Transform parent) {
//        if(parent == this)
//            throw new RuntimeException("Cannot set parent to itself");
//
//        if (this.parent != null)
//            this.parent.children.remove(this);
//
//        this.parent = parent;
//
//        if (parent != null && !parent.children.contains(this))
//            parent.children.add(this);
//    }

    public void setParent(Transform newParent) {
        if (newParent == this)
            throw new RuntimeException("Cannot set parent to itself");

        // 1️⃣ Save world matrix before changing parent
        Matrix4f worldBefore = getModelMatrix();

        // 2️⃣ Remove from old parent
        if (parent != null)
            parent.children.remove(this);

        parent = newParent;

        // 3️⃣ Add to new parent
        if (newParent != null && !newParent.children.contains(this))
            newParent.children.add(this);

        // 4️⃣ Recompute local transform so world stays the SAME
        Matrix4f parentWorld = (parent == null)
                ? new Matrix4f().identity()
                : parent.getModelMatrix();

        Matrix4f invParent = parentWorld.invert(new Matrix4f());
        Matrix4f newLocal = invParent.mul(worldBefore);

        // Extract TRS back into local position/rotation/scale
        newLocal.getTranslation(position);
        newLocal.getScale(scale);

        rotation = decomposeRotation(newLocal);  // helper function below
    }

    private Vector3f decomposeRotation(Matrix4f m) {
        Vector3f euler = new Vector3f();

        m.getNormalizedRotation(new Quaternionf())
                .getEulerAnglesXYZ(euler);

        euler.set(
                (float)Math.toDegrees(euler.x),
                (float)Math.toDegrees(euler.y),
                (float)Math.toDegrees(euler.z)
        );

        return euler;
    }


    public List<Transform> getChildren() {
        return children;
    }

    // ---------------------
    // 🧮 Matrix
    // ---------------------
    public Matrix4f getLocalMatrix() {
        return new Matrix4f()
                .identity()
                .translate(position)
                .rotateX((float) Math.toRadians(rotation.x))
                .rotateY((float) Math.toRadians(rotation.y))
                .rotateZ((float) Math.toRadians(rotation.z))
                .scale(scale);
    }

    public Matrix4f getModelMatrix() {
        Matrix4f model = getLocalMatrix();
        if (parent != null) {
            Matrix4f parentMatrix = parent.getModelMatrix();
            parentMatrix.mul(model, model);
        }
        return model;
    }

    // ---------------------
    // 🌍 Helpers
    // ---------------------
    public Vector3f getWorldPosition() {
        Vector3f worldPos = new Vector3f();
        getModelMatrix().getTranslation(worldPos);
        return worldPos;
    }

    public Vector3f forward() {
        float yaw = (float) Math.toRadians(rotation.y);
        float pitch = (float) Math.toRadians(rotation.x);

        return new Vector3f(
                (float) (Math.cos(pitch) * Math.sin(yaw)),
                (float) Math.sin(pitch),
                (float) (Math.cos(pitch) * Math.cos(yaw) * -1)
        ).normalize();
    }

    public Vector3f right() {
        float yaw = (float) Math.toRadians(rotation.y + 90);
        return new Vector3f(
                (float) Math.sin(yaw),
                0,
                (float) -Math.cos(yaw)
        ).normalize();
    }

    public Vector3f up() {
        return new Vector3f(0, 1, 0);
    }

    // ---------------------
    // 🌀 Movement Helpers
    // ---------------------
    public void rotate(Vector3f deltaEuler) {
        rotation.add(deltaEuler);
    }

    public void setEuler(float pitch, float yaw, float roll) {
        rotation.set(pitch, yaw, roll);
    }

    public void addPosition(Vector3f delta) {
        position.add(delta);
    }

    public void addScale(Vector3f delta) {
        scale.add(delta);
    }

    public Transform getParent()
    {
        return parent;
    }

    // ---------------------
    // 🔁 Recursive Update
    // ---------------------
    public void updateHierarchy() {
        for (Transform child : children)
            child.updateHierarchy();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Transform))
            throw new RuntimeException("Object is not a Transform");

        Transform other = (Transform) obj;
        return position.equals(other.position) && rotation.equals(other.rotation) && scale.equals(other.scale);
    }



}
