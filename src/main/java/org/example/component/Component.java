package org.example.component;

import org.example.ECS.EngineObjectFix;
import org.example.camera.ECamera;
import org.example.component.behaviour.Behaviour;
import org.example.light.Light;
import org.example.transform.Transform;

public  class Component  {

    public EngineObject engineObject;
    public Transform transform;
    private boolean active = true;

    public EngineObjectFix engineObjectFix;
    public ECamera ecamera;
    public Light light;



    void start(EngineObject e)
    {
        engineObject = e;
        transform = e.getTransform();
    }

    public void start(EngineObjectFix e)
    {
        engineObjectFix = e;
        transform = e.transform;
    }



    void update(ECamera cam)
    {

    }


    public String getName()
    {
        return this.getClass().getSimpleName();
    }



    boolean addToPipeLine()
    {
        return false;
    }

    public void setActive(boolean enable)
    {
        active = enable;
    }

    public boolean isActive()
    {
        return active;
    }

    public boolean isObjectActive()
    {
        return engineObjectFix.isActive();
    }


}
