package org.example.imgui.element.input;

import imgui.type.ImString;
import org.example.imgui.element.ImGuiElement;

public class ImGuiInputText implements ImGuiElement {
    private final String label;
    private final ImString value;

    public ImGuiInputText(String label, String initialValue) {
        this.label = label;
        this.value = new ImString(initialValue);
    }

    @Override
    public void render() {
        imgui.ImGui.inputText(label, value);
    }

    public String getValue() {
        return value.get();
    }

    @Override
    public String getName() {
        return label;
    }

}
