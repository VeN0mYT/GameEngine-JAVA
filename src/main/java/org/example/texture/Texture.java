package org.example.texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30C.glGenerateMipmap;
import static org.lwjgl.opengl.GL33.*;

public class Texture {
    private int width;
    private int height;
    private int channels;
    private ByteBuffer data;
    private int textureID;

    public Texture() {
        textureID = glGenTextures();
    }

    public void loadImage(String path, int wrapMode, boolean flip) {
        STBImage.stbi_set_flip_vertically_on_load(flip);

        int[] w = new int[1];
        int[] h = new int[1];
        int[] c = new int[1];

        data = STBImage.stbi_load(path, w, h, c, 4); // Force RGBA
        width = w[0];
        height = h[0];
        channels = c[0];

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapMode);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapMode);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        if (data != null) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
                    GL_RGBA, GL_UNSIGNED_BYTE, data);
            glGenerateMipmap(GL_TEXTURE_2D);
            glBindTexture(GL_TEXTURE_2D, 0);
        } else {
            System.out.println("Failed to load texture: " + path);
        }

        if(data != null)
        STBImage.stbi_image_free(data);
    }

    public void generateTexture(List<Byte> byteData, int width, int height, int filterMode) {
        this.width = width;
        this.height = height;

        glBindTexture(GL_TEXTURE_2D, textureID);

        ByteBuffer buffer = BufferUtils.createByteBuffer(byteData.size());
        for (byte b : byteData) buffer.put(b);
        buffer.flip();

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RED, width, height, 0, GL_RED, GL_UNSIGNED_BYTE, buffer);

        int[] swizzleMask = {GL_RED, GL_RED, GL_RED, GL_ONE};
        glTexParameteriv(GL_TEXTURE_2D,GL_TEXTURE_SWIZZLE_RGBA, swizzleMask);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filterMode);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filterMode);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void use(int slot) {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void delete() {
        glDeleteTextures(textureID);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }
}
