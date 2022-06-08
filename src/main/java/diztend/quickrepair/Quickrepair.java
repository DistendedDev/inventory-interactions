package diztend.quickrepair;

import diztend.quickrepair.command.OPConfigCommand;
import diztend.quickrepair.command.PlayerConfigCommand;
import diztend.quickrepair.config.GlobalConfig;
import diztend.quickrepair.config.PlayerConfig;
import diztend.quickrepair.event.PlayerCopyFromEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Quickrepair implements ModInitializer {

    public static String MOD_ID = "quickrepair";
    public static String MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();

    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final GlobalConfig CONFIG = new GlobalConfig.Builder(MOD_ID + "-" + MOD_VERSION)
            .addField("unit_repair_default", "true")
            .addField("unit_repair_enchanted_default", "false")
            .addField("unit_repair_rate_default", "0.25")
            .addField("combine_repair_default", "true")
            .addField("combine_bonus_default", "0.0")
            .addField("item_naming_default", "false")
            .addField("shapeless_crafting_default", "false")
            .addField("smithing_default", "true")
            .addField("player_config_command", "false")
            .build();

    public static final PlayerConfig PLAYER_CONFIGS = new PlayerConfig.Builder()
            .boolField("unit_repair", Boolean.parseBoolean(CONFIG.getConfig("unit_repair_default")))
            .boolField("unit_repair_enchanted", Boolean.parseBoolean(CONFIG.getConfig("unit_repair_enchanted_default")))
            .boolField("combine_repair", Boolean.parseBoolean(CONFIG.getConfig("combine_repair_default")))
            .boolField("item_naming", Boolean.parseBoolean(CONFIG.getConfig("item_naming_default")))
            .boolField("shapeless_crafting", Boolean.parseBoolean(CONFIG.getConfig("shapeless_crafting_default")))
            .boolField("smithing", Boolean.parseBoolean(CONFIG.getConfig("smithing_default")))
            .decimalField("unit_repair_rate", Double.parseDouble(CONFIG.getConfig("unit_repair_rate_default")))
            .decimalField("combine_bonus", Double.parseDouble(CONFIG.getConfig("combine_bonus_default")))
            .build();

    public static void log(String info) {
        LOGGER.info(info);
    }

    @Override
    public void onInitialize() {
        if (Boolean.parseBoolean(CONFIG.getConfig("player_config_command"))){
            CommandRegistrationCallback.EVENT.register(PlayerConfigCommand::register);
        }
        CommandRegistrationCallback.EVENT.register(OPConfigCommand::register);
        ServerPlayerEvents.COPY_FROM.register(new PlayerCopyFromEvent());
    }
}
