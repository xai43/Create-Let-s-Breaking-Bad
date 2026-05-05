package net.khai.create_lets_breaking_bad.datagen;

import net.khai.create_lets_breaking_bad.Create_Lets_Breaking_Bad;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Create_Lets_Breaking_Bad.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //LootTable
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(net.khai.create_lets_breaking_bad.datagen.ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));

        //Recipes
        generator.addProvider(event.includeServer(), new net.khai.create_lets_breaking_bad.datagen.ModRecipeProvider(packOutput, lookupProvider));

        //Tags
        BlockTagsProvider blockTagsProvider = new net.khai.create_lets_breaking_bad.datagen.ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new net.khai.create_lets_breaking_bad.datagen.ModItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

        //Map
        generator.addProvider(event.includeServer(), new net.khai.create_lets_breaking_bad.datagen.ModDataMapProvider(packOutput, lookupProvider));

        //ModelsItems
        generator.addProvider(event.includeClient(), new net.khai.create_lets_breaking_bad.datagen.ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new net.khai.create_lets_breaking_bad.datagen.ModBlockStateProvider(packOutput, existingFileHelper));
    }
}
