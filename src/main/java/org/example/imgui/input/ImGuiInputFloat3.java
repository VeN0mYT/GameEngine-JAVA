package org.example.imgui.input;

import imgui.type.ImFloat;
import org.example.imgui.ImGuiElement;
import org.joml.Vector3f;

public class ImGuiInputFloat3  implements ImGuiElement{
    private final String label;
    private final float[] value;

    public ImGuiInputFloat3(String label, float[] initialValue) {
        this.label = label;
        this.value = initialValue;
    }

    public ImGuiInputFloat3(String label, Vector3f v)
    {
        this.label = label;
        this.value = new float[3];
        this.value[0] = v.x;
        this.value[1] = v.y;
        this.value[2] = v.z;
    }

    public  ImGuiInputFloat3(String label) {
        this.label = label;
        this.value = new float[3];
        this.value[0] = 0;
        this.value[1] = 0;
        this.value[2] = 0;

    }

    public ImGuiInputFloat3(String label,Object obj)
    {
        this.label = label;
        if(obj == null)
        {
            this.value = new float[3];
            this.value[0] = 0;
            this.value[1] = 0;
            this.value[2] = 0;
            return;
        }
        Vector3f v = (Vector3f)obj;
        this.value = new float[3];
        this.value[0] = v.x;
        this.value[1] = v.y;
        this.value[2] = v.z;
    }

    public float[] getValue() {
        return value;
    }

    public Vector3f getVectorValue() {
        return new Vector3f(value[0], value[1], value[2]);
    }

    public void setValue(float x, float y, float z) {
        this.value[0] = x;
        this.value[1] = y;
        this.value[2] = z;
    }

    public void setValue(float[] value) {
        this.value[0] = value[0];
        this.value[1] = value[1];
        this.value[2] = value[2];
    }

    public void setValue(Vector3f value) {
        this.value[0] = value.x;
        this.value[1] = value.y;
        this.value[2] = value.z;
    }

    @Override
    public void render() {
        imgui.ImGui.inputFloat3(label, value);
    }

    @Override
    public String getName() {
        return label;
    }
}
