package net.minestom.server.entity.player;

import net.minestom.server.ServerFlag;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.client.play.ClientStatusPacket;
import net.minestom.server.network.packet.server.play.ChunkDataPacket;
import net.minestom.server.network.packet.server.play.UnloadChunkPacket;
import net.minestom.server.utils.chunk.ChunkUtils;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MicrotusExtension.class)
class PlayerRespawnChunkIntegrationTest {

    @Test
    void testChunkUnloadsOnRespawn(Env env) {
        var instance = env.createFlatInstance();
        var connection = env.createConnection();
        Player player = connection.connect(instance, new Pos(0, 40, 0)).join();
        player.teleport(new Pos(32, 40, 32)).join();

        var unloadChunkTracker = connection.trackIncoming(UnloadChunkPacket.class);
        player.setHealth(0);
        player.respawn();
        // Since client unloads the chunks, we shouldn't receive any unload packets
        unloadChunkTracker.assertCount(0);
    }

    @Test
    void testChunkReloadCount(Env env) {
        var instance = env.createFlatInstance();
        var connection = env.createConnection();
        Player player = connection.connect(instance, new Pos(0, 40, 0)).join();

        var loadChunkTracker = connection.trackIncoming(ChunkDataPacket.class);
        player.setHealth(0);
        player.respawn();
        // Player should have all their chunks reloaded
        int chunkLoads = ChunkUtils.getChunkCount(Math.min(ServerFlag.CHUNK_VIEW_DISTANCE, player.getSettings().getViewDistance()));
        loadChunkTracker.assertCount(chunkLoads);
    }

    @Test
    void testPlayerTryRespawn(Env env) {
        var instance = env.createFlatInstance();
        var connection = env.createConnection();
        Player player = connection.connect(instance, new Pos(0, 40, 0)).join();

        var loadChunkTracker = connection.trackIncoming(ChunkDataPacket.class);
        player.setHealth(0);
        player.addPacketToQueue(new ClientStatusPacket(ClientStatusPacket.Action.PERFORM_RESPAWN));
        player.interpretPacketQueue();
        List<ChunkDataPacket> dataPacketList = loadChunkTracker.collect();
        Set<ChunkDataPacket> duplicateCheck = new HashSet<>();
        int actualViewDistance = Math.min(ServerFlag.CHUNK_VIEW_DISTANCE, player.getSettings().getViewDistance());
        int chunkLoads = ChunkUtils.getChunkCount(actualViewDistance);
        loadChunkTracker.assertCount(chunkLoads);
        for (ChunkDataPacket packet : dataPacketList) {
            assertFalse(duplicateCheck.contains(packet));
            duplicateCheck.add(packet);
            assertTrue(Math.abs(packet.chunkX()) <= actualViewDistance && Math.abs(packet.chunkZ()) <= actualViewDistance);
        }
    }
}
