package net.villanelle.crystal_craft.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class AmethystPickaxeItem extends PickaxeItem {
    public AmethystPickaxeItem(Properties properties) {
        super(ModTiers.AMETHYST, properties.attributes(PickaxeItem.createAttributes(ModTiers.AMETHYST, 2, -2.8f)));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        boolean result = super.mineBlock(stack, level, state, pos, miningEntity);
        if (state.is(Blocks.BUDDING_AMETHYST)) {
            if (!level.isClientSide && result) {
                Block.popResource(level, pos, new ItemStack(Blocks.BUDDING_AMETHYST));
            }
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component tooltip = Component.translatable("tooltip.crystal_craft.amethyst_pickaxe")
                .withStyle(ChatFormatting.LIGHT_PURPLE);
        tooltipComponents.add(tooltip);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
