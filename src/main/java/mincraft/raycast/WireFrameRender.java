package mincraft.raycast;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class WireFrameRender {
    private float lineWidth = 2.0f;

    public WireFrameRender() {}

    public WireFrameRender(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * Render a wireframe cube at (x, y, z) with size 1
     */
    public void renderWireCube(float x, float y, float z, Matrix4f view, Matrix4f projection) {
        glColor3f(1f, 1f, 0f);
        glLineWidth(lineWidth);
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        float half = 0.5f; // Half size of the block
        float[][] vertices = {
                {x - half, y - half, z - half},
                {x + half, y - half, z - half},
                {x + half, y + half, z - half},
                {x - half, y + half, z - half},
                {x - half, y - half, z + half},
                {x + half, y - half, z + half},
                {x + half, y + half, z + half},
                {x - half, y + half, z + half}
        };

        int[][] edges = {
                {0,1},{1,2},{2,3},{3,0},
                {4,5},{5,6},{6,7},{7,4},
                {0,4},{1,5},{2,6},{3,7}
        };

        glBegin(GL_LINES);
        for (int[] edge : edges) {
            glVertex3f(vertices[edge[0]][0], vertices[edge[0]][1], vertices[edge[0]][2]);
            glVertex3f(vertices[edge[1]][0], vertices[edge[1]][1], vertices[edge[1]][2]);
        }
        glEnd();

        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }


    public void setLineWidth(float width) {
        this.lineWidth = width;
    }
}
