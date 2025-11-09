package org.example.time;

public class TimeFix {
    private static double deltaTime;
    private static double totalTime;
    private static double lastTime;

    public static void update(double currentTime) {  // need to be called in main render loop
        deltaTime = currentTime - lastTime;
//        System.out.println("update() deltaTime = " + deltaTime + " currentTime=" + currentTime);
        lastTime = currentTime;
        totalTime += deltaTime;
    }


    public static void update() {
        double currentTime = (double)(System.nanoTime() / 1_000_000_000.0);
        deltaTime = currentTime - lastTime;
        lastTime = currentTime;
        totalTime += deltaTime;
    }


    public static double getDeltaTime() {
        return deltaTime;
    }

    public static double getTime() {
        return totalTime;
    }

    public static void reset() {
        deltaTime = 0;
        totalTime = 0;
        lastTime = 0;
    }
}
