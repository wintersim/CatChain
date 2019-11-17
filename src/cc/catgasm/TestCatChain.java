package cc.catgasm;

import cc.catgasm.chain.Block;
import cc.catgasm.chain.Blockchain;
import cc.catgasm.config.Config;

import java.nio.charset.StandardCharsets;

public class TestCatChain {
    public static void main(String[] args) {
        Config config = Config.getInstace();
        Block genesis = new Block("Catchain!".getBytes(StandardCharsets.UTF_8));

        Blockchain bc = new Blockchain(genesis);
        bc.addBlock("Test");
        bc.addBlock("Does it work?");
        bc.addBlock("I hope it does");
        bc.addBlock("Let's see");

        bc.verifyBlockchain();
        bc.printChain();

        bc.testRewrite();
    }
}
