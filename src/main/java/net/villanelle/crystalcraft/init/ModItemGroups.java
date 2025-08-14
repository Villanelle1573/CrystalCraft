package net.villanelle.crystalcraft.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.villanelle.crystalcraft.CrystalCraft;

import java.util.function.Supplier;

public class ModItemGroups {
    public static final DeferredRegister<CreativeModeTab> CCT = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrystalCraft.MOD_ID);

    public static final Supplier<CreativeModeTab> Crystal_Craft_Blocks = CCT.register("crystal_craft_blocks",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.CRYSTAL_CARVING_TABLE.get()))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .title(Component.translatable("creativetab.crystal_craft.crystal_craft_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.CRYSTAL_CARVING_TABLE);
                    }).build());

    public static void register(IEventBus eventBus) {
        CCT.register(eventBus);
    }
}
