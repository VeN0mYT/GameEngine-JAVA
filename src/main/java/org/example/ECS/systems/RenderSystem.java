package org.example.ECS.systems;

import org.example.ECS.EngineObjectFix;
import org.example.ECS.manager.ComponentListener;
import org.example.ECS.manager.ComponentManager;
import org.example.ECS.manager.EngineObjectManager;
import org.example.component.Component;
import org.example.component.MeshRender;
import org.example.component.behaviour.Behaviour;

import java.util.HashMap;
import java.util.Map;

public final class RenderSystem extends SystemBase implements ComponentListener {

    private Map<EngineObjectFix, MeshRender> componentsMap = new HashMap<>();
    public RenderSystem() {
        super();

    }

    @Override
    public void start()
    {
//        comp = this.components.getAllOfType(MeshRender.class);
    }

    @Override
    public void render() {
        for(MeshRender m:componentsMap.values()) {
            if (m.isActive()&&m.isObjectActive())
                m.update();
        }
    }

    @Override
    public void onComponentAdded(EngineObjectFix e,Component c) {
        if(c instanceof MeshRender m)
            componentsMap.put(e, m);
    }

    @Override
    public void onComponentRemoved(EngineObjectFix e,Component c) {
        if(c instanceof MeshRender m)
            componentsMap.remove(e);
    }
}
