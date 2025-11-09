package org.example.imgui;

import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ImGuiWindow {
    private String title;
    private float x, y, width, height;
    private ImBoolean open = new ImBoolean(true);

    private boolean movable = true;
    private boolean resizable = true;
    private boolean collapsible = true;

    private final HashMap<String,ImGuiElement> elementsByName = new HashMap<>();
    private final List<ImGuiElement> elements = new ArrayList<>();


    public ImGuiWindow(String title, float x, float y, float width, float height) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setOpen(boolean open) {
        this.open.set(open);
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public void setCollapsible(boolean collapsible) {
        this.collapsible = collapsible;
    }

    public void addElement(ImGuiElement element) {
        elementsByName.put(element.getName(), element);
        elements.add(element);
    }

    public ImGuiElement getElement(String name)
    {
        if(!elementsByName.containsKey(name))
            throw new IllegalArgumentException("Element with name " + name + " not found");

        return elementsByName.get(name);
    }

    public void RemoveElement(String name)
    {
        if(!elementsByName.containsKey(name))
            throw new IllegalArgumentException("Element with name " + name + " not found");

        ImGuiElement element = elementsByName.remove(name);
        elements.remove(element);
    }

    public void render()
    {

        int flags = 0;

        if (!movable) flags |= ImGuiWindowFlags.NoMove;
        if (!resizable) flags |= ImGuiWindowFlags.NoResize;
        if (!collapsible) flags |= ImGuiWindowFlags.NoCollapse;

        if(!open.get()) return;

//        ImGui.setNextWindowPos(x,y);
//        ImGui.setNextWindowSize(width,height);
        if(ImGui.begin(title,open,flags))
        {
            for (ImGuiElement element : elements) {
                element.render();
            }
        }

        ImGui.end();
    }
}
