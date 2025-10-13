package net.villanelle.crystal_craft.init;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.item.AmethystPickaxeItem;
import net.villanelle.crystal_craft.item.ModFoods;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CrystalCraft.MOD_ID);

    public static final DeferredItem<Item> POLISHED_AMETHYST = ITEMS.register("polished_amethyst",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AMETHYST_CANDY = ITEMS.register("amethyst_candy",
            () -> new Item(new Item.Properties().food(ModFoods.AMETHYST_CANDY)));
    public static final DeferredItem<Item> AMETHYST_PICKAXE = ITEMS.register("amethyst_pickaxe",
            () -> new AmethystPickaxeItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
