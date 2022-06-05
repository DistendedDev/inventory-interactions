package diztend.quickrepair;

import diztend.quickrepair.command.PlayerConfigCommand;
import diztend.quickrepair.config.Config;
import diztend.quickrepair.event.PlayerCopyFromEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Quickrepair implements ModInitializer {

    public static String MOD_ID = "quickrepair";
    public static String MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();

    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final Config CONFIG = new Config.Builder(MOD_ID + "-" + MOD_VERSION)
            .addField("unit_repair_default", "true")
            .addField("unit_repair_enchanted_default", "false")
            .addField("unit_repair_rate_default", "0.25")
            .addField("combine_repair_default", "true")
            .addField("combine_bonus_default", "0.0")
            .addField("item_naming_default", "false")
            .addField("shapeless_crafting_default", "false")
            .addField("smithing_default", "true")
            .addField("op_config_command", "true")
            .addField("player_config_command", "false")
            .build();

    public static boolean getBooleanConfig(String key) {
        if (CONFIG.getConfig(key) == null) return false;
        return CONFIG.getConfig(key).equals("true");
    }

    public static double getDecimalConfig(String key) {
        return Double.parseDouble(CONFIG.getConfig(key));
    }

    public static void log(String info) {
        LOGGER.info(info);
    }

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(PlayerConfigCommand::register);
        ServerPlayerEvents.COPY_FROM.register(new PlayerCopyFromEvent());
    }
}
