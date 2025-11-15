package org.example.texture;

import static org.lwjgl.opengl.ARBInternalformatQuery2.GL_TEXTURE_2D;

public enum TextureType {
    TEXTURE_2D(GL_TEXTURE_2D);
    public final int glType;
    TextureType(int glType) { this.glType = glType; }
}
