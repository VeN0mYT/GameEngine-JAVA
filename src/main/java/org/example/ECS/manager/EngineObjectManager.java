package org.example.ECS.manager;

import org.example.ECS.EngineObjectFix;
import org.example.ECS.SceneFix;
import org.example.scene.Scene;

import java.util.HashSet;
import java.util.Set;

public final class EngineObjectManager {
    private int nextId = 0;
    private  final Set<EngineObjectFix> EngineObjects = new HashSet<>();

    public EngineObjectFix createEngineObject(SceneFix scene)
    {
        EngineObjectFix engineObjectFix = new EngineObjectFix(nextId++,scene);
        EngineObjects.add(engineObjectFix);
        return engineObjectFix;
    }

    public EngineObjectFix createEngineObject(SceneFix scene,String name)
    {
        EngineObjectFix engineObjectFix = createEngineObject(scene);
        engineObjectFix.setName(name);
        return engineObjectFix;
    }

    public void destroyEngineObject(EngineObjectFix id)
    {
        EngineObjects.remove(id);
    }

    public Set<EngineObjectFix> getEngineObjects()
    {
        return EngineObjects;
    }
}
