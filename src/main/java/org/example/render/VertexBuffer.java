package org.example.render;


import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer {
    private int m_RendererID;

    public VertexBuffer(float[] vertices)
    {
        m_RendererID = glGenBuffers();
        AddData(vertices);
    }

    public VertexBuffer()
    {
        m_RendererID = glGenBuffers();
    }

    public void bind()
    {
        glBindBuffer(GL_ARRAY_BUFFER, m_RendererID);
    }

    public void AddData(float[] vertices)
    {
        bind(); // make sure buffer is bound

        // allocate native memory
        FloatBuffer buffer = MemoryUtil.memAllocFloat(vertices.length);
        buffer.put(vertices).flip();

        // upload to GPU
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        // free native memory
        MemoryUtil.memFree(buffer);
    }

    public void unbind()
    {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void destroy()
    {
        glDeleteBuffers(m_RendererID);
    }
}
