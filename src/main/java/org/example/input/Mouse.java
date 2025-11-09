package org.example.input;

import org.example.render.Render;
import org.lwjgl.glfw.GLFW;

import static java.awt.SystemColor.window;
import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private static Mouse instance;
    private long window;

    private final boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private final boolean[] lastButtons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private double mouseX, mouseY;
    private double lastX, lastY;
    private double deltaX, deltaY;
    private boolean firstMouse = true;


    private static double LastDeltaX,LastDeltaY;

    private static boolean cursorVisible = false;

    private Mouse(long window) {
        // Disable cursor for FPS-style control
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

        // Set position callback
        glfwSetCursorPosCallback(window, (windowHandle, xpos, ypos) -> {
            cursorPosCallback(xpos, ypos);
        });

        // Set mouse button callback
        glfwSetMouseButtonCallback(window, (windowHandle, button, action, mods) -> {
            mouseButtonCallback(button, action);
        });
    }

    public static void init() {
        Render renderer = Render.getInstance();
        long window = renderer.getWindow();
        instance = new Mouse(window);
        instance.window = window;
    }

    private void mouseButtonCallback(int button, int action) {
        if (button >= 0 && button < buttons.length)
            buttons[button] = (action != GLFW_RELEASE);
    }

    private void cursorPosCallback(double xpos, double ypos) {

        if(cursorVisible)
        {
            deltaX = 0;
            deltaY = 0;
            return;
        }
        if (firstMouse) {
            lastX = xpos;
            lastY = ypos;
            firstMouse = false;
        }

        deltaX = xpos - lastX;
        deltaY = ypos - lastY;
        lastX = xpos;
        lastY = ypos;

        mouseX = xpos;
        mouseY = ypos;
    }

    public static void update() {
        if (instance == null) return;

        System.arraycopy(instance.buttons, 0, instance.lastButtons, 0, instance.buttons.length);
        instance.deltaX = 0;
        instance.deltaY = 0;
    }

    public static boolean isButtonDown(int button) {
        return instance != null && instance.buttons[button];
    }

    public static boolean isButtonPressed(int button) {
        return instance != null && instance.buttons[button] && !instance.lastButtons[button];
    }

    public static boolean isButtonReleased(int button) {
        return instance != null && !instance.buttons[button] && instance.lastButtons[button];
    }

    public static double getX() {
        return instance != null ? instance.mouseX : 0.0;
    }

    public static double getY() {
        return instance != null ? instance.mouseY : 0.0;
    }

    public static double getDeltaX() {
        return instance != null ? instance.deltaX : 0.0;
    }

    public static double getDeltaY() {
        return instance != null ? instance.deltaY : 0.0;
    }

    public static void showCursor() {
        if (instance == null) return;
        glfwSetInputMode(instance.window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        cursorVisible = true;

    }

    public static void hideCursor() {
        if(instance == null) return;
        glfwSetInputMode(instance.window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        cursorVisible = false;

        double[] xpos = new double[1];
        double[] ypos = new double[1];
        glfwGetCursorPos(instance.window, xpos, ypos);
        instance.lastX = xpos[0];
        instance.lastY = ypos[0];
        instance.deltaX = 0;
        instance.deltaY = 0;
        instance.firstMouse = false;
    }

    public static boolean isCursorVisible() {
        return cursorVisible;
    }
}
