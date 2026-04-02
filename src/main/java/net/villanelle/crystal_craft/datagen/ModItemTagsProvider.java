package net.villanelle.crystal_craft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.villanelle.crystal_craft.CrystalCraft;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, CrystalCraft.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {

    }
}
