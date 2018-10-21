package quarris.villagerquests.quest.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import quarris.villagerquests.quest.IQuest;
import quarris.villagerquests.quest.QuestManager;
import quarris.villagerquests.quest.task.ITask;
import quarris.villagerquests.util.JsonUtil;

import java.util.List;
import java.util.Objects;

public class Quest implements IQuest {

    private String questTitle;
    private String[] questDescription;
    private VillagerProfession profession;
    private VillagerCareer career;
    private ITask[] tasks;
    private int questDuration;
    private ItemStack[] itemRewards;
    private int experienceReward;

    public Quest(String title, String[] desc, ITask[] tasks, int dur, ItemStack[] itemRewards, int xpReward) {
        this.questTitle = title;
        this.questDescription = desc;
        this.tasks = tasks;
        this.questDuration = dur;
        this.itemRewards = itemRewards;
        this.experienceReward = xpReward;
    }

    public Quest(JsonObject jsonQuest) {
        // Quest Title
        questTitle = JsonUtils.getString(jsonQuest, "title");

        // Quest Description
        JsonArray jsonDescription = JsonUtils.getJsonArray(jsonQuest, "description");
        questDescription = new String[jsonDescription.size()];
        for (int i = 0; i < jsonDescription.size(); i++) {
            questDescription[i] = jsonDescription.get(i).getAsString();
        }

        // Quest Profession
        profession = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation((jsonQuest.get("profession").getAsString())));
        if (profession == null) {
            profession = VillagerRegistry.FARMER;
        }
        String careerName = JsonUtil.get(jsonQuest, "career", null);
        if (careerName != null) {
            List<VillagerCareer> careers = ReflectionHelper.getPrivateValue(VillagerProfession.class, profession, 3);
            for (VillagerCareer career : careers) {
                if (careerName.equalsIgnoreCase(career.getName())) {
                    this.career = career;
                    break;
                }
            }
        }

        // Tasks
        JsonArray jsonTasks = JsonUtils.getJsonArray(jsonQuest, "tasks");
        tasks = new ITask[jsonTasks.size()];
        for (int i = 0; i < jsonTasks.size(); i++) {
            JsonObject taskJson = jsonTasks.get(i).getAsJsonObject();
            String type = JsonUtils.getString(taskJson, "type").toUpperCase();
            ITask task = QuestManager.getTaskFromType(ITask.Type.valueOf(type), taskJson);
            tasks[i] = task;
        }

        // Duration
        questDuration = JsonUtils.getInt(jsonQuest, "duration");

        // Item Rewards
        JsonArray jsonItemRewards = JsonUtils.getJsonArray(jsonQuest, "item_rewards");
        itemRewards = new ItemStack[jsonItemRewards.size()];
        for (int i = 0; i < jsonItemRewards.size(); i++) {
            JsonObject item = jsonItemRewards.get(i).getAsJsonObject();
            String name = JsonUtils.getString(item, "name");
            int amount = JsonUtil.get(item, "amount", 1);
            int meta = JsonUtil.get(item, "meta", 0);
            if (amount > 64) {
                amount = 64;
            }
            ItemStack stack = new ItemStack(Objects.requireNonNull(Item.getByNameOrId(name)), amount, meta);
            itemRewards[i] = stack;
        }

        // XP Rewards
        experienceReward = JsonUtil.get(jsonQuest, "xp_reward", 0);
    }

    @Override
    public String getQuestTitle() {
        return questTitle;
    }

    @Override
    public String[] getQuestDescription() {
        return questDescription;
    }

    @Override
    public ITask[] getTasks() {
        return tasks;
    }

    @Override
    public int getQuestDuration() {
        return questDuration;
    }

    @Override
    public ItemStack[] getItemRewards() {
        return itemRewards;
    }

    @Override
    public int getExperienceReward() {
        return experienceReward;
    }
}

