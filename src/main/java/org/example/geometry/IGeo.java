package org.example.geometry;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;

public abstract class IGeo {
    public List<Vector3f> vertex = null;
    public List<Integer> index = null;
    public List<Vector2f> uv = null;
    public List<Vector3f> color = null;
    public List<Vector3f> normal = null;
}
