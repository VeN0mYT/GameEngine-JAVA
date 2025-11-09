package org.example.ECS.systems;

import org.example.ECS.EngineObjectFix;
import org.example.ECS.manager.ComponentListener;
import org.example.ECS.manager.ComponentManager;
import org.example.ECS.manager.EngineObjectManager;
import org.example.component.Component;
import org.example.component.behaviour.Behaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class BehaviourSystem extends SystemBase implements ComponentListener {

    private final List<Behaviour> behaviours = new ArrayList<>();


    public BehaviourSystem() {
        super();

    }

    @Override
    public void start() {
//        behaviours.addAll(components.getAllBehaviours());
        for(Behaviour b : behaviours) {
            if(b.isActive()&&b.isObjectActive())
                b.start();
        }
    }

    @Override
    public void update() {
        for(Behaviour b : behaviours)
            if(b.isActive()&&b.isObjectActive())
                b.update();
    }

    @Override
    public void onComponentAdded(EngineObjectFix e, Component c) {
        if (c instanceof Behaviour b) behaviours.add(b);
    }

    @Override
    public void onComponentRemoved(EngineObjectFix e,Component c) {
        if (c instanceof Behaviour b) behaviours.remove(b);
    }


}
