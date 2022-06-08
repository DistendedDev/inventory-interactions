package diztend.quickrepair.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import diztend.quickrepair.Quickrepair;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class PlayerConfigCommand extends ConfigCommand{

    private static final LiteralArgumentBuilder<ServerCommandSource> command = CommandManager.literal("quickrepair");

    public static void registerBooleanSetting(String name) {
        command.then(CommandManager.literal(name)
                .then(CommandManager.argument(name, BoolArgumentType.bool())
                        .executes(c -> setBoolean(c, name, BoolArgumentType.getBool(c, name)))));
    }

    public static void registerDecimalSetting(String name) {
        command.then(CommandManager.literal(name)
                .then(CommandManager.argument(name, DoubleArgumentType.doubleArg())
                        .executes(c -> setDecimal(c, name, DoubleArgumentType.getDouble(c, name)))));
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        Quickrepair.log("registering player command");
        registerBooleanSetting("unit_repair");
        registerBooleanSetting("unit_repair_enchanted");
        registerBooleanSetting("combine_repair");
        registerBooleanSetting("item_naming");
        registerBooleanSetting("shapeless_crafting");
        registerBooleanSetting("smithing");
        dispatcher.register(command);
    }

}
