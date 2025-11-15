package org.example.texture;

import static org.lwjgl.opengl.GL11C.*;

public enum FilterMode {
    NEAREST(GL_NEAREST, GL_NEAREST),
    LINEAR(GL_LINEAR, GL_LINEAR),
    MIPMAP(GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR);

    public final int minFilter, magFilter;
    FilterMode(int minFilter, int magFilter) {
        this.minFilter = minFilter;
        this.magFilter = magFilter;
    }
}
