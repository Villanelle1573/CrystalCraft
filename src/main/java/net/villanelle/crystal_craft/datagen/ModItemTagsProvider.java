package net.villanelle.crystal_craft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.init.ModItemTags;
import net.villanelle.crystal_craft.init.ModItems;

import java.util.concurrent.CompletableFuture;

import static net.villanelle.crystal_craft.init.ModItems.GRINDING_WHEEL;
import static net.villanelle.crystal_craft.init.ModItems.WIRE_SAW;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, CrystalCraft.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(ModItemTags.CHIPPING_TOOLS)
                .add(ModItems.getResourceKey(GRINDING_WHEEL.get()))
                .add(ModItems.getResourceKey(WIRE_SAW.get()));
    }
}
