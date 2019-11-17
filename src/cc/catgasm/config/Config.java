package cc.catgasm.config;

public class Config {
    private static final String CONFIG_PATH = "config.txt";
    private static final Config CONFIG = new Config(CONFIG_PATH);

    private Config(String path) {

    }

    public static Config getInstace() {
        return CONFIG;
    }


}
