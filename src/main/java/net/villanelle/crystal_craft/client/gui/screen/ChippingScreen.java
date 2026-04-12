package net.villanelle.crystal_craft.client.gui.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.inventory.ChippingMenu;

public class ChippingScreen extends AbstractContainerScreen<ChippingMenu> {
    private static final Identifier CHIPPING_GUI = Identifier.fromNamespaceAndPath(CrystalCraft.MOD_ID, "textures/gui/container/chipping_container.png");

    public ChippingScreen(ChippingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.titleLabelX = 8;
        this.titleLabelY = 5;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int xo = (this.width - this.imageWidth) / 2;
        int yo = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, CHIPPING_GUI, xo, yo, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }
}
