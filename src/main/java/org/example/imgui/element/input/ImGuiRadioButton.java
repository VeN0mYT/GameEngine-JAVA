package org.example.imgui.element.input;

import imgui.ImGui;
import imgui.type.ImInt;
import org.example.imgui.element.ImGuiElement;

import java.util.ArrayList;
import java.util.List;

public class ImGuiRadioButton implements ImGuiElement {

    private final String label;
    private final List<String> options = new ArrayList<>();
    private final ImInt selected = new ImInt(0);
    private boolean firstRender = true;

    private Runnable onChange = () -> {};

    public ImGuiRadioButton(String label, Runnable onChange) {
        this.label = label;
        this.onChange = onChange;
    }

    public void addOption(String option) {
        options.add(option);
    }


    @Override
    public void render() {
        if (label != null && !label.isEmpty()) {
            ImGui.text(label);
        }

        // Render each option as a radio button
        for (int i = 0; i < options.size(); i++) {
            if (ImGui.radioButton(options.get(i), selected, i)) {
                onChange.run();
            }
        }
    }

    public int getSelectedIndex() {
        return selected.get();
    }

    public String getSelectedLabel() {
        if (options.isEmpty()) return null;
        return options.get(selected.get());
    }

    public void setSelectedIndex(int index) {
        if (index >= 0 && index < options.size()) {
            selected.set(index);
        }
    }

    @Override
    public String getName() {
        return label;
    }
}
