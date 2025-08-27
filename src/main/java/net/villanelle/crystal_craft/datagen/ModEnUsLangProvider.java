package net.villanelle.crystal_craft.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.init.ModBlocks;
import net.villanelle.crystal_craft.init.ModItems;

public class ModEnUsLangProvider extends LanguageProvider {
    public ModEnUsLangProvider(PackOutput output) {
        super(output, CrystalCraft.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.POLISHED_AMETHYST.get(), "Polished Amethyst");

        add(ModBlocks.CRYSTAL_CARVING_TABLE.get(), "Crystal Carving Table");

        add("creativetab.crystal_craft.crystal_craft_blocks", "Crystal Craft Blocks");
        add("creativetab.crystal_craft.crystal_craft_materials", "Crystal Craft Materials");

        add("gui.crystal_craft.crystalcarving_table", "Crystal Carving");
    }
}
