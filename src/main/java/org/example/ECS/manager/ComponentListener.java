package org.example.ECS.manager;

import org.example.ECS.EngineObjectFix;
import org.example.component.Component;

public interface ComponentListener {
    void onComponentAdded(EngineObjectFix e,Component c);
    void onComponentRemoved(EngineObjectFix e,Component c);
}
