package org.example;

import org.example.imgui.color.ImGuiColorEdit3;
import org.example.imgui.color.ImGuiColorPicker3;
import org.example.imgui.input.ImGuiButton;
import org.example.imgui.input.ImGuiCheckbox;
import org.example.imgui.list.ImGuiListBox;
import org.example.imgui.reflectfield.ReflectFields;
import org.example.imgui.sliders.DragInt;
import org.example.imgui.sliders.ImGuiSliderFloat;
import org.example.imgui.*;
import org.example.imgui.input.ImGuiRadioButton;
import org.example.render.Render;
import org.example.render.*;
import org.example.scripts.script;

public class UiTest {

    public static  ImGuiWindow UiWindow;

    public static void main(String[] args) {
        Render render = Render.getInstance(800, 600, "UiTest", SyncMode.VSYNC_ON);

        render.setInit(UiTest::init);
        render.setOnRender(UiTest::onRender);
        render.setOnEnd(UiTest::onEnd);
        render.loop();
    }

    public static void init()
    {
        UiWindow = new ImGuiWindow("UiWindow", 10,20,200, 200);
        UiWindow.addElement(new ImGuiButton("Button", () -> {
            System.out.println("Button Clicked");
        }));

        UiWindow.addElement(new ImGuiSliderFloat("Slider", 0, 100, 50));

        UiWindow.addElement(new ImGuiCheckbox("box",true));

        UiWindow.addElement(new ImGuiRadioButton("radio", () -> {}));

        ImGuiRadioButton d = (ImGuiRadioButton)UiWindow.getElement("radio");
        d.addOption("easy");
        d.addOption("mediam");
        d.addOption("hard");

        UiWindow.addElement(new ImGuiListBox("list"));
        ImGuiListBox l = (ImGuiListBox)UiWindow.getElement("list");
        l.addItem("item1");
        l.addItem("item2");
        l.addItem("item3");


        script s = new script();

        var fields = ReflectFields.getFields(s);

        for(var f : fields) {
//            System.out.println(f);
            UiWindow.addElement(ImGuiElementCreator.create(f.type.getTypeName(), f.name, f.value));
        }



    }

    public static void onRender()
    {


        UiWindow.render();


    }

    public static void onEnd()
    {

    }
}
