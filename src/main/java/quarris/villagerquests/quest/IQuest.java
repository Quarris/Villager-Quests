package quarris.villagerquests.quest;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import quarris.villagerquests.quest.task.ITask;

public interface IQuest {

    String getQuestTitle();

    String[] getQuestDescription();

    ITask[] getTasks();

    int getQuestDuration();

    ItemStack[] getItemRewards();

    int getExperienceReward();
}
