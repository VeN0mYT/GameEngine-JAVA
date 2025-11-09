package org.example.ECS.systems;

import org.example.ECS.manager.ComponentManager;
import org.example.ECS.manager.EngineObjectManager;

public abstract class SystemBase {
    protected EngineObjectManager entities;
    protected ComponentManager components;

    public SystemBase(EngineObjectManager e, ComponentManager c) {
        this.entities = e;
        this.components = c;
    }

    public SystemBase()
    {

    }

    public void setEntities(EngineObjectManager e)
    {
        this.entities = e;
    }

    public void setComponents(ComponentManager c)
    {
        this.components = c;
    }

    public void start() {}
    public void update() {}
    public void render() {}
}
