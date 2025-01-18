package net.minestom.server.event.auth;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

record MojangAuthImpl(KeyPair keyPair) implements MojangAuth {

    private static final ComponentLogger LOGGER = ComponentLogger.logger();

    @Override
    public byte @Nullable [] digestData(@NotNull String data, byte @NotNull [] sharedSecret) {
        return digestData("", keyPair.getPublic(), decryptByteToSecretKey(sharedSecret));
    }

    private SecretKeySpec decryptByteToSecretKey(byte[] bytes) {
        return new SecretKeySpec(getNonce(bytes), "AES");
    }

    @Override
    public byte[] getNonce(byte[] bytes) {
        return cipherData(2, keyPair.getPrivate(), bytes);
    }

    @Override
    public Cipher getCipher(int mode, byte[] sharedSecret) {
        Key key = decryptByteToSecretKey(sharedSecret);
        try {
            Cipher cipher3 = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher3.init(mode, key, new IvParameterSpec(key.getEncoded()));
            return cipher3;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] cipherData(int mode, Key key, byte[] data) {
        try {
            return setupCipher(mode, key.getAlgorithm(), key).doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException var4) {
            MinecraftServer.getExceptionManager().handleException(var4);
        }
        LOGGER.error("Cipher data failed!");
        return null;
    }

    private Cipher setupCipher(int mode, String transformation, Key key) {
        try {
            Cipher cipher4 = Cipher.getInstance(transformation);
            cipher4.init(mode, key);
            return cipher4;
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException var4) {
            MinecraftServer.getExceptionManager().handleException(var4);
        }
        LOGGER.error("Cipher creation failed!");
        return null;
    }

    private byte @Nullable [] digestData(String algorithm, byte[]... data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            for (byte[] bytes : data) {
                digest.update(bytes);
            }
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            MinecraftServer.getExceptionManager().handleException(e);
            return null;
        }
    }

    private byte[] digestData(String data, PublicKey publicKey, SecretKey secretKey) {
        try {
            return digestData("SHA-1", data.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
        } catch (UnsupportedEncodingException e) {
            MinecraftServer.getExceptionManager().handleException(e);
            return null;
        }
    }

    static final class MojangAuthBuilderImpl implements MojangAuth.MojangAuthBuilder {

        private static final ComponentLogger LOGGER = ComponentLogger.logger();

        private KeyPair keyPair;

        @Override
        public MojangAuth.MojangAuthBuilder keyPair(@NotNull KeyPair keyPair) {
            this.keyPair = keyPair;
            return this;
        }

        @Override
        public MojangAuthBuilder generateKeyPair() {
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(1024);
                this.keyPair = keyGen.generateKeyPair();
            } catch (NoSuchAlgorithmException e) {
                MinecraftServer.getExceptionManager().handleException(e);
                LOGGER.error("Key pair generation failed!");
            }
            return this;
        }

        @Override
        public MojangAuth build() {
            if (keyPair == null) {
                throw new IllegalStateException("KeyPair is not set");
            }
            return new MojangAuthImpl(keyPair);
        }
    }

}
