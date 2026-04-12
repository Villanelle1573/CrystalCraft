package net.villanelle.crystal_craft.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.villanelle.crystal_craft.init.ModBlocks;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.POLISHED_AMETHYST_BLOCK.get());
        dropSelf(ModBlocks.POLISHED_AMETHYST_STAIRS.get());
        add(ModBlocks.POLISHED_AMETHYST_SLAB.get(),
                block -> (createSlabItemTable(ModBlocks.POLISHED_AMETHYST_SLAB.get())));
        dropSelf(ModBlocks.POLISHED_AMETHYST_WALL.get());
        dropSelf(ModBlocks.AMETHYST_STAIRS.get());
        add(ModBlocks.AMETHYST_SLAB.get(),
                block -> (createSlabItemTable(ModBlocks.AMETHYST_SLAB.get())));
        dropSelf(ModBlocks.AMETHYST_WALL.get());
        dropSelf(ModBlocks.IRON_GRATE.get());
        dropSelf(ModBlocks.CHIPPING_TABLE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
