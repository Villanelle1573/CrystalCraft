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
        add(ModItems.AMETHYST_CANDY.get(), "Amethyst Candy");
        add(ModItems.AMETHYST_PICKAXE.get(), "Amethyst Pickaxe");

        add(ModBlocks.CRYSTAL_CARVING_TABLE.get(), "Crystal Carving Table");
        add(ModBlocks.POLISHED_AMETHYST_BLOCK.get(), "Polished Amethyst Block");
        add(ModBlocks.POLISHED_AMETHYST_STAIRS.get(), "Polished Amethyst Stairs");
        add(ModBlocks.POLISHED_AMETHYST_SLAB.get(), "Polished Amethyst Slab");
        add(ModBlocks.POLISHED_AMETHYST_WALL.get(), "Polished Amethyst Wall");
        add(ModBlocks.AMETHYST_STAIRS.get(), "Amethyst Stairs");
        add(ModBlocks.AMETHYST_SLAB.get(), "Amethyst Slab");
        add(ModBlocks.AMETHYST_WALL.get(), "Amethyst Wall");

        add("itemGroup.crystal_craft.crystal_craft_blocks", "Crystal Craft Blocks");
        add("itemGroup.crystal_craft.crystal_craft_items", "Crystal Craft Items");

        add("tooltip.crystal_craft.amethyst_pickaxe", "Can Mine Budding Amethyst");

        add("gui.crystal_craft.crystalcarving_table", "Crystal Carving");
    }
}
