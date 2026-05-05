package net.khai.create_lets_breaking_bad;

import net.khai.create_lets_breaking_bad.block.ModBlocks;
import net.khai.create_lets_breaking_bad.item.ModCreativeModTabs;
import net.khai.create_lets_breaking_bad.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Create_Lets_Breaking_Bad.MOD_ID)
public class Create_Lets_Breaking_Bad {
    public static final String MOD_ID = "create_lets_breaking_bad";
    public static final Logger LOGGER = LogUtils.getLogger();



    public Create_Lets_Breaking_Bad(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
    }



    private void commonSetup(FMLCommonSetupEvent event) {
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    //@EventBusSubscriber(modid = "create_lets_breaking_bad", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    //public static class ClientModEvents {
    //    @SubscribeEvent
    //    public static void onClientSetup(FMLClientSetupEvent event) {
    //        // Здесь должен быть хоть какой-то код, помеченный @SubscribeEvent
    //        // Например, лог о запуске клиента
    //    }
}