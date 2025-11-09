package org.example.ECS;

import org.example.camera.ECamera;
import org.example.component.Component;
import org.example.light.Light;
import org.example.transform.Transform;

public final class EngineObjectFix {
    private int id;
    private String name;
    private SceneFix scene;
    public Transform transform = new Transform();
    private boolean Active = true;


    public EngineObjectFix(int id,SceneFix scene,String name) {
        this.id = id;
        this.name = name;
        this.scene = scene;
    }

    public EngineObjectFix(int id,SceneFix scene)
    {
        this.id = id;
        this.name = "EngineObject";
        this.scene = scene;
    }

    public  int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    void setScene(SceneFix scene)
    {
        this.scene = scene;
    }

    public <T extends Component> T GetComponent(Class<T> type)
    {
        return scene.getComponentManager().get(this, type);
    }

    public <T extends Component> void AddComponent(T component)
    {
        scene.getComponentManager().add(this, component);
    }

    public <T extends Component> void RemoveComponent(Class<T> type)
    {
        scene.getComponentManager().remove(this, type);
    }

    public void setParent(EngineObjectFix parent)
    {
        transform.setParent(parent.transform);
    }

    public void setParent(Transform parent)
    {
        transform.setParent(parent);
    }

//    public void setActive(boolean active)
//    {
//        scene.setActiveEngineObject(this,active);
//    }

    public void setActive(boolean active)
    {
        Active = active;
    }

    public boolean isActive()
    {
        return Active;
    }

    public void Destroy()
    {
        scene.destroyEngineObject(this);
    }

    public EngineObjectFix findEngineObject(String name)
    {
        return scene.findEngineObject(name);
    }

    public ECamera getCamera()
    {
        return scene.getCamera();
    }

    public Light getLight()
    {
        return scene.getLight();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof EngineObjectFix) {
            EngineObjectFix other = (EngineObjectFix) obj;
            return id == other.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
