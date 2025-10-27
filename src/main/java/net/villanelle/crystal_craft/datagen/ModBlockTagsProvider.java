package net.villanelle.crystal_craft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.init.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CrystalCraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.CRYSTAL_CARVING_TABLE.get())
                .add(ModBlocks.POLISHED_AMETHYST_BLOCK.get())
                .add(ModBlocks.POLISHED_AMETHYST_STAIRS.get())
                .add(ModBlocks.POLISHED_AMETHYST_SLAB.get())
                .add(ModBlocks.AMETHYST_STAIRS.get())
                .add(ModBlocks.AMETHYST_SLAB.get())
                .add(ModBlocks.IRON_GRATE.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.POLISHED_AMETHYST_WALL.get())
                .add(ModBlocks.AMETHYST_WALL.get());
    }
}
