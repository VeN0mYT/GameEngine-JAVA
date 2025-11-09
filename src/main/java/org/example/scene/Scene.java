package org.example.scene;

import org.example.camera.ECamera;
import org.example.component.EngineObject;
import org.example.light.Light;
import org.example.time.Time;
import org.joml.Vector3f;

import java.util.HashMap;

public class Scene {
    private ECamera camera;
//    private List<EngineObject> objects = new ArrayList<>();
    private HashMap<String,EngineObject> objects = new HashMap<>();


    private Light light;

    public Scene(ECamera camera) {

        this.camera = camera;
        light = new Light(new Vector3f(0,5,3),new Vector3f(1,1,1),5);
    }

    public Scene(ECamera camera, Light light) {
        this.camera = camera;
        this.light = light;
    }

    public void addObject(EngineObject obj) {
        obj.setLight(light);
        objects.put(obj.GetName(),obj);
    }

    public void start()
    {
        for(EngineObject obj : objects.values())
        {
            obj.start();
        }
    }

    public void update()
    {
        for(EngineObject obj : objects.values())
        {
            obj.update();
        }
    }
    public void render() {
        camera.updateView();
        for (EngineObject obj : objects.values()) {
            obj.render(camera,light);
        }
    }

    public EngineObject GetObject(String name)
    {
        if(!(objects.containsKey(name)))
            throw new IllegalArgumentException("Object with name " + name + " not found");

        return objects.get(name);
    }

    public Light GetLight()
    {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }
}
