package net.khai.create_lets_breaking_bad.datagen;

import net.khai.create_lets_breaking_bad.Create_Lets_Breaking_Bad;
import net.khai.create_lets_breaking_bad.block.ModBlocks;
import net.khai.create_lets_breaking_bad.block.custom.BismuthLampBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Create_Lets_Breaking_Bad.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        customLamp();

    }

    private void customLamp() {
        getVariantBuilder(ModBlocks.BISMUTH_LAMP.get()).forAllStates(state -> {
            if(state.getValue(BismuthLampBlock.CLICKED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("bismuth_lamp_on",
                        ResourceLocation.fromNamespaceAndPath(Create_Lets_Breaking_Bad.MOD_ID, "block/" + "bismuth_lamp_on")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("bismuth_lamp_off",
                        ResourceLocation.fromNamespaceAndPath(Create_Lets_Breaking_Bad.MOD_ID, "block/" + "bismuth_lamp_off")))};
            }
        });

        simpleBlockItem(ModBlocks.BISMUTH_LAMP.get(), models().cubeAll("bismuth_lamp_off",
                ResourceLocation.fromNamespaceAndPath(Create_Lets_Breaking_Bad.MOD_ID, "block/bismuth_lamp_off")));
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("create_lets_breaking_bad:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("create_lets_breaking_bad:block/" + deferredBlock.getId().getPath() + appendix));
    }
}