package org.example.render;

import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
public class ElementBuffer {
    private final int rendererID;

    // Constructor with data
    public ElementBuffer(int[] indices) {
        rendererID = glGenBuffers();
        addData(indices);
    }

    // Empty constructor (no data yet)
    public ElementBuffer() {
        rendererID = glGenBuffers();
    }

    // Upload data
    public void addData(int[] indices) {
        bind();

        // Convert array → IntBuffer
        IntBuffer buffer = MemoryUtil.memAllocInt(indices.length);
        buffer.put(indices).flip();

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        // Free native memory
        MemoryUtil.memFree(buffer);
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rendererID);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void destroy() {
        glDeleteBuffers(rendererID);
    }
}
