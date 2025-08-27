package net.villanelle.crystal_craft.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.init.ModBlocks;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CrystalCraft.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockModelBuilder modelCrystalCarvingTable = models().getBuilder("crystalcarving_table")
                .parent(models().getExistingFile(mcLoc("block/block")))
                .texture("particle", modLoc("block/crystalcarving_table_bottom"))
                .texture("bottom", modLoc("block/crystalcarving_table_bottom"))
                .texture("top", modLoc("block/crystalcarving_table_top"))
                .texture("side", modLoc("block/crystalcarving_table_side"))
                .element()
                .from(0, 0, 0)
                .to(16, 12, 16)
                .face(Direction.DOWN).texture("#bottom").cullface(Direction.DOWN).end()
                .face(Direction.UP).texture("#top").end()
                .face(Direction.NORTH).texture("#side").cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).texture("#side").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).texture("#side").cullface(Direction.WEST).end()
                .face(Direction.EAST).texture("#side").cullface(Direction.EAST).end()
                .end();

        simpleBlockWithItem(ModBlocks.CRYSTAL_CARVING_TABLE.get(), modelCrystalCarvingTable);
    }
}