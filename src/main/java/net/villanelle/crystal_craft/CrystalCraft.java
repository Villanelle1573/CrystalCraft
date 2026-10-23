package net.villanelle.crystal_craft;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.villanelle.crystal_craft.init.ModBlocks;
import net.villanelle.crystal_craft.init.ModItemGroups;
import net.villanelle.crystal_craft.init.ModItems;
import net.villanelle.crystal_craft.init.ModMenuTypes;
import net.villanelle.crystal_craft.init.ModRecipeTypes;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(CrystalCraft.MOD_ID)
public class CrystalCraft {
    public static final String MOD_ID = "crystal_craft";

    public static final Logger LOGGER = LogUtils.getLogger();

    public CrystalCraft(IEventBus modEventBus) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModItemGroups.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipeTypes.register(modEventBus);
    }

    /// {@return 本模组命名空间下的 `Identifier`}
    public static @NotNull Identifier of(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
