package mincraft.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryo.Kryo;
import mincraft.block.ChunkManager;

import java.io.IOException;

public class GameClient {
    private Client client;
    private ClientListener listener;

    public GameClient(String ip) throws IOException {
        client = new Client();
        Kryo kryo = client.getKryo();

        // Register packets
        kryo.register(PacketPlayerPosition.class);
        kryo.register(PacketAddBlock.class);
        kryo.register(PacketRemoveBlock.class);

        listener = new ClientListener();
        client.addListener(listener);

        client.start();
        client.connect(5000, ip, 54555, 54777);
        System.out.println("Connected to server: " + ip);
    }

    public void send(Object packet) {
        client.sendTCP(packet);
    }

    public void disconnect() {
        client.stop();
    }

    // Let render loop poll updates
    public void pollUpdates(ChunkManager chunkManager) {
        listener.applyPending(chunkManager);
    }
}
