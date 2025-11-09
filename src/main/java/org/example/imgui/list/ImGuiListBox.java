package org.example.imgui.list;

import imgui.type.ImInt;
import org.example.imgui.ImGuiElement;

import java.util.ArrayList;
import java.util.List;

public class ImGuiListBox implements ImGuiElement {
    private final String label;
    private final List<String> items;
    private final ImInt itemIndex;

    public ImGuiListBox(String label) {
        this.label = label;
        this.items = new ArrayList<>();
        this.itemIndex = new ImInt(0);
    }

    public ImGuiListBox(String label, List<String> items){
        this.label = label;
        this.items = items;
        this.itemIndex = new ImInt(0);
    }

    @Override
    public void render() {
        String[] itemsArray = items.toArray(new String[0]);
        imgui.ImGui.listBox(label, itemIndex, itemsArray);
    }

    public int getItemIndex() {
        return itemIndex.get();
    }

    public String getSelectedItem()
    {
        return items.get(itemIndex.get());
    }

    public void addItem(String item) {
        items.add(item);
    }
    public List<String> getItems() {
        return items;
    }

    public String getName() {
        return label;
    }
}
