package net.villanelle.crystal_craft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.villanelle.crystal_craft.init.ModBlocks;
import net.villanelle.crystal_craft.init.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CRYSTAL_CARVING_TABLE)
                .pattern(" L ")
                .pattern("APA")
                .pattern("PPP")
                .define('L', Items.LEATHER)
                .define('A', Items.AMETHYST_SHARD)
                .define('P', Items.POLISHED_DEEPSLATE)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_BLOCK)
                .pattern("PP")
                .pattern("PP")
                .define('P', ModItems.POLISHED_AMETHYST)
                .unlockedBy(getHasName(ModItems.POLISHED_AMETHYST), has(ModItems.POLISHED_AMETHYST))
                .save(recipeOutput);

        stairBuilder(ModBlocks.POLISHED_AMETHYST_STAIRS, Ingredient.of(ModBlocks.POLISHED_AMETHYST_BLOCK))
                .unlockedBy(getHasName(ModBlocks.POLISHED_AMETHYST_BLOCK), has(ModBlocks.POLISHED_AMETHYST_BLOCK))
                .save(recipeOutput);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_AMETHYST_STAIRS.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());

        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_SLAB.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_AMETHYST_SLAB.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get(), 2);

        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_WALL.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_AMETHYST_WALL.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());

        stairBuilder(ModBlocks.AMETHYST_STAIRS, Ingredient.of(Blocks.AMETHYST_BLOCK))
                .unlockedBy(getHasName(Blocks.AMETHYST_BLOCK), has(Blocks.AMETHYST_BLOCK))
                .save(recipeOutput);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AMETHYST_STAIRS.get(), Blocks.AMETHYST_BLOCK);

        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK, 2);

        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_WALL.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AMETHYST_WALL.get(), Blocks.AMETHYST_BLOCK);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.AMETHYST_CANDY, 3)
                .requires(Items.SUGAR)
                .requires(Items.AMETHYST_SHARD)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMETHYST_PICKAXE)
                .pattern("APA")
                .pattern(" S ")
                .pattern(" S ")
                .define('A', Items.AMETHYST_SHARD)
                .define('P', ModItems.POLISHED_AMETHYST)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.POLISHED_AMETHYST), has(ModItems.POLISHED_AMETHYST))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.IRON_GRATE, 4)
                .pattern(" I ")
                .pattern("I I")
                .pattern(" I ")
                .define('I', Items.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_BLOCK), has(Items.IRON_BLOCK))
                .save(recipeOutput);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.IRON_GRATE, Blocks.IRON_BLOCK, 4);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BUDDING_AMETHYST, 2)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.AMETHYST_SHARD)
                .define('B', Items.BUDDING_AMETHYST)
                .unlockedBy(getHasName(Items.BUDDING_AMETHYST), has(Items.BUDDING_AMETHYST))
                .save(recipeOutput);
    }
}
