package org.example.geometry;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;

public class CubeGeo extends IGeo {

    public CubeGeo() {
        vertex = new ArrayList<>();
        index = new ArrayList<>();
        uv = new ArrayList<>();
        color = new ArrayList<>();
        normal = new ArrayList<>();

        Vector3f[] faceVertices = new Vector3f[]{
            new Vector3f(-0.5f, -0.5f,  0.5f),
            new Vector3f( 0.5f, -0.5f,  0.5f),
            new Vector3f( 0.5f,  0.5f,  0.5f),
            new Vector3f(-0.5f,  0.5f,  0.5f),

            // Back
            new Vector3f( 0.5f, -0.5f, -0.5f),
            new Vector3f(-0.5f, -0.5f, -0.5f),
            new Vector3f(-0.5f,  0.5f, -0.5f),
            new Vector3f( 0.5f,  0.5f, -0.5f),

            // Left
            new Vector3f(-0.5f, -0.5f, -0.5f),
            new Vector3f(-0.5f, -0.5f,  0.5f),
            new Vector3f(-0.5f,  0.5f,  0.5f),
            new Vector3f(-0.5f,  0.5f, -0.5f),

            // Right
            new Vector3f( 0.5f, -0.5f,  0.5f),
            new Vector3f( 0.5f, -0.5f, -0.5f),
            new Vector3f( 0.5f,  0.5f, -0.5f),
            new Vector3f( 0.5f,  0.5f,  0.5f),

            // Top
            new Vector3f(-0.5f,  0.5f,  0.5f),
            new Vector3f( 0.5f,  0.5f,  0.5f),
            new Vector3f( 0.5f,  0.5f, -0.5f),
            new Vector3f(-0.5f,  0.5f, -0.5f),

            // Bottom
            new Vector3f(-0.5f, -0.5f, -0.5f),
            new Vector3f( 0.5f, -0.5f, -0.5f),
            new Vector3f( 0.5f, -0.5f,  0.5f),
            new Vector3f(-0.5f, -0.5f,  0.5f),
        };

        for (Vector3f v : faceVertices)
            vertex.add(v);

        // Each face = 2 triangles = 6 indices
        int[] faceIndices = {
                0, 1, 2, 2, 3, 0,       // front
                4, 5, 6, 6, 7, 4,       // back
                8, 9, 10, 10, 11, 8,    // left
                12, 13, 14, 14, 15, 12, // right
                16, 17, 18, 18, 19, 16, // top
                20, 21, 22, 22, 23, 20  // bottom
        };

        for (int i : faceIndices)
            index.add(i);

        Vector2f[] faceUVs = new Vector2f[]{
                new Vector2f(0.0f, 0.0f),
                new Vector2f(1.0f, 0.0f),
                new Vector2f(1.0f, 1.0f),
                new Vector2f(0.0f, 1.0f),
        };

        for(int i = 0;i<6;i++)
         uv.addAll(Arrays.asList(faceUVs));



        Vector3f[] faceColors = new Vector3f[]{
                new Vector3f(1.0f, 0.0f, 0.0f), // front - red
                new Vector3f(0.0f, 1.0f, 0.0f), // back - green
                new Vector3f(0.0f, 0.0f, 1.0f), // left - blue
                new Vector3f(1.0f, 1.0f, 0.0f), // right - yellow
                new Vector3f(1.0f, 0.0f, 1.0f), // top - magenta
                new Vector3f(0.0f, 1.0f, 1.0f), // bottom - cyan
        };

        for(int i = 0;i<6;i++)
            color.addAll(Arrays.asList(faceColors));

        // Face normals
        Vector3f[] faceNormals = new Vector3f[]{
                new Vector3f(0, 0, 1),   // front
                new Vector3f(0, 0, -1),  // back
                new Vector3f(-1, 0, 0),  // left
                new Vector3f(1, 0, 0),   // right
                new Vector3f(0, 1, 0),   // top
                new Vector3f(0, -1, 0),  // bottom
        };

        for (Vector3f n : faceNormals)
            for (int j = 0; j < 4; j++)
                normal.add(n);

    }


}
