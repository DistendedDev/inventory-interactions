package diztend.quickrepair.util;

import net.minecraft.item.ItemStack;

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

}
