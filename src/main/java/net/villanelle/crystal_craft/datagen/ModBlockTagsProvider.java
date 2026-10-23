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
        // 正确挖掘工具：镐
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

        // 需要石镐及以上才会掉落（木镐、金镐挖了没有掉落物），对应原版 iron_block / copper_grate 的级别
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.getResourceKey(ModBlocks.IRON_GRATE.get()));

        // 楼梯、台阶、墙的分类标签，方便其他模组按类别识别
        tag(BlockTags.STAIRS)
                .add(ModBlocks.getResourceKey(ModBlocks.POLISHED_AMETHYST_STAIRS.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.AMETHYST_STAIRS.get()));

        tag(BlockTags.SLABS)
                .add(ModBlocks.getResourceKey(ModBlocks.POLISHED_AMETHYST_SLAB.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.AMETHYST_SLAB.get()));

        tag(BlockTags.WALLS)
                .add(ModBlocks.getResourceKey(ModBlocks.POLISHED_AMETHYST_WALL.get()))
                .add(ModBlocks.getResourceKey(ModBlocks.AMETHYST_WALL.get()));
    }
}
