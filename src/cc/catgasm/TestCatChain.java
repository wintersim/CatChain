package cc.catgasm;

import cc.catgasm.chain.Block;
import cc.catgasm.chain.Blockchain;
import cc.catgasm.chain.Image;
import cc.catgasm.config.Config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TestCatChain {
    public static void main(String[] args) {
        Config config = Config.getInstace();
        Block genesis = new Block("Catchain!".getBytes(StandardCharsets.UTF_8));

        Blockchain bc = new Blockchain(genesis);

        try {
            bc.addBlock(new Image("images/1.png").toRawData());
            bc.addBlock(new Image("images/2.jpg").toRawData());
            bc.addBlock(new Image("images/3.png").toRawData());
        } catch (IOException e) {
            e.printStackTrace();
        }

        bc.verifyBlockchain();
        bc.printChain();

        bc.testRewrite();

    }
}
