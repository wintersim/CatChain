package cc.catgasm;

import cc.catgasm.chain.Block;
import cc.catgasm.chain.Blockchain;
import cc.catgasm.config.Config;
import cc.catgasm.core.Image;
import cc.catgasm.core.exception.CatChainException;

import java.nio.charset.StandardCharsets;

public class TestCatChain {
    public static void main(String[] args) {
        Config config = Config.getInstace();
        Block genesis = null;
        Blockchain bc = null;

        try {
            genesis = new Block("Catchain!".getBytes(StandardCharsets.UTF_8));

            bc = new Blockchain(genesis);

            bc.addBlock(new Image("images/1.png").toRawData());
            bc.addBlock(new Image("images/2.jpg").toRawData());
            bc.addBlock(new Image("images/3.png").toRawData());
        } catch (CatChainException e) {
            e.printStackTrace();
            e.printErrorCode();
            return;

        }

        bc.verifyBlockchain();
        bc.printChain();

        bc.testRewrite();

    }
}
