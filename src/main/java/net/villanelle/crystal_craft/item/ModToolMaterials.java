package net.villanelle.crystal_craft.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;
import net.villanelle.crystal_craft.init.ModItemTags;


public class ModToolMaterials {
    public static final ToolMaterial AMETHYST = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            190,
            5.0f,
            1.0f,
            20,
            ModItemTags.AMETHYST_TOOL_MATERIALS);
}
