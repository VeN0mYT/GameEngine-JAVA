package org.example.imgui.color;

import org.example.imgui.ImGuiElement;
import org.joml.Vector3f;

public class ImGuiColorPicker3 implements ImGuiElement {
    private final String label;
    private final float[] values;

    public ImGuiColorPicker3(String label) {
        this.label = label;
        this.values = new float[3];
        this.values[0] = 1;
        this.values[1] = 1;
        this.values[2] = 1;
    }

    @Override
    public void render() {
        imgui.ImGui.colorPicker3(label, values);
    }

    public float[] getValues() {
        return values;
    }

    public Vector3f getColor()
    {
        return new Vector3f(values[0], values[1], values[2]);
    }

    public void setColor(Vector3f color)
    {
        values[0] = color.x;
        values[1] = color.y;
        values[2] = color.z;
    }

    @Override
    public String getName() {
        return label;
    }
}
