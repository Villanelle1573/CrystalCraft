package net.villanelle.crystal_craft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
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

        // 紫水晶镐的修复材料（ToolMaterial.AMETHYST 的 repairItems 指向这个标签）
        tag(ModItemTags.AMETHYST_TOOL_MATERIALS)
                .add(Items.AMETHYST_SHARD.builtInRegistryHolder().key());

        tag(ItemTags.PICKAXES)
                .add(ModItems.getResourceKey(ModItems.AMETHYST_PICKAXE.get()));

        // 通用「挖掘类工具」标签，c:tools 会收录它
        tag(Tags.Items.MINING_TOOL_TOOLS)
                .add(ModItems.getResourceKey(ModItems.AMETHYST_PICKAXE.get()));

        // 通用「糖果」食物标签
        tag(Tags.Items.FOODS_CANDY)
                .add(ModItems.getResourceKey(ModItems.AMETHYST_CANDY.get()));
    }
}
