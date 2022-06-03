package diztend.quickrepair.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import diztend.quickrepair.Quickrepair;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class QuickRepairConfigCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("quickrepair")
                .then(CommandManager.literal("unit_repair").executes(c -> {
                    Quickrepair.log("command detected");
                    return Command.SINGLE_SUCCESS;
                })));
    }

}
