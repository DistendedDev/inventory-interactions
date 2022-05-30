package diztend.quickrepair.util;

import diztend.quickrepair.Quickrepair;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

import java.util.Optional;

public class RepairMethods {

    public static boolean unitRepairItem(ItemStack stack, ItemStack otherStack, double rate) {
        stack.setDamage((int) Math.max(stack.getDamage() - Math.floor(stack.getMaxDamage() * rate), 0));
        otherStack.decrement(1);
        return true;
    }

    public static boolean combineItems(ItemStack stack, ItemStack otherStack, double bonus) {
        stack.setDamage(Math.max(stack.getDamage() - otherStack.getMaxDamage() + otherStack.getDamage() - (int) (stack.getMaxDamage() * bonus), 0));
        otherStack.decrement(1);
        return true;
    }

    public static boolean nameItem(ItemStack stack, ItemStack otherStack) {
        otherStack.decrement(1);
        stack.setCustomName(otherStack.getName());
        return true;
    }

    public static boolean craftShapeless(ItemStack stack, ItemStack otherStack, Slot slot, StackReference otherSlot, PlayerEntity player, World world) {
        CraftingInventory inventory = new CraftingInventory(player.playerScreenHandler, 2, 2);
        inventory.setStack(0, stack);
        inventory.setStack(1, otherStack);
        Optional<CraftingRecipe> recipe = world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, inventory, world);
        if (recipe.isPresent()) {
            ItemStack output = recipe.get().getOutput().copy();
            int stackCount = stack.getCount();
            int otherStackCount = otherStack.getCount();
            int minStackCount = Math.min(stackCount, otherStackCount);
            stackCount -= minStackCount;
            otherStackCount -= minStackCount;
            int craftedStackCount = output.getCount() * minStackCount;
            if (craftedStackCount <= output.getMaxCount() * 2 && stackCount == 0 && otherStackCount == 0) {
                output.setCount(Math.min(output.getMaxCount(), craftedStackCount));
                craftedStackCount = Math.max(0, craftedStackCount - output.getMaxCount());
                slot.setStack(output.copy());
                output.setCount(craftedStackCount);
                otherSlot.set(output.copy());
                return true;
            } else if (craftedStackCount <= output.getMaxCount()) {
                output.setCount(craftedStackCount);
                if (stackCount == 0) {
                    slot.setStack(output.copy());
                    otherStack.setCount(otherStackCount);
                } else {
                    stack.setCount(stackCount);
                    otherSlot.set(stack.copy());
                    slot.setStack(output.copy());
                }
                return true;
            }
        }
        return false;
    }

    public static boolean smithItems(ItemStack stack, ItemStack otherStack, Slot slot, StackReference otherSlot, PlayerEntity player, World world) {
        Inventory inventory = new SimpleInventory(2);
        inventory.setStack(0, stack);
        inventory.setStack(1, otherStack);
        Optional<SmithingRecipe> recipe = world.getRecipeManager().getFirstMatch(RecipeType.SMITHING, inventory, world);
        if (recipe.isPresent()) {
            ItemStack output = recipe.get().getOutput().copy();
            int stackCount = stack.getCount();
            int otherStackCount = otherStack.getCount();
            int minStackCount = Math.min(stackCount, otherStackCount);
            stackCount -= minStackCount;
            otherStackCount -= minStackCount;
            int craftedStackCount = output.getCount() * minStackCount;
            if (craftedStackCount <= output.getMaxCount() * 2 && stackCount == 0 && otherStackCount == 0) {
                output.setCount(Math.min(output.getMaxCount(), craftedStackCount));
                craftedStackCount = Math.max(0, craftedStackCount - output.getMaxCount());
                slot.setStack(output.copy());
                output.setCount(craftedStackCount);
                otherSlot.set(output.copy());
                return true;
            } else if (craftedStackCount <= output.getMaxCount()) {
                output.setCount(craftedStackCount);
                if (stackCount == 0) {
                    slot.setStack(output.copy());
                    otherStack.setCount(otherStackCount);
                } else {
                    stack.setCount(stackCount);
                    otherSlot.set(stack.copy());
                    slot.setStack(output.copy());
                }
                return true;
            }
        }
        return false;
    }

}
