package quarris.villagerquests.capability;

import net.minecraft.nbt.NBTTagCompound;
import quarris.villagerquests.quest.IQuest;

import java.util.List;
import java.util.UUID;

public interface IQuestHandler {

    IQuest getQuest();

    void setQuest(IQuest quest);

    List<UUID> getAcceptedPlayerList();

    NBTTagCompound serializeNBT();

    void deserializeNBT(NBTTagCompound nbt);

}
