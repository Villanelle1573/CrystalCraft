package net.villanelle.crystal_craft.block;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.villanelle.crystal_craft.inventory.CrystalCarvingMenu;
import net.villanelle.crystal_craft.init.ModMenuTypes;

import javax.annotation.Nullable;

public class CrystalCarvingTable extends Block {
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    
    public CrystalCarvingTable() {
        super(BlockBehaviour.Properties.of()
                .strength(4f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.POLISHED_DEEPSLATE));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        LOGGER.info("CrystalCarvingTable: useWithoutItem called");
        if (level.isClientSide()) {
            LOGGER.info("CrystalCarvingTable: Client side - returning SUCCESS");
            return InteractionResult.SUCCESS;
        }

        LOGGER.info("CrystalCarvingTable: Server side - getting menu provider");
        MenuProvider menuProvider = state.getMenuProvider(level, pos);
        if (menuProvider != null) {
            LOGGER.info("CrystalCarvingTable: Menu provider found - opening menu");
            player.openMenu(menuProvider);
            return InteractionResult.CONSUME;
        }
        LOGGER.info("CrystalCarvingTable: No menu provider found - returning PASS");
        return InteractionResult.PASS;
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
                (containerId, inventory, player) ->
                        new CrystalCarvingMenu(ModMenuTypes.CRYSTAL_CARVING_MENU.get(), containerId, inventory, ContainerLevelAccess.create(level, pos)),
                Component.translatable("gui.crystal_craft.crystalcarving_table")
        );
    }
}
