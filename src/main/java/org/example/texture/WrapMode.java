package org.example.texture;

import static org.lwjgl.opengl.GL11C.GL_REPEAT;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.GL_MIRRORED_REPEAT;

public enum WrapMode {
    REPEAT(GL_REPEAT),
    CLAMP(GL_CLAMP_TO_EDGE),
    MIRRORED_REPEAT(GL_MIRRORED_REPEAT);

    public final int glMode;
    WrapMode(int glMode) { this.glMode = glMode; }
}
