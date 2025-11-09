package org.example.input;

import org.lwjgl.glfw.GLFW;

public class Input {
    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] lastKeys = new boolean[GLFW.GLFW_KEY_LAST];

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (key >= 0 && key < keys.length)
            keys[key] = (action != GLFW.GLFW_RELEASE);
    }

    public static void update() {
        System.arraycopy(keys, 0, lastKeys, 0, keys.length);
    }

    public static boolean isKeyDown(int key) {
        return keys[key];
    }

    public static boolean isKeyPressed(int key) {
        return keys[key] && !lastKeys[key];
    }

    public static boolean isKeyReleased(int key) {
        return !keys[key] && lastKeys[key];
    }
}
