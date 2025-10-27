package net.villanelle.crystal_craft.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WaterloggedTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class IronGrateBlock extends WaterloggedTransparentBlock {
    public IronGrateBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(4f, 6f)
                .noOcclusion()
                .sound(SoundType.COPPER_GRATE)
                .requiresCorrectToolForDrops());
    }
}