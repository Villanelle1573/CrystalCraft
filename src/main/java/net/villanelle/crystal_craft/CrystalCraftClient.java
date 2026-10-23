package net.villanelle.crystal_craft;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.villanelle.crystal_craft.client.gui.screen.ChippingScreen;

import static net.villanelle.crystal_craft.init.ModMenuTypes.CHIPPING_MENU;

// 只在物理客户端加载，这里可以安全地引用客户端类。
@Mod(value = CrystalCraft.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = CrystalCraft.MOD_ID, value = Dist.CLIENT)
public class CrystalCraftClient {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(CHIPPING_MENU.get(), ChippingScreen::new);
    }
}
