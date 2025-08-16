package net.villanelle.crystal_craft;

import net.minecraft.resources.ResourceLocation;
import net.villanelle.crystal_craft.init.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(CrystalCraft.MOD_ID)
public class CrystalCraft {
    public static final String MOD_ID = "crystal_craft";

    public static final Logger LOGGER = LogUtils.getLogger();

    public CrystalCraft(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItemGroups.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipeTypes.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static @NotNull ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
