package org.example.transform;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Color extends Vector4f {

    public Color(float x, float y, float z) {
        super(x, y, z,1f);
    }

    public Color(float x, float y, float z, float w) {
        super(x, y, z, w);
    }

    public Color()
    {
        super(1f,1f,1f,1f);
    }

    public static Color White()
    {
        return new Color(1f,1f,1f,1f);
    }

    public static Color Black()
    {
        return new Color(0f,0f,0f,1f);
    }

    public static Color Red()
    {
        return new Color(1f,0f,0f,1f);
    }

    public static Color Green()
    {
        return new Color(0f,1f,0f,1f);
    }

    public static Color Blue()
    {
        return new Color(0f,0f,1f,1f);
    }

    public static Color Yellow()
    {
        return new Color(1f,1f,0f,1f);
    }

    public static Color Cyan()
    {
        return new Color(0f,1f,1f,1f);
    }

    public static Color Magenta()
    {
        return new Color(1f,0f,1f,1f);
    }

    public static Color Orange()
    {
        return new Color(1f,0.5f,0f,1f);
    }

    public static Color Purple()
    {
        return new Color(0.5f,0f,0.5f,1f);
    }

    public static Color Brown()
    {
        return new Color(0.5f,0.25f,0f,1f);
    }

    public static Color Gray()
    {
        return new Color(0.5f,0.5f,0.5f,1f);
    }

    public static Color Pink()
    {
        return new Color(1f,0.5f,0.5f,1f);
    }


}
