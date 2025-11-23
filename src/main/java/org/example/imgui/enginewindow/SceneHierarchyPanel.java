package org.example.imgui.enginewindow;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import org.example.ECS.EngineObjectFix;
import org.example.ECS.SceneFix;
import org.example.transform.Transform;

import java.util.ArrayList;
import java.util.List;

public class SceneHierarchyPanel  implements EngineWindow {
    private final SceneFix scene;
    private EngineObjectFix selectedObject = null;
    private final List<EngineObjectFix> deletedObjects = new ArrayList<>();

    public SceneHierarchyPanel(SceneFix scene) {
        this.scene = scene;
    }

    public void render() {
        ImGui.begin("Scene Hierarchy");

        ImGui.text("Scene Hierarchy (Drag here to un-parent)");

        if (ImGui.beginDragDropTarget()) {
            Object payload = ImGui.acceptDragDropPayload("ENGINE_OBJECT");
            if (payload instanceof EngineObjectFix dragged) {
                dragged.setParent((EngineObjectFix) null);   // → Send object back to ROOT
            }
            ImGui.endDragDropTarget();
        }

        ImGui.separator();

        // Loop through all top-level objects (those without parent)
        for (EngineObjectFix obj : scene.getEngineObjectMap().values()) {
            if (obj.transform.getParent() == null) {
                drawObjectNode(obj);
            }
        }

        if(!deletedObjects.isEmpty())
        {
            for(EngineObjectFix obj : deletedObjects)
                obj.Destroy();

            deletedObjects.clear();
        }

        ImGui.end();
    }

    private void drawObjectNode(EngineObjectFix object) {

        int flags = ImGuiTreeNodeFlags.OpenOnArrow |
                ImGuiTreeNodeFlags.SpanAvailWidth;

        List<Transform> children = object.transform.getChildren();

        // highlight selected
        if (selectedObject == object)
            flags |= ImGuiTreeNodeFlags.Selected;

        // mark leaf if no children
        if (children.isEmpty())
            flags |= ImGuiTreeNodeFlags.Leaf;

        // UNIQUE ID FIX: required to avoid ImGui ID conflicts
        boolean open = ImGui.treeNodeEx(object.getName() + "##" + object.getId(), flags);

        // Select
        if (ImGui.isItemClicked()) {
            selectedObject = object;
        }




        // ───────────────────────────────
        // DRAG SOURCE
        // ───────────────────────────────
        if (ImGui.beginDragDropSource()) {
            // Send pointer or ID
            ImGui.setDragDropPayload("ENGINE_OBJECT", object);
            ImGui.text("Move: " + object.getName());
            ImGui.endDragDropSource();
        }



        // ───────────────────────────────
        // DROP TARGET
        // ───────────────────────────────
        if (ImGui.beginDragDropTarget()) {
            Object payload = ImGui.acceptDragDropPayload("ENGINE_OBJECT");
            if (payload instanceof EngineObjectFix dragged) {

                // Prevent dropping onto itself
                if (dragged != object) {
                    dragged.setParent(object); // reparent!
                }

            }
            ImGui.endDragDropTarget();
        }

        // Context Menu
        if (ImGui.beginPopupContextItem()) {
            if (ImGui.menuItem("Delete"))
                deletedObjects.add(object);
            if (ImGui.menuItem("Rename"))
                System.out.println("Rename requested for " + object.getName());
            ImGui.endPopup();
        }

        // Recursive draw
        if (open) {
            for (Transform child : children) {
                EngineObjectFix childObj = scene.getObjectByTransform(child);
                drawObjectNode(childObj);
            }
            ImGui.treePop();
        }
    }



}
