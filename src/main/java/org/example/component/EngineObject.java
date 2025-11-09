package org.example.component;

import org.example.camera.ECamera;
import org.example.component.behaviour.Behaviour;
import org.example.light.Light;
import org.example.transform.Transform;

import java.util.ArrayList;
import java.util.HashMap;



public final class EngineObject {
    private final String name;
    private final Transform transform;


    private Light light;

    private final HashMap<String, Component> components = new HashMap<>();
    private final ArrayList<Component> componentPipeLine = new ArrayList<>();
    private final ArrayList<Behaviour> behaviours = new ArrayList<>();

    public EngineObject(String name)
    {
        this.name = name;
        transform = new Transform();
    }



    public void start()
    {
        for(Component c : components.values())
        {
            c.start(this);
        }

        for(Behaviour b : behaviours)
        {
            b.start();
        }
    }

    public void update()
    {
        for(Behaviour b : behaviours)
            b.update();
    }

    public void render(ECamera cam, Light light)
    {

//        meshRender.Render(transform,cam,light);
        for(Component c : componentPipeLine)
            c.update(cam);
    }

    public Transform getTransform() {
        return transform;
    }


    public void setLight(Light light) {
        this.light = light;
    }

    public Light getLight() {
        return light;
    }

    public  String GetName()
    {
        return name;
    }


    public void addComponent(Component component)
    {
        components.put(component.getName(), component);

        if(component.addToPipeLine())
            componentPipeLine.add(component);
    }

    public void addComponent(Behaviour behaviour)
    {
        components.put(behaviour.getName(), behaviour);
        behaviours.add(behaviour);
    }

    public <T extends Component> T addComponent(Class<T> type) {
        try {
            T instance = type.getDeclaredConstructor().newInstance();


            if (instance instanceof Behaviour behaviour) {
                components.put(behaviour.getName(), behaviour);
                behaviours.add(behaviour);
            }
            else {
                components.put(instance.getName(), instance);

                if (instance.addToPipeLine())
                    componentPipeLine.add(instance);
            }

            return instance;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    public <T extends Component> T getComponent(Class<T> type,String name)
    {

        if(!(components.containsKey(name))||components.get(name) == null)
            throw new IllegalArgumentException("Component with name " + name + " not found");

        return type.cast(components.get(name));
    }

    public void removeComponent(String name)
    {
        if(!(components.containsKey(name))||components.get(name) == null)
            throw new IllegalArgumentException("Component with name " + name + " not found");


        Component component = components.get(name);

        if(component instanceof Behaviour)
        {
            behaviours.remove(component);
            components.remove(name);        //need to be checked
            return;
        }

        if(component.addToPipeLine())
            componentPipeLine.remove(component);

        components.remove(name);
    }


    public void setParent(EngineObject parent) {
        transform.setParent(parent.getTransform());
    }






}
