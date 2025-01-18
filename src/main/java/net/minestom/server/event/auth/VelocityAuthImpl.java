package net.minestom.server.event.auth;

import net.minestom.server.MinecraftServer;
import net.minestom.server.network.NetworkBuffer;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static net.minestom.server.network.NetworkBuffer.*;

record VelocityAuthImpl(String secret, Key key) implements VelocityAuth {

    @Override
    public boolean checkIntegrity(@NotNull NetworkBuffer buffer) {
        byte[] signature = buffer.read(RAW_BYTES);
        byte[] data = buffer.readBytes(buffer.readableBytes());
        try {
            Mac mac = Mac.getInstance(MAC_ALGORITHM);
            mac.init(key);
            final byte[] mySignature = mac.doFinal(data);
            if (!MessageDigest.isEqual(signature, mySignature)) {
                return false;
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            MinecraftServer.getExceptionManager().handleException(e);
        }
        return true;
    }

    static final class VelocityAuthBuilderImpl implements VelocityAuth.VelocityAuthBuilder {

        private String secret;

        @Override
        public VelocityAuthBuilder secret(String secret) {
            this.secret = secret;
            return this;
        }

        @Override
        public VelocityAuth build() {
            if (secret == null) {
                throw new IllegalStateException("The secret is not set");
            }
            return new VelocityAuthImpl(secret, new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), MAC_ALGORITHM));
        }
    }

}
