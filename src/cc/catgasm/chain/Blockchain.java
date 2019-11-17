package cc.catgasm.chain;

import cc.catgasm.util.Hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Blockchain {
    private List<Block> blocks;

    public Blockchain(Block genesis) {
        blocks = new ArrayList<>();
        blocks.add(genesis);
    }

    public void addBlock(String data) {
        blocks.add(new Block(blocks.get(blocks.size()-1), data));
    }

    public void addBlock(byte[] data) {
        blocks.add(new Block(blocks.get(blocks.size()-1), data));
    }

    public void verifyBlockchain() {
        byte[] lastHash = new byte[64]; //Empty hash for genesis block
        for (Block block : blocks) {
            if(!Arrays.equals(lastHash, block.getPrevHash())) { //TODO better "if"
                System.out.println("Block #" + block.getNr() + " is not valid. Block hash: " + Hash.hashToString(block.getHash()));
                break;
            }
            if(!Arrays.equals(Block.mine(block).getFirst(), block.getHash())) {
                System.out.println("Block #" + block.getNr() + " is not valid. Block hash: " + Hash.hashToString(block.getHash()));
                break;
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
            sb.append("\tData:\t\t\t").append(new String(block.getData())).append("\n\n");
        }
        System.out.println(sb);
    }

    public void testRewrite() {
        blocks.get(1).setData("nope");
        verifyBlockchain();
        printChain();
    }
}
