package net.villanelle.crystal_craft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.init.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, CrystalCraft.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.getResourceKey(ModBlocks.POLISHED_AMETHYST_BLOCK.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.POLISHED_AMETHYST_STAIRS.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.POLISHED_AMETHYST_SLAB.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.POLISHED_AMETHYST_WALL.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.AMETHYST_STAIRS.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.AMETHYST_SLAB.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.AMETHYST_WALL.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.IRON_GRATE.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.CHIPPING_TABLE.get()));

        tag(BlockTags.WALLS)
                .add(ModBlocks.getResourceKey(ModBlocks.POLISHED_AMETHYST_WALL.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.AMETHYST_WALL.get()));
    }
}
