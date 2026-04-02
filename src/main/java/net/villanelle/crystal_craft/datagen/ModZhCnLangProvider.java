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
        add(ModItems.AMETHYST_CANDY.get(), "紫晶糖果");
        add(ModItems.AMETHYST_PICKAXE.get(), "紫水晶镐");

        add(ModBlocks.POLISHED_AMETHYST_BLOCK.get(), "磨制紫水晶块");
        add(ModBlocks.POLISHED_AMETHYST_STAIRS.get(), "磨制紫水晶楼梯");
        add(ModBlocks.POLISHED_AMETHYST_SLAB.get(), "磨制紫水晶台阶");
        add(ModBlocks.POLISHED_AMETHYST_WALL.get(), "磨制紫水晶墙");
        add(ModBlocks.AMETHYST_STAIRS.get(), "紫水晶楼梯");
        add(ModBlocks.AMETHYST_SLAB.get(), "紫水晶台阶");
        add(ModBlocks.AMETHYST_WALL.get(), "紫水晶墙");
        add(ModBlocks.IRON_GRATE.get(), "铁格栅");

        add("itemGroup.crystal_craft.crystal_craft_blocks", "晶饰工艺方块");
        add("itemGroup.crystal_craft.crystal_craft_items", "晶饰工艺物品");

        add("tooltip.crystal_craft.amethyst_pickaxe", "可挖掘紫水晶母岩");

        add("gui.crystal_craft.crystalcarving_table", "晶体雕琢");
    }
}
