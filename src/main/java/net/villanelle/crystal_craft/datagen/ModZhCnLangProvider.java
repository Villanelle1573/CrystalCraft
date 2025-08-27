package net.villanelle.crystal_craft.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.init.ModBlocks;
import net.villanelle.crystal_craft.init.ModItems;

public class ModZhCnLangProvider extends LanguageProvider {
    public ModZhCnLangProvider(PackOutput output) {
        super(output, CrystalCraft.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.POLISHED_AMETHYST.get(), "磨制紫水晶");

        add(ModBlocks.CRYSTAL_CARVING_TABLE.get(), "晶雕台");

        add("creativetab.crystal_craft.crystal_craft_blocks", "晶饰工艺方块");
        add("creativetab.crystal_craft.crystal_craft_materials", "晶饰工艺材料");

        add("gui.crystal_craft.crystalcarving_table", "晶体雕琢");
    }
}
