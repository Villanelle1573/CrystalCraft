package net.villanelle.crystal_craft.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.List;
import java.util.function.Consumer;

public class AmethystPickaxeItem extends Item {

    private final ToolMaterial material;
    private final float attackDamage;
    private final float attackSpeed;

    public AmethystPickaxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.pickaxe(material, attackDamage, attackSpeed));
        this.material = material;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getBlock() == Blocks.BUDDING_AMETHYST) {
            if (!level.isClientSide()) {
                ItemEntity itemEntity = new ItemEntity(
                        level,
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        new ItemStack(Blocks.BUDDING_AMETHYST)
                );
                level.addFreshEntity(itemEntity);
            }
            level.removeBlock(pos, false);
            stack.hurtAndBreak(1, miner, EquipmentSlot.MAINHAND);
            return true;
        }
        return super.mineBlock(stack, level, state, pos, miner);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable("tooltip.crystal_craft.amethyst_pickaxe")
                .withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public ToolMaterial getMaterial() {
        return material;
    }
}