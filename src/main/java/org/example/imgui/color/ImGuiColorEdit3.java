package org.example.imgui.color;

import org.example.imgui.ImGuiElement;

public class ImGuiColorEdit3 implements ImGuiElement {
    private final String label;
    private final float[] value;

    public ImGuiColorEdit3(String label) {
        this.label = label;
        this.value = new float[3];
        this.value[0] = 1;
        this.value[1] = 1;
        this.value[2] = 1;
    }

    @Override
    public void render() {
        imgui.ImGui.colorEdit3(label, value);
    }

    public float[] getValue() {
        return value;
    }

    @Override
    public String getName() {
        return label;
    }
}
