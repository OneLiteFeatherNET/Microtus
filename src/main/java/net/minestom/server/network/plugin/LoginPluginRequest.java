package net.minestom.server.network.plugin;

import java.util.concurrent.CompletableFuture;

@Deprecated(forRemoval = true, since = "1.6.0")
public class LoginPluginRequest {
    private final String channel;
    private final byte[] requestPayload;
    private final CompletableFuture<LoginPluginResponse> responseFuture = new CompletableFuture<>();

    public LoginPluginRequest(String channel, byte[] requestPayload) {
        this.channel = channel;
        this.requestPayload = requestPayload;
    }

    public String getChannel() {
        return channel;
    }

    public byte[] getRequestPayload() {
        return requestPayload;
    }

    public CompletableFuture<LoginPluginResponse> getResponseFuture() {
        return responseFuture;
    }
}
