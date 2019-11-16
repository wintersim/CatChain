package cc.catgasm.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    private byte[] hash;

    public Hash(byte[] input) {
        hash = hash(input);
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
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
