package org.example.ECS;

import org.example.ECS.manager.ComponentListener;
import org.example.SkyBox.SkyBox;
import org.example.camera.ECamera;
import org.example.component.Component;
import org.example.ECS.manager.ComponentManager;
import org.example.ECS.manager.EngineObjectManager;
import org.example.ECS.systems.SystemBase;
import org.example.imgui.enginewindow.EngineWindow;
import org.example.light.Light;
import org.example.render.Render;
import org.example.shader.ShaderGlobalContext;
import org.example.time.TimeFix;
import org.example.transform.Transform;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SceneFix implements EngineWindow {
    private final ECamera camera;
    private final Light light = new Light(new Vector3f(0,5,3),new Vector3f(1,1,1),5);
    private final EngineObjectManager engineObjectManager = new EngineObjectManager();
    private final ComponentManager componentManager = new ComponentManager();
    private final List<SystemBase> systems = new ArrayList<>();
    private final SkyBox skyBox;
    private final Map<String,EngineObjectFix> EngineObjectMap = new HashMap<>(); //need it for now until the editor and live compile be ready

    private final Map<Transform, EngineObjectFix> transformToObject = new HashMap<>();
    final List<EngineObjectFix> rootObjects = new ArrayList<>();



    public SceneFix()
    {
        Render r = Render.getInstance();
        camera = new ECamera((float)r.getWidth()/(float)r.getHeight());
        skyBox = new SkyBox(camera);
        ShaderGlobalContext.get().setCamera(camera);
        ShaderGlobalContext.get().setMainLight(light);
        camera.getTransform().position.z = -5;
    }
//    public EngineObjectFix createEngineObject()
//    {
//        return engineObjectManager.createEngineObject(this);            //same will be used again when the editor and live compile be ready
//    }

    public EngineObjectFix createEngineObject(String name)
    {
        EngineObjectFix e = engineObjectManager.createEngineObject(this,name);

        if(EngineObjectMap.containsKey(name))
            throw new IllegalArgumentException("EngineObject with name " + name + " already exists");

        EngineObjectMap.put(name, e);
        e.AddComponent(e.transform);

        registerObject(e);
        return e;
    }

    public <T extends Component> void addComponent(EngineObjectFix id, T component)
    {
        componentManager.add(id, component);
    }

    public <T extends Component> T getComponent(EngineObjectFix id, Class<T> type)
    {
        return componentManager.get(id, type);
    }

    public <T extends Component> void removeComponent(EngineObjectFix id, Class<T> type)
    {
        componentManager.remove(id, type);
    }

    public void destroyEngineObject(EngineObjectFix obj)
    {
//        EngineObjectMap.remove(id.getName());
//        componentManager.remove(id);
//        engineObjectManager.destroyEngineObject(id);

        for (Transform child : new ArrayList<>(obj.transform.getChildren())) {
            EngineObjectFix childObj = transformToObject.get(child);
            if (childObj != null)
                destroyEngineObject(childObj);
        }

        // Remove from maps
        EngineObjectMap.remove(obj.getName());
        unregisterObject(obj);

        componentManager.remove(obj);
        engineObjectManager.destroyEngineObject(obj);
    }

//    public void setActiveEngineObject(EngineObjectFix e,boolean active)
//    {
//        componentManager.ActiveEngineObjectComponent(e,active);
//    }

    public void addSystem(SystemBase system)
    {
        system.setComponents(componentManager);
        system.setEntities(engineObjectManager);
        systems.add(system);
        componentManager.addListener((ComponentListener)system);
    }

    public void removeSystem(SystemBase system)
    {
        systems.remove(system);
        componentManager.addListener((ComponentListener)system);
    }

    public void start() { for (var s : systems) s.start(); }

    public void update() {
        for (var s : systems)
            s.update();

        camera.update((float)TimeFix.getDeltaTime());
    }

    public void render() {

        for (var s : systems)
            s.render();

        skyBox.render();
    }

    public EngineObjectManager getEngineObjectManager() {
        return engineObjectManager;
    }

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public ECamera getCamera() {
        return camera;
    }

    public  Light getLight() {
        return light;
    }

    public EngineObjectFix findEngineObject(String id)  //need it for now until the editor and live compile be ready
    {
        return EngineObjectMap.get(id);
    }

    public Map<String,EngineObjectFix> getEngineObjectMap()
    {
        return EngineObjectMap;
    }

    public EngineObjectFix getObjectByTransform(Transform t)
    {
        return transformToObject.get(t);
    }

    public void registerObject(EngineObjectFix obj) {
        transformToObject.put(obj.transform, obj);
        rootObjects.add(obj); // by default root
    }

    public void unregisterObject(EngineObjectFix obj) {
        transformToObject.remove(obj.transform);
        rootObjects.remove(obj);
    }



}
