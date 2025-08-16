package net.villanelle.crystal_craft.init;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.villanelle.crystal_craft.CrystalCraft;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CrystalCraft.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
