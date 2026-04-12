package net.villanelle.crystal_craft.init;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.datagen.*;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = CrystalCraft.MOD_ID)
public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(((output, lookupProvider) ->
                new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(
                        ModBlockLootTableProvider :: new, LootContextParamSets.BLOCK
                )), lookupProvider)));

        event.createProvider(ModRecipesProvider.Runner :: new);
        event.createProvider(ModBlockTagsProvider :: new);
        event.createProvider(ModItemTagsProvider :: new);
        event.createProvider(ModEnUsLangProvider :: new);
        event.createProvider(ModZhCnLangProvider :: new);
        event.createProvider(ModModelsProvider :: new);
    }
}
