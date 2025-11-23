package org.example.imgui.element.input;

import org.example.imgui.element.ImGuiElement;

public class ImGuiButton implements ImGuiElement {
    private final String label;
    private final Runnable onClick;

    public ImGuiButton(String label, Runnable onClick) {
        this.label = label;
        this.onClick = onClick;
    }

    @Override
    public void render() {
        if (imgui.ImGui.button(label)) {
            onClick.run();
        }
    }

    @Override
    public String getName() {
        return label;
    }
}
