package org.example.imgui.input;

import org.example.imgui.ImGuiElement;
import imgui.type.ImInt;

public class ImGuiInputInt implements ImGuiElement{
    private final String label;
    private final ImInt value;

    public ImGuiInputInt(String label, int initialValue) {
        this.label = label;
        this.value = new ImInt(initialValue);
    }

    public ImGuiInputInt(String label, Object value) {
        this.label = label;
        if(value == null)
        {
            this.value = new ImInt(0);
            return;
        }
        this.value = new ImInt((int)value);
    }

    @Override
    public void render() {
        imgui.ImGui.inputInt(label, value);
    }

    public int getValue() {
        return value.get();
    }

    @Override
    public String getName() {
        return label;
    }
}
