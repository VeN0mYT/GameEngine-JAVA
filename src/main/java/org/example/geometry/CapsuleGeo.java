package org.example.geometry;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class CapsuleGeo extends IGeo {

    public CapsuleGeo(float radius, float height, int segments, int rings) {
        vertex = new java.util.ArrayList<>();
        color = new java.util.ArrayList<>();
        uv = new java.util.ArrayList<>();
        index = new java.util.ArrayList<>();
        normal = new java.util.ArrayList<>();

        float halfHeight = height / 2.0f;

        // ===== Top Hemisphere =====
        for (int y = 0; y <= rings; y++) {
            float v = (float) y / rings;
            float phi = (float) (Math.PI / 2.0 * v); // 0 → π/2

            for (int x = 0; x <= segments; x++) {
                float u = (float) x / segments;
                float theta = (float) (u * Math.PI * 2.0);

                float nx = (float) (Math.cos(theta) * Math.cos(phi));
                float ny = (float) (Math.sin(phi));
                float nz = (float) (Math.sin(theta) * Math.cos(phi));

                float px = radius * nx;
                float py = radius * ny + halfHeight;
                float pz = radius * nz;

                vertex.add(new Vector3f(px, py, pz));
                normal.add(new Vector3f(nx, ny, nz));

                // UV mapping (cylindrical style)
                uv.add(new Vector2f(u, 1.0f - v * 0.5f));

                // Color (top = light blue)
                color.add(new Vector3f(0.5f, 0.7f, 1.0f));
            }
        }

        int topVertexCount = vertex.size();

        // ===== Cylinder =====
        for (int y = 0; y <= 1; y++) {
            float py = (y == 0) ? -halfHeight : halfHeight;
            float t = (float) y;

            for (int x = 0; x <= segments; x++) {
                float u = (float) x / segments;
                float theta = (float) (u * Math.PI * 2.0);

                float nx = (float) Math.cos(theta);
                float nz = (float) Math.sin(theta);
                float px = radius * nx;
                float pz = radius * nz;

                vertex.add(new Vector3f(px, py, pz));
                normal.add(new Vector3f(nx, 0.0f, nz));

                // UV mapping (straight wrap)
                uv.add(new Vector2f(u, 0.5f + t * 0.5f));

                // Color (middle = blue)
                color.add(new Vector3f(0.2f, 0.5f, 1.0f));
            }
        }

        int midVertexCount = vertex.size();

        // ===== Bottom Hemisphere =====
        for (int y = 0; y <= rings; y++) {
            float v = (float) y / rings;
            float phi = (float) (Math.PI / 2.0 * v); // 0 → π/2

            for (int x = 0; x <= segments; x++) {
                float u = (float) x / segments;
                float theta = (float) (u * Math.PI * 2.0);

                float nx = (float) (Math.cos(theta) * Math.cos(phi));
                float ny = (float) (-Math.sin(phi));
                float nz = (float) (Math.sin(theta) * Math.cos(phi));

                float px = radius * nx;
                float py = radius * ny - halfHeight;
                float pz = radius * nz;

                vertex.add(new Vector3f(px, py, pz));
                normal.add(new Vector3f(nx, ny, nz));

                // UV mapping
                uv.add(new Vector2f(u, tToUV(v)));
                color.add(new Vector3f(0.0f, 0.4f, 1.0f));
            }
        }

        // ===== Indices =====
        int vertsPerRing = segments + 1;

        // Top hemisphere
        for (int y = 0; y < rings; y++) {
            for (int x = 0; x < segments; x++) {
                int i0 = y * vertsPerRing + x;
                int i1 = i0 + vertsPerRing;
                int i2 = i0 + 1;
                int i3 = i1 + 1;
                addQuad(i0, i1, i2, i3);
            }
        }

        // Cylinder
        int cylinderStart = topVertexCount;
        for (int y = 0; y < 1; y++) {
            for (int x = 0; x < segments; x++) {
                int i0 = cylinderStart + y * vertsPerRing + x;
                int i1 = i0 + vertsPerRing;
                int i2 = i0 + 1;
                int i3 = i1 + 1;
                addQuad(i0, i1, i2, i3);
            }
        }

        // Bottom hemisphere
        int bottomStart = midVertexCount;
        for (int y = 0; y < rings; y++) {
            for (int x = 0; x < segments; x++) {
                int i0 = bottomStart + y * vertsPerRing + x;
                int i1 = i0 + vertsPerRing;
                int i2 = i0 + 1;
                int i3 = i1 + 1;
                addQuad(i0, i1, i2, i3);
            }
        }
    }

    private void addQuad(int i0, int i1, int i2, int i3) {
        index.add(i0);
        index.add(i1);
        index.add(i2);
        index.add(i2);
        index.add(i1);
        index.add(i3);
    }

    private float tToUV(float v) {
        return 0.5f - v * 0.5f; // smoothly maps bottom hemisphere UV
    }
}
