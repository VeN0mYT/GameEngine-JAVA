package mincraft.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import mincraft.block.BlockType;
import mincraft.block.Chunk;
import mincraft.block.ChunkManager;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientListener extends Listener {
    private final ConcurrentLinkedQueue<Object> pending = new ConcurrentLinkedQueue<>();

    @Override
    public void received(Connection c, Object obj) {
        pending.add(obj); // queue updates for main thread
    }

    public void applyPending(ChunkManager chunkManager) {
        Object obj;
        while ((obj = pending.poll()) != null) {
            if (obj instanceof PacketPlayerPosition pos) {
                // TODO: track other players (entity system)
                System.out.println("Player " + pos.playerId + " moved to " + pos.x + "," + pos.y + "," + pos.z);
            }
            else if (obj instanceof PacketAddBlock add) {
                Chunk c = chunkManager.getChunkFromWorld(add.x, add.y, add.z);
                if (c != null) {
                    c.setBlock(add.x - c.getWorldX(), add.y - c.getWorldY(), add.z - c.getWorldZ(),
                            BlockType.values()[add.blockId]);
                    c.generateChunk();
                    c.uploadData();
                }
            }
            else if (obj instanceof PacketRemoveBlock rem) {
                Chunk c = chunkManager.getChunkFromWorld(rem.x, rem.y, rem.z);
                if (c != null) {
                    c.setBlock(rem.x - c.getWorldX(), rem.y - c.getWorldY(), rem.z - c.getWorldZ(), BlockType.AIR);
                    c.generateChunk();
                    c.uploadData();
                }
            }
        }
    }
}
