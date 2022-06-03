package diztend.quickrepair.mixin;

import diztend.quickrepair.Quickrepair;
import diztend.quickrepair.util.PlayerDataSaverInterface;
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

    private NbtCompound persistentData;

    @Override
    public void setPersistentData(NbtCompound persistentData) {
        this.persistentData = persistentData;
    }

    @Override
    public NbtCompound getPersistentData() {
        if (persistentData == null) {
            persistentData = new NbtCompound();
        }
        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void writeData(NbtCompound nbt, CallbackInfoReturnable info) {
        if (persistentData != null) {
            nbt.put(Quickrepair.MOD_ID + ".config_data", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void readData(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains(Quickrepair.MOD_ID + ".config_data", NbtElement.COMPOUND_TYPE)) {
            persistentData = nbt.getCompound(Quickrepair.MOD_ID + ".config_data");
        }
    }

}
