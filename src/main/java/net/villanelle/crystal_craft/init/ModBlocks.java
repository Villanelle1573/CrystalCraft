package net.villanelle.crystal_craft.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.block.CrystalCarvingTable;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CrystalCraft.MOD_ID);

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(toReturn, name);
        return toReturn;
    }

    // 晶雕台
    public static final DeferredBlock<CrystalCarvingTable> CRYSTAL_CARVING_TABLE = registerBlock(
            "crystalcarving_table", CrystalCarvingTable::new);

    public static final DeferredBlock<Block> POLISHED_AMETHYST_BLOCK = registerBlock(
            "polished_amethyst_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.AMETHYST)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<StairBlock> POLISHED_AMETHYST_STAIRS = registerBlock(
            "polished_amethyst_stairs",
            () -> new StairBlock(ModBlocks.POLISHED_AMETHYST_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.of()
                            .sound(SoundType.AMETHYST)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<SlabBlock> POLISHED_AMETHYST_SLAB = registerBlock(
            "polished_amethyst_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.AMETHYST)
                    .requiresCorrectToolForDrops()));

    private static <T extends Block> void registerBlockItem(DeferredBlock<T> block, String name) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
