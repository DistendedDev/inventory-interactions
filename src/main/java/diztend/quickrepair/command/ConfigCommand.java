package diztend.quickrepair.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import diztend.quickrepair.config.PlayerDataSaverInterface;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public abstract class ConfigCommand {

    public static int setBoolean(CommandContext<ServerCommandSource> c, String key, boolean value) {
        return setBoolean(c, c.getSource().getEntity(), key, value);
    }

    public static int setDecimal(CommandContext<ServerCommandSource> c, String key, double value) {
        return setDecimal(c, c.getSource().getEntity(), key, value);
    }

    public static int setBoolean(CommandContext<ServerCommandSource> c, Entity entity, String key, boolean value) {
        PlayerDataSaverInterface player = (PlayerDataSaverInterface) entity;
        if (player == null) return 0;
        player.getQuickRepairData().putBoolean(key, value);
        c.getSource().sendFeedback(new LiteralText(entity.getName().getString() + " set " + key + " to " + value), true);
        return Command.SINGLE_SUCCESS;
    }

    public static int setDecimal(CommandContext<ServerCommandSource> c, Entity entity, String key, double value) {
        PlayerDataSaverInterface player = (PlayerDataSaverInterface) entity;
        if (player == null) return 0;
        player.getQuickRepairData().putDouble(key, value);
        c.getSource().sendFeedback(new LiteralText(entity.getName().getString() + " set " + key + " to " + value), true);
        return Command.SINGLE_SUCCESS;
    }

}
