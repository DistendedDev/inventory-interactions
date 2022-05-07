package diztend.quickrepair.mixin;

import diztend.quickrepair.Quickrepair;
import diztend.quickrepair.util.RepairMethods;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
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
                if (stack.getItem().canRepair(stack, otherStack) && Quickrepair.getBooleanConfig("do_unit_repair")) {
                    if (!stack.hasEnchantments() || Quickrepair.getBooleanConfig("unit_repair_enchanted_items")) {
                        return RepairMethods.unitRepairItem(stack, otherStack, Quickrepair.getDecimalConfig("unit_repair_rate"));
                    }
                } else if (stack.getItem() == otherStack.getItem() && Quickrepair.getBooleanConfig("do_item_combine")) {
                    if (!stack.hasEnchantments() && !otherStack.hasEnchantments()) {
                        return RepairMethods.combineItems(stack, otherStack, Quickrepair.getDecimalConfig("item_combine_bonus_rate"));
                    }
                }
            }
            if (otherStack.getItem() == Items.NAME_TAG && otherStack.hasCustomName() && Quickrepair.getBooleanConfig("do_item_naming")) {
                return RepairMethods.nameItem(stack, otherStack);
            }
        }
        return false;
    }

}
