package org.example.imgui.theme;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.ImVec4;
import imgui.flag.ImGuiCol;

public class ImGuiTheme{

    public static void applyDarkTheme() {
        ImGuiStyle style = ImGui.getStyle();
        float[][] colors = style.getColors();

        // Base grayscale palette
        float[] bgDark     = new float[]{0.10f, 0.105f, 0.11f, 1.00f};
        float[] bgMid      = new float[]{0.14f, 0.15f, 0.16f, 1.00f};
        float[] bgLight    = new float[]{0.18f, 0.19f, 0.20f, 1.00f};
        float[] accentBlue = new float[]{0.26f, 0.59f, 0.98f, 1.00f};
        float[] accentHover= new float[]{0.35f, 0.65f, 1.00f, 1.00f};
        float[] accentActive= new float[]{0.20f, 0.50f, 0.90f, 1.00f};
        float[] textNormal = new float[]{0.95f, 0.96f, 0.98f, 1.00f};
        float[] textDisabled = new float[]{0.50f, 0.50f, 0.50f, 1.00f};

        // Core colors
        colors[ImGuiCol.Text]               = textNormal;
        colors[ImGuiCol.TextDisabled]       = textDisabled;
        colors[ImGuiCol.WindowBg]           = bgDark;
        colors[ImGuiCol.ChildBg]            = bgDark;
        colors[ImGuiCol.PopupBg]            = new float[]{0.08f, 0.09f, 0.10f, 1.00f};
        colors[ImGuiCol.Border]             = new float[]{0.20f, 0.20f, 0.20f, 0.40f};
        colors[ImGuiCol.BorderShadow]       = new float[]{0, 0, 0, 0};

        // Frames / panels
        colors[ImGuiCol.FrameBg]            = bgMid;
        colors[ImGuiCol.FrameBgHovered]     = bgLight;
        colors[ImGuiCol.FrameBgActive]      = accentActive;

        colors[ImGuiCol.TitleBg]            = bgDark;
        colors[ImGuiCol.TitleBgActive]      = bgMid;
        colors[ImGuiCol.TitleBgCollapsed]   = bgDark;

        colors[ImGuiCol.MenuBarBg]          = bgMid;

        // Scrollbars
        colors[ImGuiCol.ScrollbarBg]        = bgDark;
        colors[ImGuiCol.ScrollbarGrab]      = bgLight;
        colors[ImGuiCol.ScrollbarGrabHovered] = accentHover;
        colors[ImGuiCol.ScrollbarGrabActive]  = accentActive;

        // Buttons
        colors[ImGuiCol.Button]             = bgMid;
        colors[ImGuiCol.ButtonHovered]      = accentHover;
        colors[ImGuiCol.ButtonActive]       = accentActive;

        // Tabs
        colors[ImGuiCol.Tab]                = bgMid;
        colors[ImGuiCol.TabHovered]         = accentHover;
        colors[ImGuiCol.TabActive]          = accentActive;
        colors[ImGuiCol.TabUnfocused]       = bgMid;
        colors[ImGuiCol.TabUnfocusedActive] = bgLight;

        // Headers (collapsing sections)
        colors[ImGuiCol.Header]             = bgMid;
        colors[ImGuiCol.HeaderHovered]      = accentHover;
        colors[ImGuiCol.HeaderActive]       = accentActive;

        // Checkboxes, sliders, etc.
        colors[ImGuiCol.CheckMark]          = accentBlue;
        colors[ImGuiCol.SliderGrab]         = accentBlue;
        colors[ImGuiCol.SliderGrabActive]   = accentActive;

        // Resize grips
        colors[ImGuiCol.ResizeGrip]         = bgLight;
        colors[ImGuiCol.ResizeGripHovered]  = accentHover;
        colors[ImGuiCol.ResizeGripActive]   = accentActive;

        // Selections
        colors[ImGuiCol.PlotLines]          = accentBlue;
        colors[ImGuiCol.PlotHistogram]      = accentBlue;
        colors[ImGuiCol.TextSelectedBg]     = new float[]{0.26f, 0.59f, 0.98f, 0.35f};


        style.setWindowRounding(6.0f);
        style.setFrameRounding(4.0f);
        style.setGrabRounding(4.0f);
        style.setPopupRounding(6.0f);
        style.setScrollbarRounding(9.0f);
        style.setTabRounding(5.0f);

        style.setFramePadding(6, 4);
        style.setItemSpacing(8, 6);
        style.setWindowPadding(10, 10);
        style.setIndentSpacing(20);

        style.setWindowBorderSize(1.0f);
        style.setFrameBorderSize(0.0f);
    }
}
