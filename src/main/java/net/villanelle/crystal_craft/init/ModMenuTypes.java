package net.villanelle.crystal_craft.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.inventory.CrystalCarvingMenu;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, CrystalCraft.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<CrystalCarvingMenu>> CRYSTAL_CARVING_MENU = 
            MENU_TYPES.register("crystal_carving_menu", () -> 
                    new MenuType<>((containerId, inventory) -> new CrystalCarvingMenu(ModMenuTypes.CRYSTAL_CARVING_MENU.get(), containerId, inventory), null));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
