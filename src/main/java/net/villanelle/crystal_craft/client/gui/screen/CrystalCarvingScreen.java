package net.villanelle.crystal_craft.client.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.inventory.CrystalCarvingMenu;

public class CrystalCarvingScreen extends AbstractContainerScreen<CrystalCarvingMenu> {
    private static final ResourceLocation CONTAINER_LOCATION = CrystalCraft.of("textures/gui/container/crystalcarving/background.png");

    public CrystalCarvingScreen(CrystalCarvingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        // 根据背景图设置GUI尺寸
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94; // 调整玩家背包标签位置
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
