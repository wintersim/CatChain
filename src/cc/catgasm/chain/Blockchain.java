package cc.catgasm.chain;

import cc.catgasm.core.Image;
import cc.catgasm.core.exception.CatChainException;
import cc.catgasm.util.Hash;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Blockchain {
    private List<Block> blocks;

    public Blockchain(Block genesis) {
        blocks = new ArrayList<>();
        blocks.add(genesis);
    }

    public void addBlock(String data) throws CatChainException {
        blocks.add(new Block(blocks.get(blocks.size()-1), data));
    }

    public void addBlock(byte[] data) throws CatChainException {
        blocks.add(new Block(blocks.get(blocks.size()-1), data));
    }

    public void verifyBlockchain() {
        byte[] lastHash = new byte[64]; //Empty hash for genesis block
        for (Block block : blocks) {
            if(!Arrays.equals(lastHash, block.getPrevHash())) { //TODO better "if"
                System.out.println("Block #" + block.getNr() + " is not valid. Block hash: " + Hash.hashToString(block.getHash()));
                return;
            }
            if(!Arrays.equals(Block.hash(block), block.getHash())) {
                System.out.println("Block #" + block.getNr() + " is not valid. Block hash: " + Hash.hashToString(block.getHash()));
                return;
            }
            lastHash = block.getHash();
        }

        System.out.println("Blockchain verified");
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void printChain() {
        StringBuilder sb = new StringBuilder();
        for (Block block : blocks) {
            sb.append("\n#").append(block.getNr()).append("\n");
            sb.append("\tHash:\t\t\t").append(Hash.hashToString(block.getHash())).append("\n");
            sb.append("\tPrevious Hash:\t").append(Hash.hashToString(block.getPrevHash())).append("\n");
            sb.append("\tNonce:\t\t\t").append(block.getNonce()).append("\n");
            sb.append("\tTimestamp:\t\t").append(block.getTimestamp()).append("\n");
            try {
                sb.append("\tData:\t\t\t").append(new Image(block.getData()).getFilename()).append("\n\n");
            } catch (CatChainException e) {
                e.printStackTrace();

            }
        }
        System.out.println(sb);
    }

    public void testRewrite() {
        blocks.get(1).setData("nope");
        verifyBlockchain();
        printChain();
    }
}
