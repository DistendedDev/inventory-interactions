package diztend.quickrepair;

import com.mojang.brigadier.CommandDispatcher;
import diztend.quickrepair.command.QuickRepairConfigCommand;
import diztend.quickrepair.config.Config;
import diztend.quickrepair.event.PlayerCopyFromEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Quickrepair implements ModInitializer {

    public static String MOD_ID = "quickrepair";

    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final Config CONFIG = new Config.Builder(MOD_ID)
            .addField("do_unit_repair", "true")
            .addField("unit_repair_enchanted_items", "false")
            .addField("unit_repair_rate", "0.25")
            .addField("do_item_combine", "true")
            .addField("item_combine_bonus_rate", "0.0")
            .addField("do_item_naming", "true")
            .addField("do_shapeless_crafting", "true")
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
        CommandRegistrationCallback.EVENT.register(QuickRepairConfigCommand::register);
        ServerPlayerEvents.COPY_FROM.register(new PlayerCopyFromEvent());
    }
}
