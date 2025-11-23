package org.example.imgui;

import imgui.ImGui;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import org.example.imgui.enginewindow.EngineWindow;
import org.example.render.Render;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class DockingWindow
{
    private  int width = 1920;
    private int height = 1080;
    private final List<EngineWindow> windows = new ArrayList<>();

    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }


    public DockingWindow()
    {
        Render r = Render.getInstance();
        setSize(width, height);
    }
    public void render()
    {
        ImGui.setNextWindowPos(0, 0);
        ImGui.setNextWindowSize(width, height);
        ImGui.begin("DockSpace Demo",
                ImGuiWindowFlags.NoTitleBar |
                        ImGuiWindowFlags.NoResize |
                        ImGuiWindowFlags.NoMove |
                        ImGuiWindowFlags.NoCollapse |
                        ImGuiWindowFlags.MenuBar |
                        ImGuiWindowFlags.NoBringToFrontOnFocus);
        int dockspaceID = ImGui.getID("MainDockSpace");
        ImGui.dockSpace(dockspaceID, 0, 0,  ImGuiDockNodeFlags.PassthruCentralNode);

        for(EngineWindow window : windows)
        {
            window.render();
        }

        ImGui.end();

    }

    public void addWindow(EngineWindow window)
    {
        windows.add(window);
    }
}
