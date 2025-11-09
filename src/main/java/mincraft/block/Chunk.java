package mincraft.block;

import org.example.render.*;

import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class Chunk {
    private final int chunkX, chunkY, chunkZ;
    public static final int CHUNK_SIZE = 16;
    public static final int CHUNK_HEIGHT = 16;



    private final BlockType[][][] blockTypes = new BlockType[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
    private final BlockData blockData = new BlockData();

    private final List<Float> vertices = new ArrayList<>();
    private final List<Integer> indices = new ArrayList<>();

    private  VertexArray vao;
    private  VertexBuffer vbo;
    private  ElementBuffer ebo;

    private boolean isGenerated = false;
    private boolean isUploaded = false;

    private final Random random = new Random();

    public Chunk(int chunkX, int chunkY, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.chunkY = chunkY;

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    blockTypes[x][y][z] = BlockType.AIR;
                }
            }
        }
    }

    public boolean inBounds(int x, int y, int z) {
        return x >= 0 && x < CHUNK_SIZE &&
                y >= 0 && y < CHUNK_HEIGHT &&
                z >= 0 && z < CHUNK_SIZE;
    }

    private boolean isAir(int x, int y, int z) {
        return !inBounds(x, y, z) || blockTypes[x][y][z] == BlockType.AIR;
    }

    private void generateTree(int maxHeight, int x, int z) {
        int treeHeight = 10;
        int leafRadius = 2;

        int trunkY = maxHeight;
        for (int i = 0; i < treeHeight; i++) {
            int y = trunkY + i;
            if (y < CHUNK_HEIGHT)
                blockTypes[x][y][z] = BlockType.WOOD;
        }

        int baseY = trunkY + treeHeight - 1;

        for (int dx = -leafRadius; dx <= leafRadius; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -leafRadius; dz <= leafRadius; dz++) {
                    int lx = x + dx;
                    int ly = baseY + dy;
                    int lz = z + dz;

                    if (Math.abs(dx) + Math.abs(dy) + Math.abs(dz) <= 3) {
                        if (inBounds(lx, ly, lz) && blockTypes[lx][ly][lz] == BlockType.AIR) {
                            blockTypes[lx][ly][lz] = BlockType.LEAVES;
                        }
                    }
                }
            }
        }


    }

//    public void initChunk() {
//        for (int x = 0; x < CHUNK_SIZE; x++) {
//            for (int z = 0; z < CHUNK_SIZE; z++) {
//
//                int worldX = chunkX * CHUNK_SIZE + x;
//                int worldZ = chunkZ * CHUNK_SIZE + z;
//
//                // Simple height function: sine wave + random variation
//                float heightValue = 0.5f + 0.25f * (float)Math.sin(worldX * 0.2) + 0.25f * (float)Math.cos(worldZ * 0.2);
//                heightValue = Math.min(1.0f, Math.max(0.0f, heightValue)); // clamp to [0,1]
//
//                // Convert to integer height
//                int maxHeight = (int)(heightValue * (CHUNK_HEIGHT - 1));
//
//                for (int y = CHUNK_HEIGHT - 1; y >= 0; y--) {
//                    if (y == maxHeight)
//                        blockTypes[x][y][z] = BlockType.GRASS;
//                    else if (y <= maxHeight - 6)
//                        blockTypes[x][y][z] = BlockType.STONE;
//                    else if (y <= maxHeight)
//                        blockTypes[x][y][z] = BlockType.DIRT;
//                    else
//                        blockTypes[x][y][z] = BlockType.AIR;
//                }
//
//                // Randomly generate trees
//                if (random.nextInt(90) == 0) {
//                    generateTree(maxHeight, x, z);
//                }
//            }
//        }
//    }

    public void initChunk(WorldGenerator generator) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    int worldX = chunkX * CHUNK_SIZE + x;
                    int worldY = chunkY * CHUNK_HEIGHT + y;
                    int worldZ = chunkZ * CHUNK_SIZE + z;

                    blockTypes[x][y][z] = generator.sample(worldX, worldY, worldZ);
                }
            }
        }
    }



    public void generateChunk() {

            System.out.println("Generating chunk at: " + chunkX + ", " + chunkZ);
        vertices.clear();
        indices.clear();
        int indexOffset = 0;
        Map<BlockFace, List<Vector3f>> blockFaces = blockData.getBlockVertices();

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    BlockType type = getBlock(x, y, z);
                    if (type == BlockType.AIR) continue;

                    Vector3f blockPos = new Vector3f(
                            x + chunkX * CHUNK_SIZE,
                            y + chunkY * CHUNK_HEIGHT,
                            z + chunkZ * CHUNK_SIZE
                    );

                    for (BlockFace face : BlockFace.values()) {
                        Vector3i offset = switch (face) {
                            case TOP -> new Vector3i(0, 1, 0);
                            case BOTTOM -> new Vector3i(0, -1, 0);
                            case FRONT -> new Vector3i(0, 0, 1);
                            case BACK -> new Vector3i(0, 0, -1);
                            case LEFT -> new Vector3i(-1, 0, 0);
                            case RIGHT -> new Vector3i(1, 0, 0);
                        };

                        if (isAir(x + offset.x, y + offset.y, z + offset.z)) {
                            List<Vector3f> verticesFace = blockFaces.get(face);
                            List<Vector2f> texCoords = blockData.getBlockUVs().get(type).get(face);

                            for (int i = 0; i < verticesFace.size(); i++) {
                                Vector3f v = verticesFace.get(i);
                                Vector2f uv = texCoords.get(i);

                                vertices.add(v.x + blockPos.x);  // position
                                vertices.add(v.y + blockPos.y);
                                vertices.add(v.z + blockPos.z);

                                vertices.add(uv.x);              // texcoords
                                vertices.add(uv.y);

                                // add normal
                                Vector3f normal = faceNormal(face);
                                vertices.add(normal.x);
                                vertices.add(normal.y);
                                vertices.add(normal.z);
                            }

                            // CW indices
                            indices.add(indexOffset + 0);
                            indices.add(indexOffset + 1);
                            indices.add(indexOffset + 2);
                            indices.add(indexOffset + 2);
                            indices.add(indexOffset + 3);
                            indices.add(indexOffset + 0);

                            indexOffset += 4;
                        }
                    }
                }
            }
        }

        isGenerated = true;
    }

    private Vector3f faceNormal(BlockFace face) {
        return switch (face) {
            case TOP -> new Vector3f(0, 1, 0);
            case BOTTOM -> new Vector3f(0, -1, 0);
            case FRONT -> new Vector3f(0, 0, 1);
            case BACK -> new Vector3f(0, 0, -1);
            case LEFT -> new Vector3f(-1, 0, 0);
            case RIGHT -> new Vector3f(1, 0, 0);
        };
    }


    public void renderChunk() {
        vao.bind();
        glDrawElements(GL_TRIANGLES, indices.size(), GL_UNSIGNED_INT, 0);
        vao.unbind();
    }

    public void initVAO(){
        vao = new VertexArray();
        vbo = new VertexBuffer();
        ebo = new ElementBuffer();
    }

    public void uploadData() {
        System.out.println("Uploading chunk data");


        vao.bind();
        vbo.bind();
        vbo.AddData(toFloatArray(vertices));
        VertexLayout layout = new VertexLayout();
        layout.pushFloat(3); // position
        layout.pushFloat(2); // texcoords
        layout.pushFloat(3); // normals
        vao.addBuffer(vbo, layout, 0);
        ebo.bind();
        ebo.addData(toIntArray(indices));
        vao.unbind();
        isUploaded = true;
    }

    private float[] toFloatArray(List<Float> list) {
        float[] arr = new float[list.size()];
        for (int i = 0; i < list.size(); i++) arr[i] = list.get(i);
        return arr;
    }

    private int[] toIntArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) arr[i] = list.get(i);
        return arr;
    }

    public BlockType getBlock(int x, int y, int z) {
        if (!inBounds(x, y, z)) return BlockType.AIR;
        return blockTypes[x][y][z];
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        if (!inBounds(x, y, z)) return;
        blockTypes[x][y][z] = type;
    }



    public boolean isGenerated() { return isGenerated; }
    public boolean isUploaded() { return isUploaded; }


    public int getChunkX() { return chunkX; }
    public int getChunkY() { return chunkY; }
    public int getChunkZ() { return chunkZ; }

    public int getWorldX() { return chunkX * CHUNK_SIZE; }
    public int getWorldY() { return chunkY * CHUNK_HEIGHT; }
    public int getWorldZ() { return chunkZ * CHUNK_SIZE; }

    public  void SetGenerated(boolean b) { isGenerated = b; }
    public void SetUploaded(boolean b) { isUploaded = b; }
}
