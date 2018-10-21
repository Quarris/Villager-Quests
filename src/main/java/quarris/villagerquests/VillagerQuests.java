package quarris.villagerquests;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import quarris.villagerquests.capability.CapabilityHandler;
import quarris.villagerquests.event.ModEvents;
import quarris.villagerquests.gui.GuiHandler;
import quarris.villagerquests.proxy.IProxy;
import quarris.villagerquests.quest.IQuest;
import quarris.villagerquests.quest.QuestManager;
import quarris.villagerquests.quest.impl.Quest;
import quarris.villagerquests.quest.task.ItemTask;

import java.io.File;

@Mod(modid = VillagerQuests.MODID, name = VillagerQuests.NAME, version = VillagerQuests.VERSION)
public class VillagerQuests {
    public static final String MODID = "villagerquests";
    public static final String NAME = "Villager Quests";
    public static final String VERSION = "0.0.1";

    public Logger logger;

    public static File CONFIG_DIR;
    public static File MOD_ASSETS_DIR;

    public static ResourceLocation createRes(String resource) {
        return new ResourceLocation(MODID, resource);
    }

    @Mod.Instance
    private static VillagerQuests instance;

    public static VillagerQuests getInst() {
        return instance;
    }

    @SidedProxy(clientSide = "quarris.villagerquests.proxy.ClientProxy", serverSide = "quarris.villagerquests.proxy.ServerProxy")
    public static IProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        logger.info(NAME + " is starting preInit.");
        proxy.preInit(e);
        e.getSourceFile();
        MinecraftForge.EVENT_BUS.register(new ModEvents());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        CapabilityHandler.register();
        QuestManager.loadQuests();
        logger.info(NAME + " has finished preInit.");
    }

    @EventHandler
    public void init(FMLInitializationEvent e)  {
        logger.info(NAME + " is starting init.");
        proxy.init(e);
        // ... code here
        logger.info(NAME + " has finished init.");

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        logger.info(NAME + " is starting postInit.");
        proxy.postInit(e);
        // ... code here
        logger.info(NAME + " has finished postInit.");
    }
}
