package org.example.time;

import org.lwjgl.glfw.GLFW;

public class Time {
    private static float lastTime = 0.0f;

    /**
     * Returns the delta time (time between frames in seconds).
     */
    public static float getDeltaTime() {
        float currentTime = (float) GLFW.glfwGetTime();
        float delta = currentTime - lastTime;
        lastTime = currentTime;
        return delta;   // <-- fixed: return delta, not currentTime
    }

    /**
     * Resets/updates the timer to the current GLFW time.
     */
    public static void update() {
        lastTime = (float) GLFW.glfwGetTime();
    }
}
