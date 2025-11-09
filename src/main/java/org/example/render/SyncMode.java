package org.example.render;

public enum SyncMode {
    VSYNC_ON(1),
    VSYNC_OFF(0);

    private final int glfwValue;

    SyncMode(int glfwValue) {
        this.glfwValue = glfwValue;
    }

    public int getGlfwValue() {
        return glfwValue;
    }
}
