package net.villanelle.crystal_craft.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.villanelle.crystal_craft.CrystalCraft;

public class ModItemTags {
    public static final TagKey<Item> AMETHYST_TOOL_MATERIALS = bind("amethyst_tool_materials");

    private static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(CrystalCraft.MOD_ID, name));
    }
}
