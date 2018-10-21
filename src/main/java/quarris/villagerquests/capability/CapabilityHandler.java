package quarris.villagerquests.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityHandler {

    @CapabilityInject(IQuestHandler.class)
    public static Capability<IQuestHandler> QUEST_CAPABILITY;

    public static void register() {
        CapabilityManager.INSTANCE.register(IQuestHandler.class, new Capability.IStorage<IQuestHandler>() {

            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IQuestHandler> capability, IQuestHandler instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IQuestHandler> capability, IQuestHandler instance, EnumFacing side, NBTBase nbt) {
                instance.deserializeNBT((NBTTagCompound) nbt);
            }
        }, QuestCapbilityHandler::new);
    }

}
