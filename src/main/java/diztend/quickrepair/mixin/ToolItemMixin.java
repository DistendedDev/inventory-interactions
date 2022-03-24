package diztend.quickrepair.mixin;

import diztend.quickrepair.config.ModConfigs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ClickType;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Item.class)
public abstract class ToolItemMixin {

    /**
     * @author Diztend
     */
    @Overwrite()
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT){
            if (stack.isDamaged()) {
                if (stack.getItem().canRepair(stack, otherStack) && ModConfigs.DO_UNIT_REPAIR > 0) {
                    stack.setDamage((int) Math.max(stack.getDamage() - Math.floor(stack.getMaxDamage() * ModConfigs.UNIT_REPAIR_RATE), 0));
                    otherStack.decrement(1);
                    return true;
                } else if (stack.getItem() == otherStack.getItem() && ModConfigs.DO_ITEM_COMBINE > 0) {
                    if (!stack.hasEnchantments() && !otherStack.hasEnchantments()){
                        stack.setDamage(Math.max(stack.getDamage() - otherStack.getMaxDamage() + otherStack.getDamage(), 0));
                        otherStack.decrement(1);
                        return true;
                    }
                }
            }
            if (otherStack.getItem() == Items.NAME_TAG && ModConfigs.DO_ITEM_NAMING > 0) {
                otherStack.decrement(1);
                stack.setCustomName(otherStack.getName());
                return true;
            }
        }
        return false;
    }

}
