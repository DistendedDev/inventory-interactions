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
        if (clickType == ClickType.RIGHT && !stack.isEmpty() && !otherStack.isEmpty() && !player.getWorld().isClient()){
            if (stack.isDamaged()) {
                if (stack.getItem().canRepair(stack, otherStack) && Quickrepair.PLAYER_CONFIGS.getBoolean(player, "unit_repair")) {
                    if (!stack.hasEnchantments() || Quickrepair.PLAYER_CONFIGS.getBoolean(player, "unit_repair_enchanted")) {
                        return RepairMethods.unitRepairItem(stack, otherStack, Quickrepair.PLAYER_CONFIGS.getDecimal(player, "unit_repair_rate"));
                    }
                } else if (stack.getItem() == otherStack.getItem() && Quickrepair.PLAYER_CONFIGS.getBoolean(player, "combine_repair")) {
                    if (!stack.hasEnchantments() && !otherStack.hasEnchantments()) {
                        return RepairMethods.combineItems(stack, otherStack, Quickrepair.PLAYER_CONFIGS.getDecimal(player, "combine_bonus"));
                    }
                }
            }
            if (otherStack.getItem() == Items.NAME_TAG && otherStack.hasCustomName() && !stack.hasCustomName() && !stack.getItem().equals(Items.NAME_TAG) && Quickrepair.PLAYER_CONFIGS.getBoolean(player, "item_naming")) {
                return RepairMethods.nameItem(stack, otherStack);
            }
            if (Quickrepair.PLAYER_CONFIGS.getBoolean(player, "shapeless_crafting")){
                if (RepairMethods.craftShapeless(stack, otherStack, slot, cursorStackReference, player, player.getWorld())) {
                    return true;
                }
            }
            if (Quickrepair.PLAYER_CONFIGS.getBoolean(player, "smithing")){
                if (RepairMethods.smithItems(stack, otherStack, slot, cursorStackReference, player, player.getWorld())) {
                    return true;
                }
            }
        }
        return false;
    }

}
