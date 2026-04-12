package net.villanelle.crystal_craft.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.villanelle.crystal_craft.CrystalCraft;

import java.util.function.Supplier;

public class ModItemGroups {
    public static final DeferredRegister<CreativeModeTab> CCT = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrystalCraft.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> Crystal_Craft_Blocks =
            CCT.register("crystal_craft_blocks", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.POLISHED_AMETHYST_BLOCK.get()))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .title(Component.translatable("itemGroup.crystal_craft.crystal_craft_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.AMETHYST_STAIRS);
                        output.accept(ModBlocks.AMETHYST_SLAB);
                        output.accept(ModBlocks.AMETHYST_WALL);
                        output.accept(ModBlocks.CHIPPING_TABLE);
                        output.accept(ModBlocks.POLISHED_AMETHYST_BLOCK);
                        output.accept(ModBlocks.POLISHED_AMETHYST_STAIRS);
                        output.accept(ModBlocks.POLISHED_AMETHYST_SLAB);
                        output.accept(ModBlocks.POLISHED_AMETHYST_WALL);
                        output.accept(ModBlocks.IRON_GRATE);
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> Crystal_Craft_Items =
            CCT.register("crystal_craft_items", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.POLISHED_AMETHYST.get()))
                    .withTabsBefore(Crystal_Craft_Blocks.getId())
                    .title(Component.translatable("itemGroup.crystal_craft.crystal_craft_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.POLISHED_AMETHYST);
                        output.accept(ModItems.AMETHYST_CANDY);
                        output.accept(ModItems.AMETHYST_PICKAXE);
                        output.accept(ModItems.GRINDING_WHEEL);
                        output.accept(ModItems.WIRE_SAW);
                    }).build());

    public static void register(IEventBus eventBus) {
        CCT.register(eventBus);
    }
}
