package org.example.render;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;


public class VertexLayout {
    private final List<VertexBufferElement> elements = new ArrayList<>();
    private int stride = 0;

    public void pushFloat(int count) {
        elements.add(new VertexBufferElement(GL_FLOAT, count, false));
        stride += VertexBufferElement.getSizeOfType(GL_FLOAT) * count;
    }

    public void pushInt(int count) {
        elements.add(new VertexBufferElement(GL_UNSIGNED_INT, count, false));
        stride += VertexBufferElement.getSizeOfType(GL_UNSIGNED_INT) * count;
    }

    public void pushByte(int count) {
        elements.add(new VertexBufferElement(GL_UNSIGNED_BYTE, count, true));
        stride += VertexBufferElement.getSizeOfType(GL_UNSIGNED_BYTE) * count;
    }

    public List<VertexBufferElement> getElements() {
        return elements;
    }

    public int getStride() {
        return stride;
    }
}


class VertexBufferElement {
    private final int count;
    private final int type;
    private final boolean normalized;

    public VertexBufferElement(int type, int count, boolean normalized) {
        this.type = type;
        this.count = count;
        this.normalized = normalized;
    }

    public int getCount() {
        return count;
    }

    public int getType() {
        return type;
    }

    public boolean isNormalized() {
        return normalized;
    }

    public static int getSizeOfType(int type) {
        return switch (type) {
            case GL_FLOAT -> Float.BYTES;
            case GL_UNSIGNED_INT -> Integer.BYTES;
            case GL_UNSIGNED_BYTE -> Byte.BYTES;
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
