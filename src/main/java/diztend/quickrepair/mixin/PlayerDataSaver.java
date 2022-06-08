package diztend.quickrepair.mixin;

import diztend.quickrepair.Quickrepair;
import diztend.quickrepair.config.PlayerDataSaverInterface;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class PlayerDataSaver implements PlayerDataSaverInterface {

    private NbtCompound quickRepairData;

    @Override
    public void setQuickRepairData(NbtCompound quickRepairData) {
        this.quickRepairData = quickRepairData;
    }

    @Override
    public NbtCompound getQuickRepairData() {
        if (quickRepairData == null) {
            quickRepairData = new NbtCompound();
        }
        return quickRepairData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void writeData(NbtCompound nbt, CallbackInfoReturnable info) {
        if (quickRepairData != null) {
            nbt.put(Quickrepair.MOD_ID + ".config_data", quickRepairData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void readData(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains(Quickrepair.MOD_ID + ".config_data", NbtElement.COMPOUND_TYPE)) {
            quickRepairData = nbt.getCompound(Quickrepair.MOD_ID + ".config_data");
        }
    }

}
