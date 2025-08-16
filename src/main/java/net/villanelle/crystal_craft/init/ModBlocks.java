package net.villanelle.crystal_craft.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.block.CrystalCarvingTable;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CrystalCraft.MOD_ID);

    // 晶雕台
    public static final DeferredBlock<CrystalCarvingTable> CRYSTAL_CARVING_TABLE = registerBlock(
            CrystalCarvingTable::new);

    private static <T extends Block> DeferredBlock<T> registerBlock(Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register("crystalcarving_table", block);
        registerBlockItem(toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(DeferredBlock<T> block) {
        ModItems.ITEMS.register("crystalcarving_table", () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
