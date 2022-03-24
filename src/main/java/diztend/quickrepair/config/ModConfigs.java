package diztend.quickrepair.config;

import com.mojang.datafixers.util.Pair;
import diztend.quickrepair.Quickrepair;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static int DO_UNIT_REPAIR;
    public static double UNIT_REPAIR_RATE;
    public static int DO_ITEM_COMBINE;
    public static int DO_ITEM_NAMING;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Quickrepair.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("do.unit.repair", 1), "int");
        configs.addKeyValuePair(new Pair<>("unit.repair.rate", 0.25), "double");
        configs.addKeyValuePair(new Pair<>("do.item.naming", 1), "int");
        configs.addKeyValuePair(new Pair<>("do.item.combine", 1), "int");
    }

    private static void assignConfigs() {
        DO_UNIT_REPAIR = CONFIG.getOrDefault("do.unit.repair", 1);
        UNIT_REPAIR_RATE = CONFIG.getOrDefault("unit.repair.rate", 0.25);
        DO_ITEM_NAMING = CONFIG.getOrDefault("do.item.naming", 1);
        DO_ITEM_COMBINE = CONFIG.getOrDefault("do.item.combine", 1);

        Quickrepair.LOGGER.info("All " + configs.getConfigsList().size() + " have been set properly");
    }
}