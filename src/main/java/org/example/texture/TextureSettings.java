package org.example.texture;

import static org.lwjgl.opengl.GL11C.*;

public class TextureSettings {
    public TextureType type = TextureType.TEXTURE_2D;
    public TextureFormat format = TextureFormat.RGBA8;
    public FilterMode filter = FilterMode.MIPMAP;
    public WrapMode wrap = WrapMode.REPEAT;
    public boolean generateMipmap = true;
    public boolean srgb = false;
}
