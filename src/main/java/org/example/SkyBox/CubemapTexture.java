package org.example.SkyBox;

import java.nio.ByteBuffer;
import java.util.List;

import static org.lwjgl.opengl.AMDSeamlessCubemapPerTexture.GL_TEXTURE_CUBE_MAP_SEAMLESS;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.*;

public class CubemapTexture {
    private int textureID;

    public CubemapTexture(List<String> faces) {
        stbi_set_flip_vertically_on_load(false);

        glEnable(GL_TEXTURE_CUBE_MAP_SEAMLESS);


        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_CUBE_MAP, textureID);

        int[] w = new int[1];
        int[] h = new int[1];
        int[] c = new int[1];

        for (int i = 0; i < faces.size(); i++) {
            ByteBuffer data = stbi_load(faces.get(i), w, h, c, 0);
            if (data != null) {
                int format = (c[0] == 4) ? GL_RGBA : GL_RGB;
                glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0,
                        format, w[0], h[0], 0, format, GL_UNSIGNED_BYTE, data);
                stbi_image_free(data);
            } else {
                System.err.println("Failed to load cubemap face: " + faces.get(i) +
                        " Reason: " + stbi_failure_reason());
            }
        }

        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }


    public void bind() {
        glBindTexture(GL_TEXTURE_CUBE_MAP, textureID);
    }

    public void use(int slot) {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_CUBE_MAP, textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }

    public void delete() {
        glDeleteTextures(textureID);
    }

    public int getTextureID() {
        return textureID;
    }
}
