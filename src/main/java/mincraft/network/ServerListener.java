package mincraft.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerListener extends Listener {
    private Server server;

    public ServerListener(Server server) {
        this.server = server;
    }

    @Override
    public void received(Connection c, Object obj) {
        if (obj instanceof PacketPlayerPosition pos) {
            // Broadcast player movement to everyone else
            server.sendToAllExceptTCP(c.getID(), pos);
        }

        if (obj instanceof PacketAddBlock add) {
            // Broadcast block placement
            server.sendToAllExceptTCP(c.getID(), add);
        }

        if (obj instanceof PacketRemoveBlock rem) {
            // Broadcast block removal
            server.sendToAllExceptTCP(c.getID(), rem);
        }
    }
}
