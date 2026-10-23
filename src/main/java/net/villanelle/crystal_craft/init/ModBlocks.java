package net.villanelle.crystal_craft.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.block.ChippingTableBlock;
import net.villanelle.crystal_craft.block.IronGrateBlock;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CrystalCraft.MOD_ID);

    private static <T extends Block>DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func, Supplier<BlockBehaviour.Properties> properties, boolean shouldRegisterItem) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, func, properties);
        if (shouldRegisterItem) ModItems.ITEMS.registerSimpleBlockItem(block);
        return block;
    }

    private static <T extends Block>DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func, boolean shouldRegisterItem) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, func);
        if (shouldRegisterItem) ModItems.ITEMS.registerSimpleBlockItem(block);
        return block;
    }

    // 磨制紫水晶块
    public static final DeferredBlock<Block> POLISHED_AMETHYST_BLOCK = registerBlock(
            "polished_amethyst_block",
            Block :: new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK), true);

    // 磨制紫水晶楼梯
    public static final DeferredBlock<StairBlock> POLISHED_AMETHYST_STAIRS = registerBlock(
            "polished_amethyst_stairs",
            p -> new StairBlock(POLISHED_AMETHYST_BLOCK.get().defaultBlockState(), p),
            () -> BlockBehaviour.Properties.ofFullCopy(POLISHED_AMETHYST_BLOCK.get()), true);

    // 磨制紫水晶台阶
    public static final DeferredBlock<SlabBlock> POLISHED_AMETHYST_SLAB = registerBlock(
            "polished_amethyst_slab",
            SlabBlock :: new, () -> BlockBehaviour.Properties.ofFullCopy(POLISHED_AMETHYST_BLOCK.get()), true);

    // 磨制紫水晶墙
    public static final DeferredBlock<WallBlock> POLISHED_AMETHYST_WALL = registerBlock(
            "polished_amethyst_wall",
            WallBlock :: new, () -> BlockBehaviour.Properties.ofFullCopy(POLISHED_AMETHYST_BLOCK.get()), true);

    // 紫水晶楼梯
    public static final DeferredBlock<StairBlock> AMETHYST_STAIRS = registerBlock(
            "amethyst_stairs",
            p -> new StairBlock(Blocks.AMETHYST_BLOCK.defaultBlockState(), p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK), true);

    // 紫水晶台阶
    public static final DeferredBlock<SlabBlock> AMETHYST_SLAB = registerBlock(
            "amethyst_slab",
            SlabBlock :: new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK), true);

    // 紫水晶墙
    public static final DeferredBlock<WallBlock> AMETHYST_WALL = registerBlock(
            "amethyst_wall",
            WallBlock :: new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK), true);

    // 铁格栅
    public static final DeferredBlock<Block> IRON_GRATE = registerBlock(
            "iron_grate",
            IronGrateBlock :: new, () -> BlockBehaviour.Properties.of().strength(4f, 6f)
                    .sound(SoundType.COPPER_GRATE)
                    .noOcclusion()
                    .isValidSpawn(Blocks :: never)
                    .isRedstoneConductor((state, level, pos) -> false)
                    .isSuffocating((state, level, pos) -> false)
                    .isViewBlocking((state, level, pos, box) -> false), true);

    //雕研台
    public static final DeferredBlock<Block> CHIPPING_TABLE = registerBlock(
            "chipping_table",
            ChippingTableBlock :: new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE), true);

    public static ResourceKey<Block> getResourceKey(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).get();
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
