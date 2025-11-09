package org.example.component;

import org.example.camera.ECamera;
import org.example.geometry.IGeo;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public final class Mesh extends Component{
    public List<Vector3f> vertices;
    public List<Vector2f> uv;
    public List<Vector3f> colors;
    public List<Vector3f> normals;
    public List<Integer> indices;

    private float[] data;



    // ---- Constructors ----

    public Mesh(List<Vector3f> vertices, List<Integer> indices) {
        this(vertices, null, null, null, indices);
    }

    public Mesh(IGeo g) {
        this(g.vertex, g.uv, g.color, g.normal, g.index);
    }

    public Mesh(List<Vector3f> vertices, List<Vector2f> uv, List<Vector3f> colors,
                List<Vector3f> normals, List<Integer> indices) {
        this.vertices = vertices;
        this.uv = uv;
        this.colors = colors;
        this.normals = normals;
        this.indices = indices;

        // If normals not provided, recalculate them
        if (this.normals == null) {
            recalculateNormals();
        }

        if(uv != null)  //add default uv if not provided
        {
            for(int i = 0;i<vertices.size();i++)
            {
                uv.add(new Vector2f(0,0));
            }
        }

        if(colors != null)  //add default colors if not provided
        {
            for(int i = 0;i<vertices.size();i++)
            {
                colors.add(new Vector3f(1,1,1));
            }
        }

        CollectData();


    }

    // ---- Recalculate Normals (like Unity) ----

    public void recalculateNormals() {
        normals = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            normals.add(new Vector3f(0, 0, 0));
        }

        for (int i = 0; i < indices.size(); i += 3) {
            int i0 = indices.get(i);
            int i1 = indices.get(i + 1);
            int i2 = indices.get(i + 2);

            Vector3f v0 = vertices.get(i0);
            Vector3f v1 = vertices.get(i1);
            Vector3f v2 = vertices.get(i2);

            Vector3f edge1 = new Vector3f(v1).sub(v0);
            Vector3f edge2 = new Vector3f(v2).sub(v0);
            Vector3f normal = edge1.cross(edge2).normalize();

            normals.get(i0).add(normal);
            normals.get(i1).add(normal);
            normals.get(i2).add(normal);
        }

        for (Vector3f n : normals) {
            n.normalize();
        }
    }

    // ---- Collect Data for VBO ----

    private void CollectData() {
        List<Float> temp = new ArrayList<>();

        for (int i = 0; i < vertices.size(); i++) {
            Vector3f pos = vertices.get(i);
            temp.add(pos.x);
            temp.add(pos.y);
            temp.add(pos.z);

            if (uv != null) {
                Vector2f uvVal = uv.get(i);
                temp.add(uvVal.x);
                temp.add(uvVal.y);
            }

            if (colors != null) {
                Vector3f col = colors.get(i);
                temp.add(col.x);
                temp.add(col.y);
                temp.add(col.z);
            }

            Vector3f norm = normals.get(i);
            temp.add(norm.x);
            temp.add(norm.y);
            temp.add(norm.z);
        }

        data = ConvertFloat(temp);
    }

    // ---- Reset (for updating mesh data dynamically) ----

    public void Reset()
    {
        if(normals == null)
            recalculateNormals();
        CollectData();
    }

    // ---- Helpers ----

    private float[] ConvertFloat(List<Float> l) {
        float[] arr = new float[l.size()];
        for (int i = 0; i < l.size(); i++) arr[i] = l.get(i);
        return arr;
    }

    private int[] ConvertInt(List<Integer> l) {
        int[] arr = new int[l.size()];
        for (int i = 0; i < l.size(); i++) arr[i] = l.get(i);
        return arr;
    }

    // ---- Render ----



    public float[] getData() {
        return data;
    }

    public int[] getIndices() {
        return ConvertInt(indices);
    }

    public List<Integer> getIndicesList() {
        return indices;
    }

    public List<Vector3f> getVerticesList() {
        return vertices;
    }

    public List<Vector2f> getUVList() {
        return uv;
    }

    public List<Vector3f> getColorsList() {
        return colors;
    }

    public List<Vector3f> getNormalsList() {
        return normals;
    }






    public String getName() {
        return "Mesh";
    }
}
