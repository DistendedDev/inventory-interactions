package diztend.quickrepair.config;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtElement;

import java.util.LinkedHashMap;

public class PlayerConfig {

    private final LinkedHashMap<String, NbtElement> defaultValues;

    public PlayerConfig(LinkedHashMap<String, NbtElement> defaultValues) {
        this.defaultValues = defaultValues;
    }

    public boolean getBoolean(Entity entity, String key) {
        if (entity instanceof PlayerDataSaverInterface dataSaver && defaultValues.containsKey(key)) {
            NbtCompound nbt = dataSaver.getQuickRepairData();
            if (!nbt.contains(key)) {
                nbt.put(key, defaultValues.get(key));
            }
            return nbt.getBoolean(key);
        }
        return false;
    }

    public double getDecimal(Entity entity, String key) {
        if (entity instanceof PlayerDataSaverInterface dataSaver && defaultValues.containsKey(key)) {
            NbtCompound nbt = dataSaver.getQuickRepairData();
            if (!nbt.contains(key)) {
                nbt.put(key, defaultValues.get(key));
            }
            return nbt.getDouble(key);
        }
        return 0;
    }

    public static class Builder {

        private final LinkedHashMap<String, NbtElement> configFields = new LinkedHashMap<>();

        public Builder boolField(String name, boolean value) {
            configFields.put(name, NbtByte.of(value));
            return this;
        }

        public Builder decimalField(String name, double value) {
            configFields.put(name, NbtDouble.of(value));
            return this;
        }

        public PlayerConfig build() {
            return new PlayerConfig(configFields);
        }

    }

}
