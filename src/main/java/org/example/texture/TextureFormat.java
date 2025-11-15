package org.example.texture;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL21C.GL_SRGB8_ALPHA8;
import static org.lwjgl.opengl.GL30C.GL_R8;

public enum TextureFormat {
    RGBA8(GL_RGBA, GL_RGBA, GL_UNSIGNED_BYTE, GL_RGBA8),
    RGB8(GL_RGB, GL_RGB, GL_UNSIGNED_BYTE, GL_RGB8),
    RED8(GL_RED, GL_RED, GL_UNSIGNED_BYTE, GL_R8),
    SRGB8_A8(GL_RGBA, GL_RGBA, GL_UNSIGNED_BYTE, GL_SRGB8_ALPHA8); // sRGB color-space

    public final int format, internalFormat, type, sized;
    TextureFormat(int format, int internalFormat, int type, int sized) {
        this.format = format;
        this.internalFormat = internalFormat;
        this.type = type;
        this.sized = sized;
    }
}
