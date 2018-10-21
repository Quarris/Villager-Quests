package quarris.villagerquests.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import quarris.villagerquests.VillagerQuests;

import java.io.File;
import java.util.Objects;

public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        VillagerQuests.CONFIG_DIR = new File(e.getModConfigurationDirectory(), VillagerQuests.MODID);
        VillagerQuests.CONFIG_DIR.mkdirs();
        VillagerQuests.MOD_ASSETS_DIR = new File(Objects.requireNonNull(VillagerQuests.class.getClassLoader().getResource("assets/villagerquests/")).getFile());
    }

    @Override
    public void init(FMLInitializationEvent e) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {

    }
}
