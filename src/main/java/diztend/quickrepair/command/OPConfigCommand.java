package diztend.quickrepair.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import diztend.quickrepair.Quickrepair;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;

public class OPConfigCommand extends ConfigCommand {

    private static final RequiredArgumentBuilder<ServerCommandSource, GameProfileArgumentType.GameProfileArgument> node = CommandManager.argument("player", GameProfileArgumentType.gameProfile());

    public static void registerBooleanSetting(String name) {
        node.then(CommandManager.literal(name)
                .then(CommandManager.argument(name, BoolArgumentType.bool())
                        .executes(c -> {
                            List<GameProfile> profiles = GameProfileArgumentType.getProfileArgument(c, "player").stream().toList();
                            for (GameProfile profile : profiles) {
                                setBoolean(c, c.getSource().getServer().getPlayerManager().getPlayer(profile.getId()), name, BoolArgumentType.getBool(c, name));
                            }
                            return Command.SINGLE_SUCCESS;
                        })));
    }

    public static void registerDecimalSetting(String name) {
        node.then(CommandManager.literal(name)
                .then(CommandManager.argument(name, DoubleArgumentType.doubleArg())
                        .executes(c -> {
                            List<GameProfile> profiles = GameProfileArgumentType.getProfileArgument(c, "player").stream().toList();
                            for (GameProfile profile : profiles) {
                                setDecimal(c, c.getSource().getServer().getPlayerManager().getPlayer(profile.getId()), name, DoubleArgumentType.getDouble(c, name));
                            }
                            return Command.SINGLE_SUCCESS;
                        })));
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        Quickrepair.log("registering op command");
        registerBooleanSetting("unit_repair");
        registerBooleanSetting("unit_repair_enchanted");
        registerBooleanSetting("combine_repair");
        registerBooleanSetting("item_naming");
        registerBooleanSetting("shapeless_crafting");
        registerBooleanSetting("smithing");
        registerDecimalSetting("unit_repair_rate");
        registerDecimalSetting("combine_bonus");
        dispatcher.register(CommandManager.literal("quick_repair_op").then(node));
    }

}
