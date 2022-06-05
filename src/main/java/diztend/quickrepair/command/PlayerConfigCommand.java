package diztend.quickrepair.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import diztend.quickrepair.util.PlayerDataSaverInterface;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class PlayerConfigCommand {

    private static final LiteralArgumentBuilder<ServerCommandSource> command = CommandManager.literal("quickrepair");

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        registerBooleanSetting("unit_repair");
        dispatcher.register(command);
    }

    public static void registerBooleanSetting(String name) {
        command.then(CommandManager.literal(name)
                .then(CommandManager.argument(name, BoolArgumentType.bool())
                .executes(c -> setBoolean(c, name, BoolArgumentType.getBool(c, name)))));
    }

    public static int setBoolean(CommandContext<ServerCommandSource> c, String key, boolean value) {
        PlayerDataSaverInterface player = (PlayerDataSaverInterface) c.getSource().getEntity();
        if (player == null) return 0;
        player.getPersistentData().putBoolean(key, value);
        c.getSource().sendFeedback(new LiteralText(c.getSource().getEntity().getName().getString() + " set " + key + " to " + value), true);
        return Command.SINGLE_SUCCESS;
    }

}
