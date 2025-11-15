package org.example.imgui;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import org.example.ECS.EngineObjectFix;
import org.example.ECS.SceneFix;
import org.example.transform.Transform;

import java.util.List;

public class SceneHierarchyPanel {
    private final SceneFix scene;
    private EngineObjectFix selectedObject = null;

    public SceneHierarchyPanel(SceneFix scene) {
        this.scene = scene;
    }

    public void render() {
        ImGui.begin("Scene Hierarchy");

        // Loop through all top-level objects (those without parent)
        for (EngineObjectFix obj : scene.getEngineObjectMap().values()) {
            if (obj.transform.getParent() == null) {
                drawObjectNode(obj);
            }
        }

        ImGui.end();
    }

    private void drawObjectNode(EngineObjectFix object) {
        int flags = ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.SpanAvailWidth;

        // Highlight selected
        if (selectedObject == object)
            flags |= ImGuiTreeNodeFlags.Selected;

        // Mark leaf if no children
        List<Transform> children = object.transform.getChildren();
        if (children.isEmpty())
            flags |= ImGuiTreeNodeFlags.Leaf;

        boolean open = ImGui.treeNodeEx(object.getName(),flags);  //error here

        if (ImGui.isItemClicked()) {
            selectedObject = object;
        }

        // Right-click context menu
        if (ImGui.beginPopupContextItem()) {
            if (ImGui.menuItem("Delete")) {
                object.Destroy();
            }
            if (ImGui.menuItem("Rename")) {
                // you could later add inline editing or popup rename dialog
                System.out.println("Rename requested for " + object.getName());
            }
            ImGui.endPopup();
        }

        if (open) {
            for (Transform child : children) {
                drawObjectNode(findByTransform(child));
            }
            ImGui.treePop();
        }
    }

    // Helper to get EngineObjectFix by Transform
    private EngineObjectFix findByTransform(Transform t) {
        for (EngineObjectFix obj : scene.getEngineObjectMap().values()) {
            if (obj.transform == t)
                return obj;
        }
        return null;
    }

    public EngineObjectFix getSelectedObject() {
        return selectedObject;
    }
}
