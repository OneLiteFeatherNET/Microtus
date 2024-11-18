package net.minestom.server.instance;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.play.ChangeGameStatePacket;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MicrotusExtension.class)
class WeatherTest {

    @Test
    void testWeatherDefaults(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Weather weather = instance.getWeather();
        assertFalse(weather.isRaining());
        assertEquals(0, weather.rainLevel());
        assertEquals(0, weather.thunderLevel());

        instance.setWeather(new Weather(1, 0.5f), 1);
        instance.tick(0);

        env.destroyInstance(instance);
    }

    @Test
    void testWeatherSendOnJoin(@NotNull Env env) {
        Instance instance = env.createFlatInstance();

        // Weather sent on instance join
        var connection = env.createConnection();
        var tracker = connection.trackIncoming(ChangeGameStatePacket.class)
                ;
        Player player = connection.connect(instance).join();
        assertEquals(Pos.ZERO, player.getPosition());
        tracker.assertCount(4);
        List<ChangeGameStatePacket> packets = tracker.collect();
        var state = packets.getFirst();
        assertEquals(ChangeGameStatePacket.Reason.BEGIN_RAINING, state.reason());

        state = packets.get(1);
        assertEquals(ChangeGameStatePacket.Reason.RAIN_LEVEL_CHANGE, state.reason());
        assertEquals(1, state.value());

        state = packets.getLast();
        assertEquals(ChangeGameStatePacket.Reason.THUNDER_LEVEL_CHANGE, state.reason());
        assertEquals(0.5f, state.value());

        // Weather change while inside instance
        var tracker2 = connection.trackIncoming(ChangeGameStatePacket.class);
        instance.setWeather(new Weather(0, 0), 2);
        instance.tick(0);
        state = tracker2.collect().getFirst();
        assertEquals(ChangeGameStatePacket.Reason.RAIN_LEVEL_CHANGE, state.reason());
        assertEquals(0.5f, state.value());
    }
}
