package diztend.quickrepair.util;

import net.minecraft.nbt.NbtCompound;

public interface PlayerDataSaverInterface {
    NbtCompound getPersistentData();
    void setPersistentData(NbtCompound nbt);
}
