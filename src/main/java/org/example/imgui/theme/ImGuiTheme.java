package org.example.imgui.theme;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.ImVec4;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiDir;

public class ImGuiTheme {

    public static void applyDark() {
        ImGui.styleColorsDark(); // optional reset

        ImGuiStyle style = ImGui.getStyle();

        // Colors
        set(style, ImGuiCol.Text,              0.95f, 0.96f, 0.98f, 1f);
        set(style, ImGuiCol.TextDisabled,      0.50f, 0.50f, 0.50f, 1f);
        set(style, ImGuiCol.WindowBg,          0.10f, 0.105f,0.11f,  1f);
        set(style, ImGuiCol.ChildBg,           0.10f, 0.105f,0.11f,  1f);
        set(style, ImGuiCol.PopupBg,           0.08f, 0.09f, 0.10f,  1f);
        set(style, ImGuiCol.Border,            0.20f, 0.20f, 0.20f, 0.4f);
        set(style, ImGuiCol.BorderShadow,      0f,    0f,    0f,    0f);

        set(style, ImGuiCol.FrameBg,           0.14f, 0.15f, 0.16f, 1f);
        set(style, ImGuiCol.FrameBgHovered,    0.18f, 0.19f, 0.20f, 1f);
        set(style, ImGuiCol.FrameBgActive,     0.20f, 0.50f, 0.90f, 1f);

        set(style, ImGuiCol.TitleBg,           0.10f,0.105f,0.11f,1f);
        set(style, ImGuiCol.TitleBgActive,     0.14f,0.15f,0.16f,1f);
        set(style, ImGuiCol.TitleBgCollapsed,  0.10f,0.105f,0.11f,1f);

        set(style, ImGuiCol.MenuBarBg,         0.14f,0.15f,0.16f,1f);

        set(style, ImGuiCol.ScrollbarBg,       0.10f,0.105f,0.11f,1f);
        set(style, ImGuiCol.ScrollbarGrab,     0.18f,0.19f,0.20f,1f);
        set(style, ImGuiCol.ScrollbarGrabHovered, 0.35f,0.65f,1f,1f);
        set(style, ImGuiCol.ScrollbarGrabActive,  0.20f,0.50f,0.90f,1f);

        // Buttons
        set(style, ImGuiCol.Button,            0.14f,0.15f,0.16f,1f);
        set(style, ImGuiCol.ButtonHovered,     0.35f,0.65f,1f,1f);
        set(style, ImGuiCol.ButtonActive,      0.20f,0.50f,0.90f,1f);

        // Tabs
        set(style, ImGuiCol.Tab,               0.14f,0.15f,0.16f,1f);
        set(style, ImGuiCol.TabHovered,        0.35f,0.65f,1f,1f);
        set(style, ImGuiCol.TabActive,         0.20f,0.50f,0.90f,1f);
        set(style, ImGuiCol.TabUnfocused,      0.14f,0.15f,0.16f,1f);
        set(style, ImGuiCol.TabUnfocusedActive,0.18f,0.19f,0.20f,1f);

        // Headers
        set(style, ImGuiCol.Header,            0.14f,0.15f,0.16f,1f);
        set(style, ImGuiCol.HeaderHovered,     0.35f,0.65f,1f,1f);
        set(style, ImGuiCol.HeaderActive,      0.20f,0.50f,0.90f,1f);

        // Checkmarks/sliders
        set(style, ImGuiCol.CheckMark,         0.26f,0.59f,0.98f,1f);
        set(style, ImGuiCol.SliderGrab,        0.26f,0.59f,0.98f,1f);
        set(style, ImGuiCol.SliderGrabActive,  0.20f,0.50f,0.90f,1f);

        // Rounding / spacing
        style.setWindowRounding(6f);
        style.setFrameRounding(4f);
        style.setGrabRounding(4f);
        style.setPopupRounding(6f);
        style.setScrollbarRounding(9f);
        style.setTabRounding(5f);

        style.setFramePadding(6, 4);
        style.setItemSpacing(8, 6);
        style.setWindowPadding(10, 10);
        style.setIndentSpacing(20);

        style.setWindowBorderSize(1f);
        style.setFrameBorderSize(0f);
    }

    public static void apply() {
        ImGui.styleColorsDark();
        ImGuiStyle style = ImGui.getStyle();

        // General
        style.setAlpha(1.0f);
        style.setDisabledAlpha(0.6f);
        style.setWindowPadding(8.0f, 8.0f);
        style.setWindowRounding(8.4f);
        style.setWindowBorderSize(1.0f);
        style.setWindowMinSize(32.0f, 32.0f);
        style.setWindowTitleAlign(0.0f, 0.5f);
        style.setWindowMenuButtonPosition(ImGuiDir.Right);
        style.setChildRounding(3.0f);
        style.setChildBorderSize(1.0f);
        style.setPopupRounding(3.0f);
        style.setPopupBorderSize(1.0f);
        style.setFramePadding(4.0f, 3.0f);
        style.setFrameRounding(3.0f);
        style.setFrameBorderSize(1.0f);
        style.setItemSpacing(8.0f, 4.0f);
        style.setItemInnerSpacing(4.0f, 4.0f);
        style.setCellPadding(4.0f, 2.0f);
        style.setIndentSpacing(21.0f);
        style.setColumnsMinSpacing(6.0f);
        style.setScrollbarSize(5.6f);
        style.setScrollbarRounding(18.0f);
        style.setGrabMinSize(10.0f);
        style.setGrabRounding(3.0f);
        style.setTabRounding(3.0f);
        style.setTabBorderSize(0.0f);
        style.setTabMinWidthForCloseButton(0.0f);
        style.setColorButtonPosition(ImGuiDir.Right);
        style.setButtonTextAlign(0.5f, 0.5f);
        style.setSelectableTextAlign(0.0f, 0.0f);

        // Colors
        set(style, ImGuiCol.Text, 1.0f, 1.0f, 1.0f, 1.0f);
        set(style, ImGuiCol.TextDisabled, 0.6f, 0.6f, 0.6f, 1.0f);
        set(style, ImGuiCol.WindowBg, 0.125f, 0.125f, 0.125f, 1.0f);
        set(style, ImGuiCol.ChildBg, 0.125f, 0.125f, 0.125f, 1.0f);
        set(style, ImGuiCol.PopupBg, 0.168f, 0.168f, 0.168f, 1.0f);
        set(style, ImGuiCol.Border, 0.251f, 0.251f, 0.251f, 1.0f);
        set(style, ImGuiCol.BorderShadow, 0f, 0f, 0f, 0f);
        set(style, ImGuiCol.FrameBg, 0.168f, 0.168f, 0.168f, 1.0f);
        set(style, ImGuiCol.FrameBgHovered, 0.216f, 0.216f, 0.216f, 1.0f);
        set(style, ImGuiCol.FrameBgActive, 0.251f, 0.251f, 0.251f, 1.0f);
        set(style, ImGuiCol.TitleBg, 0.125f, 0.125f, 0.125f, 1.0f);
        set(style, ImGuiCol.TitleBgActive, 0.168f, 0.168f, 0.168f, 1.0f);
        set(style, ImGuiCol.TitleBgCollapsed, 0.125f, 0.125f, 0.125f, 1.0f);
        set(style, ImGuiCol.MenuBarBg, 0.168f, 0.168f, 0.168f, 1.0f);
        set(style, ImGuiCol.ScrollbarBg, 0.125f, 0.125f, 0.125f, 1.0f);
        set(style, ImGuiCol.ScrollbarGrab, 0.251f, 0.251f, 0.251f, 1.0f);
        set(style, ImGuiCol.ScrollbarGrabHovered, 0.302f, 0.302f, 0.302f, 1.0f);
        set(style, ImGuiCol.ScrollbarGrabActive, 0.349f, 0.349f, 0.349f, 1.0f);
        set(style, ImGuiCol.CheckMark, 0.0f, 0.471f, 0.843f, 1.0f);
        set(style, ImGuiCol.SliderGrab, 0.0f, 0.471f, 0.843f, 1.0f);
        set(style, ImGuiCol.SliderGrabActive, 0.0f, 0.329f, 0.6f, 1.0f);
        set(style, ImGuiCol.Button, 0.168f, 0.168f, 0.168f, 1.0f);
        set(style, ImGuiCol.ButtonHovered, 0.216f, 0.216f, 0.216f, 1.0f);
        set(style, ImGuiCol.ButtonActive, 0.251f, 0.251f, 0.251f, 1.0f);
        set(style, ImGuiCol.Header, 0.216f, 0.216f, 0.216f, 1.0f);
        set(style, ImGuiCol.HeaderHovered, 0.251f, 0.251f, 0.251f, 1.0f);
        set(style, ImGuiCol.HeaderActive, 0.302f, 0.302f, 0.302f, 1.0f);
        set(style, ImGuiCol.Separator, 0.216f, 0.216f, 0.216f, 1.0f);
        set(style, ImGuiCol.SeparatorHovered, 0.251f, 0.251f, 0.251f, 1.0f);
        set(style, ImGuiCol.SeparatorActive, 0.302f, 0.302f, 0.302f, 1.0f);
        set(style, ImGuiCol.ResizeGrip, 0.216f, 0.216f, 0.216f, 1.0f);
        set(style, ImGuiCol.ResizeGripHovered, 0.251f, 0.251f, 0.251f, 1.0f);
        set(style, ImGuiCol.ResizeGripActive, 0.302f, 0.302f, 0.302f, 1.0f);
        set(style, ImGuiCol.Tab, 0.168f, 0.168f, 0.168f, 1.0f);
        set(style, ImGuiCol.TabHovered, 0.216f, 0.216f, 0.216f, 1.0f);
        set(style, ImGuiCol.TabActive, 0.251f, 0.251f, 0.251f, 1.0f);
        set(style, ImGuiCol.TabUnfocused, 0.168f, 0.168f, 0.168f, 1.0f);
        set(style, ImGuiCol.TabUnfocusedActive, 0.216f, 0.216f, 0.216f, 1.0f);
        set(style, ImGuiCol.PlotLines, 0.0f, 0.471f, 0.843f, 1.0f);
        set(style, ImGuiCol.PlotLinesHovered, 0.0f, 0.329f, 0.6f, 1.0f);
        set(style, ImGuiCol.PlotHistogram, 0.0f, 0.471f, 0.843f, 1.0f);
        set(style, ImGuiCol.PlotHistogramHovered, 0.0f, 0.329f, 0.6f, 1.0f);
        set(style, ImGuiCol.TableHeaderBg, 0.188f, 0.188f, 0.2f, 1.0f);
        set(style, ImGuiCol.TableBorderStrong, 0.310f, 0.310f, 0.349f, 1.0f);
        set(style, ImGuiCol.TableBorderLight, 0.227f, 0.227f, 0.247f, 1.0f);
        set(style, ImGuiCol.TableRowBg, 0f, 0f, 0f, 0f);
        set(style, ImGuiCol.TableRowBgAlt, 1f, 1f, 1f, 0.06f);
        set(style, ImGuiCol.TextSelectedBg, 0.0f, 0.471f, 0.843f, 1.0f);
        set(style, ImGuiCol.DragDropTarget, 1f, 1f, 0f, 0.9f);
        set(style, ImGuiCol.NavHighlight, 0.259f, 0.588f, 0.976f, 1.0f);
        set(style, ImGuiCol.NavWindowingHighlight, 1f, 1f, 1f, 0.7f);
        set(style, ImGuiCol.NavWindowingDimBg, 0.8f, 0.8f, 0.8f, 0.2f);
        set(style, ImGuiCol.ModalWindowDimBg, 0.8f, 0.8f, 0.8f, 0.35f);
    }

    private static void set(ImGuiStyle s, int col, float r, float g, float b, float a) {
        s.setColor(col, r, g, b, a);
    }
}

