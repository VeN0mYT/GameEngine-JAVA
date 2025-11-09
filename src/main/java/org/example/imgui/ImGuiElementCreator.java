package org.example.imgui;

import org.example.imgui.input.*;
import org.joml.Vector3f;

import java.lang.reflect.Field;

public class ImGuiElementCreator {


    //add special type for component of the engine later

    public static ImGuiElement  create(String type, String name, Object value) {
//        System.out.println(type + " here");
        switch (type){
            case "int":
                return new ImGuiInputInt(name,value);
            case  "float":
                return new ImGuiInputFloat(name,value);
            case "org.joml.Vector3f":
                return new ImGuiInputFloat3(name,value);
            case "boolean":
                return new ImGuiCheckbox(name,value);
        }
        return new ImGuiButton("error  "+name,()->{
            System.out.println("error type "+ name);
        });
    }
}
