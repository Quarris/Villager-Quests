package quarris.villagerquests.quest.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ItemTask implements ITask {

    private ItemStack[] requiredItems;

    public ItemTask(ItemStack[] requiredItems) {
        this.requiredItems = requiredItems;
    }

    @Override
    public boolean isCompleted(EntityPlayer playerIn) {
        List<ItemStack> items = Arrays.asList(this.requiredItems);
        for (int i = 0; i < items.size(); i++) {
            ItemStack taskStack = items.get(i);
            for (int j = 0; j < playerIn.inventory.mainInventory.size(); j++) {
                ItemStack playerStack = playerIn.inventory.mainInventory.get(j);
                if (ItemStack.areItemsEqual(taskStack, playerStack)) {
                    items.remove(i);
                    break;
                }
                else if (j == playerIn.inventory.mainInventory.size() - 1) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Type getType() {
        return Type.ITEM;
    }

    public ItemStack[] getRequiredItems() {
        return requiredItems;
    }
}
