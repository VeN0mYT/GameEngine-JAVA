package org.example.imgui.input;

import imgui.type.ImDouble;
import org.example.imgui.ImGuiElement;

public class ImGuiInputDouble implements ImGuiElement{
    private final String label;
    private final ImDouble value;

    public ImGuiInputDouble(String label, double initialValue) {
        this.label = label;
        this.value = new ImDouble(initialValue);
    }

    @Override
    public void render() {
        imgui.ImGui.inputDouble(label, value);
    }

    public double getValue() {
        return value.get();
    }

    @Override
    public String getName() {
        return label;
    }
}
