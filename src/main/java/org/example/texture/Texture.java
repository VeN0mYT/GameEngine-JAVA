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
    private TextureSettings settings;

    public Texture() {
        textureID = glGenTextures();
        settings = new TextureSettings();
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

//            glTexImage2D(GL_TEXTURE_2D, 0, GL_SRGB8_ALPHA8, width, height, 0,
//                    GL_RGBA, GL_UNSIGNED_BYTE, data);

            glGenerateMipmap(GL_TEXTURE_2D);
            glBindTexture(GL_TEXTURE_2D, 0);
        } else {
            System.out.println("Failed to load texture: " + path);
        }

        if(data != null)
        STBImage.stbi_image_free(data);
    }

    public void loadImage(String path, TextureSettings settings, boolean flip) {
        this.settings = settings;
        STBImage.stbi_set_flip_vertically_on_load(flip);

        int[] w = new int[1];
        int[] h = new int[1];
        int[] c = new int[1];

        ByteBuffer data = STBImage.stbi_load(path, w, h, c, 4);
        if (data == null) {
            System.err.println("[Texture] Failed to load image: " + path);
            return;
        }

        this.width = w[0];
        this.height = h[0];

        bind(0);

        glTexParameteri(settings.type.glType, GL_TEXTURE_WRAP_S, settings.wrap.glMode);
        glTexParameteri(settings.type.glType, GL_TEXTURE_WRAP_T, settings.wrap.glMode);
        glTexParameteri(settings.type.glType, GL_TEXTURE_MIN_FILTER, settings.filter.minFilter);
        glTexParameteri(settings.type.glType, GL_TEXTURE_MAG_FILTER, settings.filter.magFilter);

        int internalFormat = settings.srgb ? GL_SRGB8_ALPHA8 : settings.format.sized;

        glTexImage2D(settings.type.glType, 0, internalFormat, width, height, 0,
                settings.format.format, settings.format.type, data);

        if (settings.generateMipmap) glGenerateMipmap(settings.type.glType);

        unbind();
        STBImage.stbi_image_free(data);
    }

    public void loadImage(String path, boolean flip) {
        STBImage.stbi_set_flip_vertically_on_load(flip);

        int[] w = new int[1];
        int[] h = new int[1];
        int[] c = new int[1];

        ByteBuffer data = STBImage.stbi_load(path, w, h, c, 4);
        if (data == null) {
            System.err.println("[Texture] Failed to load image: " + path);
            return;
        }

        this.width = w[0];
        this.height = h[0];

        bind(0);

        glTexParameteri(settings.type.glType, GL_TEXTURE_WRAP_S, settings.wrap.glMode);
        glTexParameteri(settings.type.glType, GL_TEXTURE_WRAP_T, settings.wrap.glMode);
        glTexParameteri(settings.type.glType, GL_TEXTURE_MIN_FILTER, settings.filter.minFilter);
        glTexParameteri(settings.type.glType, GL_TEXTURE_MAG_FILTER, settings.filter.magFilter);

        int internalFormat = settings.srgb ? GL_SRGB8_ALPHA8 : settings.format.sized;

        glTexImage2D(settings.type.glType, 0, internalFormat, width, height, 0,
                settings.format.format, settings.format.type, data);

        if (settings.generateMipmap) glGenerateMipmap(settings.type.glType);

        unbind();
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

    public void createEmpty(int width, int height, TextureSettings settings) {
        this.width = width;
        this.height = height;
        this.settings = settings;

        bind(0);

        glTexParameteri(settings.type.glType, GL_TEXTURE_WRAP_S, settings.wrap.glMode);
        glTexParameteri(settings.type.glType, GL_TEXTURE_WRAP_T, settings.wrap.glMode);
        glTexParameteri(settings.type.glType, GL_TEXTURE_MIN_FILTER, settings.filter.minFilter);
        glTexParameteri(settings.type.glType, GL_TEXTURE_MAG_FILTER, settings.filter.magFilter);

        glTexImage2D(settings.type.glType, 0, settings.format.sized,
                width, height, 0, settings.format.format, settings.format.type, (ByteBuffer) null);

        if (settings.generateMipmap) glGenerateMipmap(settings.type.glType);

        unbind();
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void bind(int slot) {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(settings.type.glType, textureID);
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

    public TextureSettings getSettings() { return settings; }
}
