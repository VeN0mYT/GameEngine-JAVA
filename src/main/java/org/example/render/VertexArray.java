package org.example.render;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArray {

    private int m_RendererID;

    public VertexArray()
    {
        m_RendererID = glGenVertexArrays();
    }

    public void bind()
    {
        glBindVertexArray(m_RendererID);
    }

    public void unbind()
    {
        glBindVertexArray(0);
    }

    public void addBuffer(VertexBuffer vbo, VertexLayout layout, int idx) {
        bind();
        vbo.bind();

        long offset = 0;
        for (int i = 0; i < layout.getElements().size(); i++) {
            VertexBufferElement element = layout.getElements().get(i);

            glEnableVertexAttribArray(i + idx);
            glVertexAttribPointer(
                    i + idx,
                    element.getCount(),
                    element.getType(),
                    element.isNormalized(),
                    layout.getStride(),
                    (long) offset
            );

            offset += (long)element.getCount() * VertexBufferElement.getSizeOfType(element.getType());
        }
    }

    public void addData(int location, int size, int stride, int offset) {
        bind();
        glEnableVertexAttribArray(location);
        glVertexAttribPointer(
                location,
                size,
                GL_FLOAT,
                false,
                stride * Float.BYTES,
                (long) offset
        );
    }

    public void destroy()
    {
        glDeleteVertexArrays(m_RendererID);
    }
}
