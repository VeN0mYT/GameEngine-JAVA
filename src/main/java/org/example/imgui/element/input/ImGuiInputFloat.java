package org.example.imgui.element.input;

import imgui.type.ImFloat;
import org.example.imgui.element.ImGuiElement;

public class ImGuiInputFloat implements ImGuiElement {
    private final String label;
    private final ImFloat value;

    public ImGuiInputFloat(String label, float initialValue) {
        this.label = label;
        this.value = new ImFloat(initialValue);
    }

    public ImGuiInputFloat(String label,Object obj)
    {
        this.label = label;
        if(obj == null)
        {
            this.value = new ImFloat(0);
            return;
        }
        this.value = new ImFloat((float)obj);
    }

    @Override
    public void render() {

        imgui.ImGui.inputFloat(label, value);
    }

    public void setValue(float value) {
         this.value.set(value);
    }

    public float getValue() {
        return value.get();
    }

    @Override
    public String getName() {
        return label;
    }
}
