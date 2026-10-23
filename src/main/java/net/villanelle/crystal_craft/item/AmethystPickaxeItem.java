package net.villanelle.crystal_craft.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

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
        // 紫水晶母岩原版挖不出掉落物，这里额外生成一个；方块本身仍然交给原版移除，
        // 这样挖掘统计、饥饿消耗和战利品表都走正常流程（母岩战利品表为空，不会双倍掉落）。
        if (state.getBlock() == Blocks.BUDDING_AMETHYST && !level.isClientSide()) {
            ItemEntity itemEntity = new ItemEntity(
                    level,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    new ItemStack(Blocks.BUDDING_AMETHYST)
            );
            level.addFreshEntity(itemEntity);
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
