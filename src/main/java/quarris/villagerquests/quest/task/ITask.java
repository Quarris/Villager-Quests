package quarris.villagerquests.quest.task;

import net.minecraft.entity.player.EntityPlayer;

public interface ITask {

    boolean isCompleted(EntityPlayer playerIn);

    Type getType();

    enum Type {
        ITEM,
        MOB,
        EXPLORATION
    }

}
