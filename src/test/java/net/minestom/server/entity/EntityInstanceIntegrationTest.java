package net.minestom.server.entity;

import net.minestom.testing.Env;
import net.minestom.server.coordinate.Pos;
import net.minestom.testing.extension.MicrotusExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MicrotusExtension.class)
class EntityInstanceIntegrationTest {

    @Test
    void entityJoin(Env env) {
        var instance = env.createFlatInstance();
        var entity = new Entity(EntityTypes.ZOMBIE);
        entity.setInstance(instance, new Pos(0, 42, 0)).join();
        assertEquals(instance, entity.getInstance());
        assertEquals(new Pos(0, 42, 0), entity.getPosition());
    }

    @Test
    void playerJoin(Env env) {
        var instance = env.createFlatInstance();
        var player = env.createPlayer(instance, new Pos(0, 42, 0));
        assertEquals(instance, player.getInstance());
        assertEquals(new Pos(0, 42, 0), player.getPosition());
    }

    @Test
    void playerSwitch(Env env) {
        var instance = env.createFlatInstance();
        var instance2 = env.createFlatInstance();
        var connection = env.createConnection();
        var player = connection.connect(instance, new Pos(0, 42, 0)).join();
        assertEquals(instance, player.getInstance());
        // #join may cause the thread to hang as scheduled for the next tick when initially in a pool
        Assertions.assertTimeout(Duration.ofSeconds(2), () -> player.setInstance(instance2).join());
        assertEquals(instance2, player.getInstance());
    }
}
