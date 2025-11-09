package mincraft.network;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryo.Kryo;

import java.io.IOException;

public class GameServer {
    private Server server;

    public GameServer() throws IOException {
        server = new Server();
        Kryo kryo = server.getKryo();

        // Register packets
        kryo.register(PacketPlayerPosition.class);
        kryo.register(PacketAddBlock.class);
        kryo.register(PacketRemoveBlock.class);

        server.addListener(new ServerListener(server));

        server.bind(54555, 54777);
        server.start();

        System.out.println("Server started on port 54555");
    }

    public static void main(String[] args) throws IOException {
        new GameServer();
    }
}
