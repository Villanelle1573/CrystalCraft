package net.villanelle.crystal_craft.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import net.villanelle.crystal_craft.init.ModBlocks;
import net.villanelle.crystal_craft.init.ModItems;
import net.villanelle.crystal_craft.recipe.chipping.ChippingRecipe;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModRecipesProvider extends RecipeProvider {
    private final HolderLookup.Provider registries;

    protected ModRecipesProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
        this.registries = registries;
    }

    @Override
    protected void buildRecipes() {
        // 磨制紫水晶做磨制紫水晶块
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_BLOCK)
                .pattern("PP")
                .pattern("PP")
                .define('P', ModItems.POLISHED_AMETHYST)
                .unlockedBy(getHasName(ModItems.POLISHED_AMETHYST), has(ModItems.POLISHED_AMETHYST))
                .save(output);

        // 磨制紫水晶块做磨制紫水晶楼梯
        stairBuilder(ModBlocks.POLISHED_AMETHYST_STAIRS, Ingredient.of(ModBlocks.POLISHED_AMETHYST_BLOCK))
                .unlockedBy(getHasName(ModBlocks.POLISHED_AMETHYST_BLOCK), has(ModBlocks.POLISHED_AMETHYST_BLOCK))
                .save(output);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_AMETHYST_STAIRS.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());

        // 磨制紫水晶块做磨制紫水晶台阶
        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_SLAB.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_AMETHYST_SLAB.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get(), 2);

        // 磨制紫水晶块做磨制紫水晶墙
        wall(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_WALL.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.POLISHED_AMETHYST_WALL.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());

        // 紫水晶做紫水晶楼梯
        stairBuilder(ModBlocks.AMETHYST_STAIRS, Ingredient.of(Blocks.AMETHYST_BLOCK))
                .unlockedBy(getHasName(Blocks.AMETHYST_BLOCK), has(Blocks.AMETHYST_BLOCK))
                .save(output);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AMETHYST_STAIRS.get(), Blocks.AMETHYST_BLOCK);

        // 紫水晶做紫水晶台阶
        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase( RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK, 2);

        // 紫水晶做紫水晶墙
        wall(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_WALL.get(), Blocks.AMETHYST_BLOCK);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.AMETHYST_WALL.get(), Blocks.AMETHYST_BLOCK);

        // 紫水晶糖
        shapeless(RecipeCategory.FOOD, ModItems.AMETHYST_CANDY, 3)
                .requires(Items.SUGAR)
                .requires(Items.AMETHYST_SHARD)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(output);

        // 紫水晶镐
        shaped(RecipeCategory.TOOLS, ModItems.AMETHYST_PICKAXE)
                .pattern("APA")
                .pattern(" S ")
                .pattern(" S ")
                .define('A', Items.AMETHYST_SHARD)
                .define('P', ModItems.POLISHED_AMETHYST)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.POLISHED_AMETHYST), has(ModItems.POLISHED_AMETHYST))
                .save(output);

        // 铁格栅
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.IRON_GRATE, 4)
                .pattern(" I ")
                .pattern("I I")
                .pattern(" I ")
                .define('I', Items.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_BLOCK), has(Items.IRON_BLOCK))
                .save(output);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.IRON_GRATE, Blocks.IRON_BLOCK, 4);

        // 紫水晶母岩再生
        shaped(RecipeCategory.MISC, Blocks.BUDDING_AMETHYST, 2)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.AMETHYST_SHARD)
                .define('B', Items.BUDDING_AMETHYST)
                .unlockedBy(getHasName(Items.BUDDING_AMETHYST), has(Items.BUDDING_AMETHYST))
                .save(output);

        // 雕研台
        shaped(RecipeCategory.MISC, ModBlocks.CHIPPING_TABLE)
                .pattern("LL")
                .pattern("PP")
                .define('L', Items.LEATHER)
                .define('P', Items.POLISHED_DEEPSLATE)
                .unlockedBy(getHasName(Items.POLISHED_DEEPSLATE), has(Items.POLISHED_DEEPSLATE))
                .save(output);

        // 磨轮
        shaped(RecipeCategory.TOOLS, ModItems.GRINDING_WHEEL)
                .pattern("SGS")
                .define('S', Items.STICK)
                .define('G', Items.GRANITE)
                .unlockedBy(getHasName(Items.GRANITE), has(Items.GRANITE))
                .save(output);

        // 线锯
        shaped(RecipeCategory.TOOLS, ModItems.WIRE_SAW)
                .pattern("SII")
                .pattern("SSS")
                .define('S', Items.STICK)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
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
