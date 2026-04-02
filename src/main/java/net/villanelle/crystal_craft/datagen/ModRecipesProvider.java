package net.villanelle.crystal_craft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.villanelle.crystal_craft.init.ModBlocks;
import net.villanelle.crystal_craft.init.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider {
    protected ModRecipesProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        shaped(RecipeCategory.MISC, ModItems.POLISHED_AMETHYST, 2)
                .pattern("A ")
                .pattern(" A")
                .define('A', Items.AMETHYST_SHARD)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_BLOCK)
                .pattern("PP")
                .pattern("PP")
                .define('P', ModItems.POLISHED_AMETHYST)
                .unlockedBy(getHasName(ModItems.POLISHED_AMETHYST), has(ModItems.POLISHED_AMETHYST))
                .save(output);

        stairBuilder(ModBlocks.POLISHED_AMETHYST_STAIRS, Ingredient.of(ModBlocks.POLISHED_AMETHYST_BLOCK))
                .unlockedBy(getHasName(ModBlocks.POLISHED_AMETHYST_BLOCK), has(ModBlocks.POLISHED_AMETHYST_BLOCK))
                .save(output);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_AMETHYST_STAIRS.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());

        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_SLAB.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_AMETHYST_SLAB.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get(), 2);

        wall(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_WALL.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_AMETHYST_WALL.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());

        stairBuilder(ModBlocks.AMETHYST_STAIRS, Ingredient.of(Blocks.AMETHYST_BLOCK))
                .unlockedBy(getHasName(Blocks.AMETHYST_BLOCK), has(Blocks.AMETHYST_BLOCK))
                .save(output);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AMETHYST_STAIRS.get(), Blocks.AMETHYST_BLOCK);

        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase( RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK, 2);

        wall(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_WALL.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AMETHYST_WALL.get(), Blocks.AMETHYST_BLOCK);

        shapeless(RecipeCategory.FOOD, ModItems.AMETHYST_CANDY, 3)
                .requires(Items.SUGAR)
                .requires(Items.AMETHYST_SHARD)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(output);

        shaped(RecipeCategory.MISC, ModItems.AMETHYST_PICKAXE)
                .pattern("APA")
                .pattern(" S ")
                .pattern(" S ")
                .define('A', Items.AMETHYST_SHARD)
                .define('P', ModItems.POLISHED_AMETHYST)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.POLISHED_AMETHYST), has(ModItems.POLISHED_AMETHYST))
                .save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.IRON_GRATE, 4)
                .pattern(" I ")
                .pattern("I I")
                .pattern(" I ")
                .define('I', Items.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_BLOCK), has(Items.IRON_BLOCK))
                .save(output);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.IRON_GRATE, Blocks.IRON_BLOCK, 4);

        shaped(RecipeCategory.MISC, Blocks.BUDDING_AMETHYST, 2)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.AMETHYST_SHARD)
                .define('B', Items.BUDDING_AMETHYST)
                .unlockedBy(getHasName(Items.BUDDING_AMETHYST), has(Items.BUDDING_AMETHYST))
                .save(output);
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipesProvider(registries, output);
        }

        @Override
        public String getName() {
            return "recipe gen";
        }
    }
}
