package net.minestom.server.particle;

import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.play.ParticlePacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParticleDataTest {

    @Test
    void testDustParticleDefault() {
        Particle particle = Particle.DUST;
        ParticlePacket packet = new ParticlePacket(particle, true, 0, 0, 0, 0, 0, 0, 0, 0);
        assertDoesNotThrow(() -> packet.write(new NetworkBuffer()));
    }

    @Test
    void testDustParticleInvalid() {
        var particle = Particle.DUST.withProperties(null, 1);
        ParticlePacket packet = new ParticlePacket(particle, true, 0, 0, 0, 0, 0, 0, 0, 0);
        assertThrows(NullPointerException.class, () -> packet.write(new NetworkBuffer()));
    }

    @Test
    void testParticleValid() {
        var particle = Particle.ENTITY_EFFECT;
        ParticlePacket packet = new ParticlePacket(particle, true, 0, 0, 0, 0, 0, 0, 0, 0);
        assertDoesNotThrow(() -> packet.write(new NetworkBuffer()));
    }

    @Test
    void testParticleData() {
        var particle = Particle.ENTITY_EFFECT;
        ParticlePacket packet = new ParticlePacket(particle, true, 0, 0, 0, 0, 0, 0, 0, 0);
        assertDoesNotThrow(() -> packet.write(new NetworkBuffer()));
    }

    @Test
    void invalidBlock() {
        var particle = Particle.BLOCK.withBlock(null);
        ParticlePacket packet = new ParticlePacket(particle, true, 0, 0, 0, 0, 0, 0, 0, 0);
        assertThrows(NullPointerException.class, () -> packet.write(new NetworkBuffer()));
    }
}
