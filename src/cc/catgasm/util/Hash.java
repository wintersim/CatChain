package cc.catgasm.util;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    private byte[] hash;

    public Hash(byte[] input) {
        hash = hash(input);
    }

    public Hash(byte[] ... inputs) {
        byte[] bytes = new byte[0];
        for (byte[] input : inputs) {
            bytes = concatenate(bytes, input);
        }
        hash = hash(bytes);
    }

    public <T> byte[] concatenate(byte[] a, byte[] b) { //TODO
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        byte[] c = (byte[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    public Hash(String input) {
        hash = hash(input.getBytes(StandardCharsets.UTF_8));
    }

    private byte[] hash(byte[] input) {
        byte[] hashed = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            hashed = md.digest(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashed;
    }

    public byte[] getBytes() {
        return hash;
    }

    @Override
    public String toString() {
        return hashToString(hash);
    }

    public static String hashToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
