package org.example.ECS.manager;

import org.example.ECS.EngineObjectFix;
import org.example.component.Component;
import org.example.component.EngineObject;
import org.example.component.behaviour.Behaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ComponentManager {
    private final Map<Class<?>, Map<EngineObjectFix, Component>> store = new HashMap<>();
    private final List<ComponentListener> listeners = new ArrayList<>();

    public <T extends Component> void add(EngineObjectFix EngineObjectID, T component)
    {
        store.computeIfAbsent(component.getClass(), k->new HashMap<>()).put(EngineObjectID, component);
        component.start(EngineObjectID);
        for (ComponentListener listener : listeners)
            listener.onComponentAdded(EngineObjectID,component);
    }

    public <T extends Component> T get(EngineObjectFix EngineObjectID, Class<T> type) {
        Map<EngineObjectFix, Component> table = store.get(type);
        return table == null ? null : type.cast(table.get(EngineObjectID));
    }

    public <T extends Component> Map<EngineObjectFix, T> getAllOfType(Class<T> type) {
        Map<EngineObjectFix, Component> table = store.get(type);
        if (table == null) return Map.of();
        Map<EngineObjectFix, T> result = new HashMap<>();
        table.forEach((id, comp) -> result.put(id, type.cast(comp)));
        return result;                                                                  //think about return the refrance for more performance later
    }

//    @SuppressWarnings("unchecked")
//    public <T extends Component> Map<EngineObjectFix, T> getAllOfType(Class<T> type) {
//        return (Map<EngineObjectFix, T>) (Map<?, ?>) store.getOrDefault(type, Map.of());
//    }

    public List<Behaviour> getAllBehaviours()
    {
        List<Behaviour> behaviours = new ArrayList<>();
        for(Class<?> obj: store.keySet())
        {
            if(obj.getSuperclass() == Behaviour.class)
            {
                Map<EngineObjectFix,Component> table = store.get(obj);
                for(Object obj2: table.values())
                behaviours.add((Behaviour)obj2);
            }
        }
        return behaviours;
    }



    public void remove(EngineObjectFix EngineObjectID, Class<?> type) {
        Map<EngineObjectFix, Component> table = store.get(type);
        if (table == null) return;

        Component removed = table.remove(EngineObjectID);
        if (removed != null) {
            for (ComponentListener listener : listeners)
                listener.onComponentRemoved(EngineObjectID,removed);
        }
    }

    public void remove(EngineObjectFix EngineObjectID) {
        for (Map<EngineObjectFix, Component> table : store.values()) {
            Component removed = table.remove(EngineObjectID);
            if (removed != null) {
                for (ComponentListener listener : listeners)
                    listener.onComponentRemoved(EngineObjectID,removed);
            }
        }
    }

    public boolean has(EngineObjectFix EngineObjectID, Class<?> type) {
        return store.containsKey(type) && store.get(type).containsKey(EngineObjectID);
    }

    public Map<Class<?>, Map<EngineObjectFix, Component>> getStore() {
        return store;
    }

    public void addListener(ComponentListener listener) {
        listeners.add(listener);
    }
    public void removeListener(ComponentListener listener) {
        listeners.remove(listener);
    }
}
