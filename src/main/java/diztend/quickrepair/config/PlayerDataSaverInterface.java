package diztend.quickrepair.config;

import net.minecraft.nbt.NbtCompound;

public interface PlayerDataSaverInterface {
    NbtCompound getQuickRepairData();
    void setQuickRepairData(NbtCompound nbt);
}
