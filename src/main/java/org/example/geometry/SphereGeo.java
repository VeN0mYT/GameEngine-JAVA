package org.example.geometry;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class SphereGeo extends IGeo {

    public SphereGeo()
    {
        this(1,30,30);
    }

    public SphereGeo(float radius, int segments, int rings) {
        vertex = new java.util.ArrayList<>();
        color = new java.util.ArrayList<>();
        uv = new java.util.ArrayList<>();
        index = new java.util.ArrayList<>();
        normal = new java.util.ArrayList<>();

        // ===== Generate vertices, UVs, colors, and normals =====
        for (int y = 0; y <= rings; y++) {
            float v = (float) y / rings;
            float phi = (float) (v * Math.PI); // 0 → π (latitude)

            for (int x = 0; x <= segments; x++) {
                float u = (float) x / segments;
                float theta = (float) (u * Math.PI * 2.0); // 0 → 2π (longitude)

                float nx = (float) (Math.cos(theta) * Math.sin(phi));
                float ny = (float) (Math.cos(phi));
                float nz = (float) (Math.sin(theta) * Math.sin(phi));

                float px = radius * nx;
                float py = radius * ny;
                float pz = radius * nz;

                // Position
                vertex.add(new Vector3f(px, py, pz));

                // Normal (unit vector)
                normal.add(new Vector3f(nx, ny, nz));

                // UV (cylindrical projection)
                uv.add(new Vector2f(u, 1.0f - v));

                // Color gradient (top = light, bottom = dark)
                color.add(new Vector3f(
                        0.2f + 0.8f * (1 - v),
                        0.3f + 0.5f * (1 - v),
                        1.0f
                ));
            }
        }

        // ===== Indices =====
        int vertsPerRow = segments + 1;

        for (int y = 0; y < rings; y++) {
            for (int x = 0; x < segments; x++) {
                int i0 = y * vertsPerRow + x;
                int i1 = i0 + vertsPerRow;
                int i2 = i0 + 1;
                int i3 = i1 + 1;

                index.add(i0);
                index.add(i1);
                index.add(i2);
                index.add(i2);
                index.add(i1);
                index.add(i3);
            }
        }
    }
}
