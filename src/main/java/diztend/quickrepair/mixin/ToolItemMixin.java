package diztend.quickrepair.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
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
                if (stack.getItem().canRepair(stack, otherStack)) {
                    stack.setDamage((int) Math.max(stack.getDamage() - Math.floor(stack.getMaxDamage() * 0.25), 0));
                    otherStack.decrement(1);
                    return true;
                } else if (stack.getItem() == otherStack.getItem()) {
                    stack.setDamage(Math.max(stack.getDamage() - otherStack.getMaxDamage() + otherStack.getDamage(), 0));
                    NbtList enchantmentList = otherStack.getEnchantments();
                    for (int i = 0; i < enchantmentList.size(); i++) {
                        //add enchantments
                    }
                    otherStack.decrement(1);
                    return true;
                }
            } else if (otherStack.getItem() == Items.ENCHANTED_BOOK && stack.isEnchantable()) {
                NbtList enchantmentList = EnchantedBookItem.getEnchantmentNbt(otherStack);
                for (int i = 0; i < enchantmentList.size(); i++) {
                    //add enchantments
                }
                otherStack.decrement(1);
                return true;
            }
        }
        return false;
    }

}
