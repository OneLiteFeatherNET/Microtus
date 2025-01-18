package net.minestom.server.listener.preplay;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.MinecraftServer;
import net.minestom.server.ServerFlag;
import net.minestom.server.event.auth.Auth;
import net.minestom.server.event.auth.MojangAuth;
import net.minestom.server.network.packet.client.login.ClientEncryptionResponsePacket;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.network.player.PlayerSocketConnection;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class ModernEncryptionResponseListener {

    private static final ComponentLogger LOGGER = ComponentLogger.logger();
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Component MOJANG_DOWN = MiniMessage.miniMessage().deserialize(System.getProperty("mojang_down", "<red>Mojang authentication servers are down, please try again later."));
    private static final Gson GSON = new Gson();

    public static void modernLoginEncryptionResponseListener(@NotNull ClientEncryptionResponsePacket packet, @NotNull PlayerConnection connection) {

        // Encryption is only support for socket connection
        if (!(connection instanceof PlayerSocketConnection socketConnection)) return;

        final String loginUsername = socketConnection.getLoginUsername();
        if (loginUsername == null || loginUsername.isEmpty()) {
            LOGGER.error("Received an encryption response without a login username");
            socketConnection.disconnect();
            return;
        }
        Auth auth = connection.getAuth();

        final boolean hasPublicKey = connection.playerPublicKey() != null;
        if (auth instanceof MojangAuth mojangAuth) {
            final boolean verificationFailed = hasPublicKey || !Arrays.equals(socketConnection.getNonce(), mojangAuth.getNonce(packet.encryptedVerifyToken()));
            if (verificationFailed) {
                LOGGER.error("Encryption failed for {}", loginUsername);
                socketConnection.disconnect();
                return;
            }
            final byte[] digestedData = mojangAuth.digestData("", packet.sharedSecret());
            if (digestedData == null) {
                // Incorrect key, probably because of the client
                LOGGER.error("Connection {} failed initializing encryption.", socketConnection.getRemoteAddress());
                connection.disconnect();
                return;
            }
            final String serverId = new BigInteger(digestedData).toString(16);
            final String username = URLEncoder.encode(loginUsername, StandardCharsets.UTF_8);

            final String url = String.format(ServerFlag.MOJANG_AUTH_URL, username, serverId);
            final HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
            CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).whenComplete((response, throwable) -> {
                final boolean unsuccessfully = throwable != null || response.statusCode() != 200 || response.body() == null || response.body().isEmpty();
                if (unsuccessfully) {
                    if (throwable != null) {
                        MinecraftServer.getExceptionManager().handleException(throwable);
                    }
                    if (socketConnection.getPlayer() != null) {
                        socketConnection.getPlayer().kick(MOJANG_DOWN);
                    } else {
                        LOGGER.error("Failed to authenticate player {}", loginUsername, throwable);
                        socketConnection.disconnect();
                    }
                    return;
                }
                try {
                    socketConnection.setEncryptionKey(packet.sharedSecret());
                    final JsonObject gameProfile = GSON.fromJson(response.body(), JsonObject.class);
                    final GameProfile profile = new GameProfile(gameProfile);
                    socketConnection.UNSAFE_setProfile(profile);
                    MinecraftServer.getConnectionManager().createPlayer(connection, profile.uuid(), profile.name());
                } catch (Exception e) {
                    MinecraftServer.getExceptionManager().handleException(e);
                    LOGGER.error("Failed to authenticate player {}", loginUsername);
                    socketConnection.disconnect();
                }
            });
        }
    }
}
