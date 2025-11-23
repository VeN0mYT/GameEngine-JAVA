package org.example.imgui.element.input;

import imgui.ImGui;
import imgui.type.ImBoolean;
import org.example.imgui.element.ImGuiElement;


public class ImGuiCheckbox implements ImGuiElement {
    private final String label;
    private final ImBoolean value;

    public ImGuiCheckbox(String label, boolean initialState) {
        this.label = label;
        this.value = new ImBoolean(initialState);
    }

    public ImGuiCheckbox(String label, Object value) {
        this.label = label;
        if(value == null)
            this.value = new ImBoolean(false);
        else
            this.value = new ImBoolean((boolean)value);
    }

    @Override
    public void render() {

        ImGui.checkbox(label, value);
    }



    public boolean isChecked() {
        return value.get();
    }

    @Override
    public String getName() {
        return label;
    }
}
