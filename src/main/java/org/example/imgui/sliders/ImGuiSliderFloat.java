package org.example.imgui.sliders;

import org.example.imgui.ImGuiElement;

public class ImGuiSliderFloat implements ImGuiElement {
    private final String label;
    private float[] value;
    private final float min;
    private final float max;

    public ImGuiSliderFloat(String label, float initialValue, float min, float max) {
        this.label = label;
        this.value = new float[]{initialValue};
        this.min = min;
        this.max = max;
    }

    @Override
    public void render() {

        imgui.ImGui.sliderFloat(label, value, min, max);
    }

    public float getValue() {
        return value[0];
    }

    @Override
    public String getName() {
        return label;
    }
}
