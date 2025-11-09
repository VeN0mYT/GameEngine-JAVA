package org.example.imgui.sliders;

import org.example.imgui.ImGuiElement;

public class ImGuiSliderInt implements ImGuiElement {
    private final String label;
    private int[] value;
    private final int min;
    private final int max;

    public ImGuiSliderInt(String label, int initialValue, int min, int max) {
        this.label = label;
        this.value = new int[]{initialValue};
        this.min = min;
        this.max = max;
    }

    @Override
    public void render() {
        imgui.ImGui.sliderInt(label, value, min, max);
    }

    public int getValue() {
        return value[0];
    }

    @Override
    public String getName() {
        return label;
    }
}
