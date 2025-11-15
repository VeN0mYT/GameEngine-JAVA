package org.example.component;

import org.example.ECS.EngineObjectFix;
import org.example.camera.ECamera;
import org.example.light.Light;
import org.example.render.ElementBuffer;
import org.example.render.VertexArray;
import org.example.render.VertexBuffer;
import org.example.render.VertexLayout;
import org.example.transform.Transform;

import static org.lwjgl.opengl.GL11.*;

public final class MeshRender extends Component{
    private Mesh mesh;
    private Material material;

    private VertexArray vao;
    private VertexBuffer vbo;
    private ElementBuffer ebo;


    public MeshRender()
    {

    }

    public MeshRender(EngineObject e)
    {
        this(e.getComponent(Mesh.class,"Mesh"), e.getComponent(Material.class,"Material"));
        engineObject = e;
    }

    public MeshRender(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;

        vao = new VertexArray();
        vao.bind();

        int[] ind = mesh.getIndices();
        vbo = new VertexBuffer(mesh.getData());

        VertexLayout layout = new VertexLayout();
        layout.pushFloat(3); // position
        layout.pushFloat(2); // texcoords
        layout.pushFloat(3); // color
        layout.pushFloat(3); // normals (always have them)

        vao.addBuffer(vbo, layout, 0);
        ebo = new ElementBuffer(ind);

        vao.unbind();
    }

    public void Reset() {
        mesh = engineObjectFix.GetComponent(Mesh.class);
        mesh.Reset();

        if(vao != null)
        vao.destroy();
        if(vbo != null)
        vbo.destroy();
        if(ebo != null)
        ebo.destroy();

        vao = new VertexArray();
        vao.bind();

        int[] ind = mesh.getIndices();
        vbo = new VertexBuffer(mesh.getData());

        VertexLayout layout = new VertexLayout();
        layout.pushFloat(3); // position
        layout.pushFloat(2); // texcoords
        layout.pushFloat(3); // color
        layout.pushFloat(3); // normals (always have them)

        vao.addBuffer(vbo, layout, 0);
        ebo = new ElementBuffer(ind);

        vao.unbind();
    }

    void Render()
    {

        material = engineObjectFix.GetComponent(Material.class);
        material.bind();


        vao.bind();
        glDrawElements(GL_TRIANGLES, mesh.getIndicesList().size(), GL_UNSIGNED_INT, 0);
        vao.unbind();
    }

    public void SetMaterial(Material material)
    {
        this.material = material;
    }

    public void SetMesh(Mesh mesh)
    {

        this.mesh = mesh;
        engineObjectFix.AddComponent(mesh);
        Reset();
    }

    public Material getMaterial()
    {
        return material;
    }

    public Mesh getMesh()
    {
        return mesh;
    }

    public void setEngineObject(EngineObject engineObject)
    {
        this.engineObject = engineObject;
        material = engineObject.getComponent(Material.class,"Material");
        SetMesh(engineObject.getComponent(Mesh.class,"Mesh"));
    }

    public void setEngineObject(EngineObjectFix engineObjectFix)
    {
        this.engineObjectFix = engineObjectFix;
        transform = engineObjectFix.transform;
        material = engineObjectFix.GetComponent(Material.class);
        SetMesh(engineObjectFix.GetComponent(Mesh.class));
    }


    void start(EngineObject engineObject)
    {
        setEngineObject(engineObject);
    }


    public void start(EngineObjectFix engineObjectFix)
    {
        setEngineObject(engineObjectFix);
    }

    void update(ECamera camera)
    {
        Render();
    }

    public void update()
    {
        Render();
    }

    boolean addToPipeLine()
    {
        return true;
    }

    public String getName() {
        return "MeshRender";
    }


}
