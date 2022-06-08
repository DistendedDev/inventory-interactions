package diztend.quickrepair.event;

import diztend.quickrepair.config.PlayerDataSaverInterface;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerCopyFromEvent implements ServerPlayerEvents.CopyFrom {
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        PlayerDataSaverInterface old = (PlayerDataSaverInterface) oldPlayer;
        PlayerDataSaverInterface player = (PlayerDataSaverInterface) newPlayer;
        player.setQuickRepairData(old.getQuickRepairData().copy());
    }
}
