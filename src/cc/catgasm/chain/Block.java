package cc.catgasm.chain;

import cc.catgasm.core.exception.ErrorCode;
import cc.catgasm.core.exception.CatChainException;
import cc.catgasm.util.Hash;
import cc.catgasm.util.MathUtil;
import cc.catgasm.util.Pair;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Block {
    private byte[] hash;
    private byte[] prevHash;
    private byte[] data;
    private long timestamp;
    private long nr;
    private long nonce;

    public static final int MAX_BLOCKSIZE = 1024 * 1024 * 4; //4 MiB

    private static long current_block = 0;
    private static long difficulty = 1; //Number represents leading zero-bytes in hash

    public Block(Block prv, byte[] data) throws CatChainException {
        if (data.length == 0) {
            throw new CatChainException(ErrorCode.BLOCK_DATA_EMPTY);
        } else if (data.length > MAX_BLOCKSIZE) {
            throw new CatChainException(ErrorCode.BLOCK_DATA_TOO_LONG);
        }

        this.prevHash = prv.getHash();
        this.data = data;
        this.timestamp = new Date().getTime();
        this.nr = current_block++;
        Pair<byte[], Long> pair = mine(this);
        hash = pair.getFirst();
        nonce = pair.getSecond();
    }

    public Block(Block prv, String data) throws CatChainException {
        this(prv, data.getBytes(StandardCharsets.UTF_8));
    }

    public Block(byte[] data) throws CatChainException { //Only used for genesis block
        if (data.length == 0) {
            throw new CatChainException(ErrorCode.BLOCK_DATA_EMPTY);
        } else if (data.length > MAX_BLOCKSIZE) {
            throw new CatChainException(ErrorCode.BLOCK_DATA_TOO_LONG);
        }

        this.prevHash = new byte[64];
        this.data = data;
        this.timestamp = new Date().getTime();
        this.nr = current_block++;
        Pair<byte[], Long> pair = mine(this);
        hash = pair.getFirst();
        nonce = pair.getSecond();
    }

    //TODO: Probably very inefficient
    public static Pair<byte[], Long> mine(Block block) {
        long nonce = -1;
        int leadingZerosFound;
        byte[] hash;

        do {
            ++nonce;
            leadingZerosFound = 0;

            hash = new Hash(block.getData(), block.getPrevHash(), MathUtil.longToByteArray(nonce)).getBytes();

            //Check leading zeros
            for (int i = 0; i < difficulty; i++) {
                if (hash[i] == 0) {
                    ++leadingZerosFound;
                }
            }
        } while (leadingZerosFound != difficulty);
        return new Pair<>(hash, nonce);
    }

    public static byte[] hash(Block block) {
        return new Hash(block.getData(), block.getPrevHash(), MathUtil.longToByteArray(block.getNonce())).getBytes();
    }

    public byte[] getHash() {
        return hash;
    }

    public byte[] getPrevHash() {
        return prevHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getNonce() {
        return nonce;
    }

    public long getNr() {
        return nr;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Block{" +
                "hash=" + Hash.hashToString(hash) +
                ", prevHash=" + Hash.hashToString(prevHash) +
                ", data=" + new String(data, StandardCharsets.UTF_8) +
                ", timestamp=" + timestamp +
                ", nr=" + nr +
                ", nonce=" + nonce +
                '}';
    }

    public void setData(String nope) { //Only for testing
        data = nope.getBytes(StandardCharsets.UTF_8);
    }
}
