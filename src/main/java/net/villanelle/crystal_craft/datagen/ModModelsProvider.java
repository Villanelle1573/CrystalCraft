package net.villanelle.crystal_craft.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.init.ModBlocks;
import net.villanelle.crystal_craft.init.ModItems;

import java.util.stream.Stream;

public class ModModelsProvider extends ModelProvider {
    public ModModelsProvider(PackOutput output) {
        super(output, CrystalCraft.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.POLISHED_AMETHYST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AMETHYST_CANDY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.AMETHYST_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.GRINDING_WHEEL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.WIRE_SAW.get(), ModelTemplates.FLAT_ITEM);

        blockModels.family(ModBlocks.POLISHED_AMETHYST_BLOCK.get())
                .stairs(ModBlocks.POLISHED_AMETHYST_STAIRS.get())
                .slab(ModBlocks.POLISHED_AMETHYST_SLAB.get())
                .wall(ModBlocks.POLISHED_AMETHYST_WALL.get());

        blockModels.family(Blocks.AMETHYST_BLOCK)
                .stairs(ModBlocks.AMETHYST_STAIRS.get())
                .slab(ModBlocks.AMETHYST_SLAB.get())
                .wall(ModBlocks.AMETHYST_WALL.get());

        blockModels.createTrivialCube(ModBlocks.IRON_GRATE.get());

        blockModels.createHorizontallyRotatedBlock(
                ModBlocks.CHIPPING_TABLE.get(),
                TexturedModel.CUBE_BOTTOM_TOP);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream();
    }
}
