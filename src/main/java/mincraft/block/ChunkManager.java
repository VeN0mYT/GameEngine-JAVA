package mincraft.block;

import org.joml.Vector3f;

import java.util.Queue;
import java.util.concurrent.*;

public class ChunkManager {
    private final ConcurrentMap<String, Chunk> chunks = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(4); // background gen threads
    private final Queue<Chunk> readyChunks = new ConcurrentLinkedQueue<>();
    private final WorldGenerator generator = new WorldGenerator();

    private final int renderDistance = 4; // horizontal radius in chunks
    private final int verticalDistance = 2; // how many chunks up/down to load
    private final int verticalDistanceDown = 8; // how many chunks up/down to load

    private static final Chunk DUMMY_CHUNK = new Chunk(0, 0, 0);
    static {
        DUMMY_CHUNK.SetGenerated(false);
        DUMMY_CHUNK.SetUploaded(false);
    }

    private String key(int x, int y, int z) {
        return x + "," + y + "," + z;
    }

    public Chunk getChunk(int x, int y, int z) {
        return chunks.getOrDefault(key(x, y, z), DUMMY_CHUNK);
    }

    public void update(Vector3f playerPos) {
        int chunkX = (int) Math.floor(playerPos.x / Chunk.CHUNK_SIZE);
        int chunkY = (int) Math.floor(playerPos.y / Chunk.CHUNK_HEIGHT);
        int chunkZ = (int) Math.floor(playerPos.z / Chunk.CHUNK_SIZE);

        // Load chunks in a cube radius around player
        for (int dx = -renderDistance; dx <= renderDistance; dx++) {
            for (int dy = -verticalDistanceDown; dy <= verticalDistance; dy++) {
                for (int dz = -renderDistance; dz <= renderDistance; dz++) {
                    int cx = chunkX + dx;
                    int cy = chunkY + dy;
                    int cz = chunkZ + dz;
                    String k = key(cx, cy, cz);

                    if (!chunks.containsKey(k)) {
                        chunks.put(k, DUMMY_CHUNK);

                        executor.submit(() -> {
                            Chunk chunk = new Chunk(cx, cy, cz);
                            chunk.initChunk(generator);
                            chunk.generateChunk();
                            readyChunks.add(chunk);
                        });
                    }
                }
            }
        }

        // Unload far chunks
//        chunks.keySet().removeIf(k -> {
//            String[] parts = k.split(",");
//            int cx = Integer.parseInt(parts[0]);
//            int cy = Integer.parseInt(parts[1]);
//            int cz = Integer.parseInt(parts[2]);
//
//            return Math.abs(cx - chunkX) > renderDistance ||
//                    Math.abs(cy - chunkY) > verticalDistance ||
//                    Math.abs(cz - chunkZ) > renderDistance;
//        });
    }

    public void uploadReadyChunks() {
        Chunk c;
        while ((c = readyChunks.poll()) != null) {
            c.initVAO();
            c.uploadData();
            chunks.put(key(c.getChunkX(), c.getChunkY(), c.getChunkZ()), c);
        }
    }

    public void render() {
        for (Chunk chunk : chunks.values()) {
            if (chunk == null) continue;
            if (chunk.isGenerated() && chunk.isUploaded()) {
                chunk.renderChunk();
            }
        }
    }

    public Chunk getChunkFromWorld(int worldX, int worldY, int worldZ) {
        int chunkX = (int) Math.floor((float) worldX / Chunk.CHUNK_SIZE);
        int chunkY = (int) Math.floor((float) worldY / Chunk.CHUNK_HEIGHT);
        int chunkZ = (int) Math.floor((float) worldZ / Chunk.CHUNK_SIZE);
        return getChunk(chunkX, chunkY, chunkZ);
    }

    public void shutdown() {
        executor.shutdownNow(); // stops all running tasks
        try {
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                System.out.println("Executor did not terminate in time.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
