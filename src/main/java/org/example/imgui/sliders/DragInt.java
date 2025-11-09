package org.example.imgui.sliders;

import org.example.imgui.ImGuiElement;

public class DragInt implements ImGuiElement {
    private  String label;
    private int[] value;
    private float min;
    private float max;

    public DragInt(String label, int initialValue, float min, float max) {
        this.label = label;
        this.value = new int[]{initialValue};
        this.min = min;
        this.max = max;
    }

    @Override
    public void render() {
        imgui.ImGui.dragInt(label, value, min, max);
    }

    public int getValue() {
        return value[0];
    }

    @Override
    public String getName() {
        return label;
    }


}
