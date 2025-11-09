package mincraft.block;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class BlockData {
    private final Map<BlockFace, List<Vector3f>> blockVertices = new EnumMap<>(BlockFace.class);
    private final Map<BlockType, Map<BlockFace, List<Vector2f>>> blockUVs = new EnumMap<>(BlockType.class);

    public BlockData() {
        initVertices();
        initUVs();
    }

    private void initVertices() {
        // Reordered for CW (clockwise) winding
        blockVertices.put(BlockFace.FRONT, Arrays.asList(
                new Vector3f(-0.5f, -0.5f,  0.5f),
                new Vector3f( 0.5f, -0.5f,  0.5f),
                new Vector3f( 0.5f,  0.5f,  0.5f),
                new Vector3f(-0.5f,  0.5f,  0.5f)
        ));

        blockVertices.put(BlockFace.BACK, Arrays.asList(
                new Vector3f( 0.5f, -0.5f, -0.5f),
                new Vector3f(-0.5f, -0.5f, -0.5f),
                new Vector3f(-0.5f,  0.5f, -0.5f),
                new Vector3f( 0.5f,  0.5f, -0.5f)
        ));

        blockVertices.put(BlockFace.LEFT, Arrays.asList(
                new Vector3f(-0.5f, -0.5f, -0.5f),
                new Vector3f(-0.5f, -0.5f,  0.5f),
                new Vector3f(-0.5f,  0.5f,  0.5f),
                new Vector3f(-0.5f,  0.5f, -0.5f)
        ));

        blockVertices.put(BlockFace.RIGHT, Arrays.asList(
                new Vector3f( 0.5f, -0.5f,  0.5f),
                new Vector3f( 0.5f, -0.5f, -0.5f),
                new Vector3f( 0.5f,  0.5f, -0.5f),
                new Vector3f( 0.5f,  0.5f,  0.5f)
        ));

        blockVertices.put(BlockFace.TOP, Arrays.asList(
                new Vector3f(-0.5f,  0.5f,  0.5f),
                new Vector3f( 0.5f,  0.5f,  0.5f),
                new Vector3f( 0.5f,  0.5f, -0.5f),
                new Vector3f(-0.5f,  0.5f, -0.5f)
        ));

        blockVertices.put(BlockFace.BOTTOM, Arrays.asList(
                new Vector3f(-0.5f, -0.5f, -0.5f),
                new Vector3f( 0.5f, -0.5f, -0.5f),
                new Vector3f( 0.5f, -0.5f,  0.5f),
                new Vector3f(-0.5f, -0.5f,  0.5f)
        ));
    }

    private void initUVs() {
        for (BlockType type : BlockType.values()) {
            Map<BlockFace, List<Vector2f>> faceUVs = new EnumMap<>(BlockFace.class);

            switch(type) {
                case GRASS -> {
                    faceUVs.put(BlockFace.FRONT, Arrays.asList(new Vector2f(0.2f, 0.1f), new Vector2f(0.1f, 0.1f),
                            new Vector2f(0.1f, 0f), new Vector2f(0.2f, 0f)));
                    faceUVs.put(BlockFace.BACK, faceUVs.get(BlockFace.FRONT));
                    faceUVs.put(BlockFace.LEFT, faceUVs.get(BlockFace.FRONT));
                    faceUVs.put(BlockFace.RIGHT, faceUVs.get(BlockFace.FRONT));
                    faceUVs.put(BlockFace.TOP, Arrays.asList(new Vector2f(0f, 0f), new Vector2f(0.1f, 0f),
                            new Vector2f(0.1f, 0.1f), new Vector2f(0f, 0.1f)));
                    faceUVs.put(BlockFace.BOTTOM, Arrays.asList(new Vector2f(0.2f, 0f), new Vector2f(0.3f, 0f),
                            new Vector2f(0.3f, 0.1f), new Vector2f(0.2f, 0.1f)));
                }
                case DIRT -> {
                    // Add similar UVs as in C++ (simplified for brevity)
                    for (BlockFace face : BlockFace.values()) {
                        faceUVs.put(face, Arrays.asList(
                                new Vector2f(0.2f, 0f), new Vector2f(0.3f, 0f),
                                new Vector2f(0.3f, 0.1f), new Vector2f(0.2f, 0.1f)
                        ));
                    }


                }

                case STONE -> {
                    // Add similar UVs as in C++ (simplified for brevity)
                    for (BlockFace face : BlockFace.values()) {
                        faceUVs.put(face, Arrays.asList(
                                new Vector2f(0.4f, 0f), new Vector2f(0.5f, 0f),
                                new Vector2f(0.5f, 0.1f), new Vector2f(0.4f, 0.1f)
                        ));
                    }
                }

                case WOOD -> {
                    // Add similar UVs as in C++ (simplified for brevity)
                    for (BlockFace face : BlockFace.values()) {
                        faceUVs.put(face, Arrays.asList(
                                new Vector2f(0.0f, 0.1f), new Vector2f(0.1f, 0.1f),
                                new Vector2f(0.1f, 0.2f), new Vector2f(0.0f, 0.2f)
                        ));
                    }
                }

                case LEAVES -> {
                    // Add similar UVs as in C++ (simplified for brevity)
                    for (BlockFace face : BlockFace.values()) {
                        faceUVs.put(face, Arrays.asList(
                                new Vector2f(0.2f, 0.2f), new Vector2f(0.1f, 0.2f),
                                new Vector2f(0.1f, 0.1f), new Vector2f(0.2f, 0.1f)
                        ));
                    }
                }
            }

            blockUVs.put(type, faceUVs);
        }
    }

    public Map<BlockFace, List<Vector3f>> getBlockVertices() {
        return blockVertices;
    }

    public Map<BlockType, Map<BlockFace, List<Vector2f>>> getBlockUVs() {
        return blockUVs;
    }
}
