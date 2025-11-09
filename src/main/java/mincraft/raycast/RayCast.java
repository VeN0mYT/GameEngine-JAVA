package mincraft.raycast;

import mincraft.block.BlockType;
import mincraft.block.Chunk;
import mincraft.block.ChunkManager;
import org.joml.Vector3f;

public class RayCast {
    private static final float MAX_DISTANCE = 6f; // max reach
    private static final float STEP = 0.1f;       // precision of stepping

    // 🔹 Raycast against a single chunk (local version)
    public static Vector3f getHitBlock(Vector3f origin, Vector3f direction, Chunk chunk) {
        Vector3f rayPos = new Vector3f(origin);

        for (float t = 0; t < MAX_DISTANCE; t += STEP) {
            rayPos.fma(STEP, direction); // rayPos += direction * STEP

            int bx = (int) Math.floor(rayPos.x);
            int by = (int) Math.floor(rayPos.y);
            int bz = (int) Math.floor(rayPos.z);

            if (!chunk.inBounds(bx, by, bz)) continue;

            if (chunk.getBlock(bx, by, bz) != BlockType.AIR) {
                return new Vector3f(bx, by, bz);
            }
        }
        return null;
    }

    // 🔹 Place block version (single chunk)
    public static Vector3f getPlacePosition(Vector3f origin, Vector3f direction, Chunk chunk) {
        Vector3f rayPos = new Vector3f(origin);
        Vector3f lastEmpty = null;

        for (float t = 0; t < MAX_DISTANCE; t += STEP) {
            rayPos.fma(STEP, direction);

            int bx = (int) Math.floor(rayPos.x);
            int by = (int) Math.floor(rayPos.y);
            int bz = (int) Math.floor(rayPos.z);

            if (!chunk.inBounds(bx, by, bz)) continue;

            if (chunk.getBlock(bx, by, bz) != BlockType.AIR) {
                return lastEmpty; // stop at solid, return last empty
            }

            lastEmpty = new Vector3f(bx, by, bz);
        }
        return null;
    }

    // 🔹 Raycast across ALL chunks using ChunkManager (3D version)
    public static Vector3f getHitBlock(Vector3f origin, Vector3f direction, ChunkManager chunkManager) {
        Vector3f rayPos = new Vector3f(origin);

        for (float t = 0; t < MAX_DISTANCE; t += STEP) {
            rayPos.fma(STEP, direction);

            int bx = (int) Math.floor(rayPos.x);
            int by = (int) Math.floor(rayPos.y);
            int bz = (int) Math.floor(rayPos.z);

            Chunk c = chunkManager.getChunkFromWorld(bx, by, bz);
            if (c == null) continue;

            // convert to local chunk coords (X,Y,Z)
            int localX = Math.floorMod(bx, Chunk.CHUNK_SIZE);
            int localY = Math.floorMod(by, Chunk.CHUNK_SIZE);
            int localZ = Math.floorMod(bz, Chunk.CHUNK_SIZE);

            if (!c.inBounds(localX, localY, localZ)) continue;

            if (c.getBlock(localX, localY, localZ) != BlockType.AIR) {
                return new Vector3f(bx, by, bz); // return world coords
            }
        }
        return null;
    }

    // 🔹 Place block across ALL chunks (3D version)
    public static Vector3f getPlacePosition(Vector3f origin, Vector3f direction, ChunkManager chunkManager) {
        Vector3f rayPos = new Vector3f(origin);
        Vector3f lastEmpty = null;

        for (float t = 0; t < MAX_DISTANCE; t += STEP) {
            rayPos.fma(STEP, direction);

            int bx = (int) Math.floor(rayPos.x);
            int by = (int) Math.floor(rayPos.y);
            int bz = (int) Math.floor(rayPos.z);

            Chunk c = chunkManager.getChunkFromWorld(bx, by, bz);
            if (c == null) continue;

            int localX = Math.floorMod(bx, Chunk.CHUNK_SIZE);
            int localY = Math.floorMod(by, Chunk.CHUNK_SIZE);
            int localZ = Math.floorMod(bz, Chunk.CHUNK_SIZE);

            if (!c.inBounds(localX, localY, localZ)) continue;

            if (c.getBlock(localX, localY, localZ) != BlockType.AIR) {
                return lastEmpty; // place just before the hit
            }

            lastEmpty = new Vector3f(bx, by, bz); // world coords
        }
        return null;
    }
}
