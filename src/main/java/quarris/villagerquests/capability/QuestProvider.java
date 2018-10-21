package quarris.villagerquests.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class QuestProvider implements ICapabilitySerializable<NBTTagCompound> {

    protected IQuestHandler questHandler;

    public QuestProvider(IQuestHandler questHandler) {
        this.questHandler = questHandler;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.QUEST_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityHandler.QUEST_CAPABILITY) return (T) questHandler;
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return questHandler.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        questHandler.deserializeNBT(nbt);
    }
}
