package quarris.villagerquests.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import quarris.villagerquests.quest.IQuest;
import quarris.villagerquests.quest.QuestManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestCapbilityHandler implements IQuestHandler {

    private IQuest currentQuest;
    private List<UUID> acceptedPlayerList;

    public QuestCapbilityHandler() {
        currentQuest = null;//QuestManager.QUESTS.get("A Quest Title".toLowerCase().replaceAll("/ /", "_"));
        acceptedPlayerList = new ArrayList<>();
    }

    @Override
    public IQuest getQuest() {
        return currentQuest;
    }

    @Override
    public void setQuest(IQuest quest) {
        currentQuest = quest;
    }

    @Override
    public List<UUID> getAcceptedPlayerList() {
        return acceptedPlayerList;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList playerList = new NBTTagList();
        if (currentQuest != null) {
            for (UUID uuid : acceptedPlayerList) {
                NBTTagCompound uuidTag = new NBTTagCompound();
                uuidTag.setUniqueId("uuid", uuid);
                playerList.appendTag(uuidTag);
            }
            tag.setString("quest", currentQuest.getQuestTitle());
            tag.setTag("playerList", playerList);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        currentQuest = QuestManager.QUESTS.get(nbt.getString("quest"));
        NBTTagList playerList = nbt.getTagList("playerList", 9);
        for (NBTBase base : playerList) {
            NBTTagCompound tag = (NBTTagCompound) base;
            acceptedPlayerList.add(tag.getUniqueId("uuid"));
        }
    }
}
