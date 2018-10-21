package quarris.villagerquests.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import org.apache.commons.io.FileUtils;
import quarris.villagerquests.VillagerQuests;
import quarris.villagerquests.capability.CapabilityHandler;
import quarris.villagerquests.capability.IQuestHandler;
import quarris.villagerquests.quest.impl.Quest;
import quarris.villagerquests.quest.task.ITask;
import quarris.villagerquests.quest.task.ItemTask;
import quarris.villagerquests.util.JsonUtil;

import java.io.*;
import java.util.*;

public class QuestManager {

    public static final Map<String, IQuest> QUESTS = new HashMap<>();

    public static void assignQuest(EntityVillager villager, String quest) {
        IQuestHandler cap = villager.getCapability(CapabilityHandler.QUEST_CAPABILITY, null);
        if (cap != null) {
            cap.setQuest(QUESTS.get(quest));
        }
    }

    public static void assignRandomQuest(EntityVillager villager) {
        IQuestHandler cap = villager.getCapability(CapabilityHandler.QUEST_CAPABILITY, null);
        if (cap != null) {
            cap.setQuest(new ArrayList<>(QUESTS.values()).get(villager.world.rand.nextInt(QUESTS.size())));
        }
    }

    public static void loadQuests() {
        copyInternalQuests();
        for (File file : Objects.requireNonNull(new File(VillagerQuests.CONFIG_DIR.getPath(), "quests").listFiles())) {
            if (file.getName().endsWith(".json")) {
                try {
                    IQuest quest = new Quest(new JsonParser().parse(new JsonReader(new BufferedReader(new FileReader(file)))).getAsJsonObject());
                    QUESTS.put(quest.getQuestTitle().toLowerCase().replaceAll("/ /", "_"), quest);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void copyInternalQuests() {
        for (File file : Objects.requireNonNull(new File(VillagerQuests.MOD_ASSETS_DIR, "quests").listFiles())) {
            if (file.getName().endsWith(".json")) {
                try {
                    FileUtils.copyFile(file, new File(VillagerQuests.CONFIG_DIR.getPath() + "/quests", file.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* Not used anymore
    public static JsonObject convertQuestToJson(IQuest quest) {
        JsonObject main = new JsonObject();
        main.addProperty("title", quest.getQuestTitle());
        JsonArray desc = new JsonArray();
        for (String descPart : quest.getQuestDescription()) {
            desc.add(descPart);
        }
        main.add("description", desc);

        JsonArray tasks = new JsonArray();
        for (ITask task : quest.getTasks()) {
            tasks.add(convertTaskToJson(task));
        }
        main.add("tasks", tasks);
        main.addProperty("duration", quest.getQuestDuration());
        JsonArray itemRewards = new JsonArray();
        for (ItemStack stack : quest.getItemRewards()) {
            JsonObject itemReq = new JsonObject();
            itemReq.addProperty("name", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
            itemReq.addProperty("amount", stack.getCount());
            itemReq.addProperty("meta", stack.getMetadata());
            itemRewards.add(itemReq);
        }
        main.add("item_rewards", itemRewards);
        main.addProperty("xp_rewards", quest.getExperienceReward());
        return main;
    }
    */
    /*
    private static JsonObject convertTaskToJson(ITask task) {
        JsonObject jsonTask = new JsonObject();
        switch (task.getType()) {
            case ITEM: {
                ItemTask itemTask = (ItemTask) task;
                jsonTask.addProperty("type", ITask.Type.ITEM.toString().toUpperCase());
                JsonArray reqs = new JsonArray();
                for (ItemStack stack : itemTask.getRequiredItems()) {
                    JsonObject itemReq = new JsonObject();
                    itemReq.addProperty("name", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
                    itemReq.addProperty("amount", stack.getCount());
                    itemReq.addProperty("meta", stack.getMetadata());
                    reqs.add(itemReq);
                }
                jsonTask.add("items", reqs);
            }
        }
        return jsonTask;
    }
    */

    public static ITask getTaskFromType(ITask.Type type, JsonObject object) {
        ITask task = null;
        switch (type) {
            case ITEM: {
                JsonArray itemsArray = JsonUtils.getJsonArray(object, "items");
                ItemStack[] itemStacks = new ItemStack[itemsArray.size()];
                for (int i = 0; i < itemsArray.size(); i++) {
                    JsonObject itemJson = itemsArray.get(i).getAsJsonObject();
                    String name = JsonUtils.getString(itemJson, "name");
                    int amount = JsonUtil.get(itemJson, "amount", 1);
                    int meta = JsonUtil.get(itemJson, "meta", 0);
                    ItemStack stack = new ItemStack(Item.getByNameOrId(name), amount, meta);
                    itemStacks[i] = stack;
                }
                task = new ItemTask(itemStacks);
            }
        }
        return task;
    }
}
