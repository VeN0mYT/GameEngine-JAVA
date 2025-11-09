package org.example.imgui.color;

import org.example.imgui.ImGuiElement;
import org.example.transform.Color;
import org.joml.Vector4f;

public class imGuiColorPicker4 implements ImGuiElement {
    private String label;
    private float[] values;

    public imGuiColorPicker4(String label, float[] values) {
        this.label = label;
        this.values = values;
    }

    public imGuiColorPicker4(String label, Color color) {
       this(label,(Vector4f)color);
    }

    public imGuiColorPicker4(String label, Object color)
    {
        if( !(color instanceof Vector4f))
            throw new IllegalArgumentException("Color must be a Vector4f or Color");

        Vector4f v = (Vector4f)color;
        this.label = label;
        this.values = new float[] { v.x, v.y, v.z, v.w };

    }

    public imGuiColorPicker4(String label, Vector4f color) {
        this.label = label;
        this.values = new float[] { color.x, color.y, color.z, color.w };
    }


    public float[] getValues() {
        return values;
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    public Vector4f getColorV() {
        return new Vector4f(values[0], values[1], values[2], values[3]);
    }

    public void setColor(Vector4f color) {
        values[0] = color.x;
        values[1] = color.y;
        values[2] = color.z;
        values[3] = color.w;
    }

    public void setColor(Color color) {
        setColor((Vector4f)color);
    }

    public Color getColor() {
        return new Color(values[0], values[1], values[2], values[3]);
    }

    @Override
    public void render() {
        imgui.ImGui.colorPicker4(label, values);
    }

    @Override
    public String getName() {
        return label;
    }
}
