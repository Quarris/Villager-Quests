package quarris.villagerquests.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quarris.villagerquests.VillagerQuests;
import quarris.villagerquests.capability.QuestCapbilityHandler;
import quarris.villagerquests.capability.QuestProvider;
import quarris.villagerquests.quest.QuestManager;

public class ModEvents {

    @SubscribeEvent
    public void attachCapabilityEvent(AttachCapabilitiesEvent<Entity> e) {
       if (e.getObject() instanceof EntityVillager) {
           e.addCapability(VillagerQuests.createRes("quest_capability"), new QuestProvider(new QuestCapbilityHandler()));
       }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract e) {
        if (e.getTarget() instanceof EntityVillager) {
            if (e.getEntityPlayer().isSneaking()) {
                e.getEntityPlayer().openGui(VillagerQuests.getInst(), 0, e.getWorld(), e.getTarget().getEntityId(), 0, 0);

            }
            else {
                QuestManager.assignRandomQuest((EntityVillager)e.getTarget());
            }
        }
    }
}
