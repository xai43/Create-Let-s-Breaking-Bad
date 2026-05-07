package net.khai.create_lets_breaking_bad.item;

import net.khai.create_lets_breaking_bad.Create_Lets_Breaking_Bad;
import net.khai.create_lets_breaking_bad.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Create_Lets_Breaking_Bad.MOD_ID);

    public static final Supplier<CreativeModeTab> BISMUTH_ITEMS_TAB = CREATIVE_MODE_TAB.register("bismuth_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CYANIDE.get()))
                    .title(Component.translatable("creativetab.create_lets_breaking_bad.bismuth_items"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModBlocks.BISMUTH_LAMP);
                        output.accept(ModItems.CYANIDE);
                        output.accept(ModItems.RADISH);
                        output.accept(ModItems.METH);

                    }).build());

    public static final Supplier<CreativeModeTab> BISMUTH_BLOCK_TAB = CREATIVE_MODE_TAB.register("bismuth_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.BISMUTH_LAMP.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Create_Lets_Breaking_Bad.MOD_ID, "bismuth_items_tab"))
                    .title(Component.translatable("creativetab.create_lets_breaking_bad.bismuth_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModBlocks.BISMUTH_LAMP);

                    }).build());

    public static void register(IEventBus eventBus)  {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
