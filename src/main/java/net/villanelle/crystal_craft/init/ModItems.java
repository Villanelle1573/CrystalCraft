package net.villanelle.crystal_craft.init;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.item.AmethystPickaxeItem;
import net.villanelle.crystal_craft.item.ModToolMaterials;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CrystalCraft.MOD_ID);

    // 磨制紫水晶
    public static final DeferredItem<Item> POLISHED_AMETHYST = ITEMS.registerSimpleItem("polished_amethyst");

    // 紫晶糖果
    public static final DeferredItem<Item> AMETHYST_CANDY = ITEMS.registerSimpleItem("amethyst_candy",
            () -> new Item.Properties().food(ModFoods.AMETHYST_CANDY));

    // 紫水晶镐
    public static final DeferredItem<Item> AMETHYST_PICKAXE = ITEMS.registerItem("amethyst_pickaxe",
            p -> new AmethystPickaxeItem(ModToolMaterials.AMETHYST, 2, -2.8f, p));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
