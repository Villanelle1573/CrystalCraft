package net.villanelle.crystal_craft.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.init.ModBlocks;
import net.villanelle.crystal_craft.init.ModItems;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    protected void buildRecipes() {
        // 紫水晶块和磨制紫水晶做磨制紫水晶块
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_BLOCK)
                .pattern("PP")
                .pattern("PP")
                .define('P', ModItems.POLISHED_AMETHYST)
                .unlockedBy(getHasName(ModItems.POLISHED_AMETHYST), has(ModItems.POLISHED_AMETHYST))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_BLOCK.get(), Blocks.AMETHYST_BLOCK);

        // 紫水晶块和磨制紫水晶块做磨制紫水晶楼梯
        stairBuilder(ModBlocks.POLISHED_AMETHYST_STAIRS, Ingredient.of(ModBlocks.POLISHED_AMETHYST_BLOCK))
                .unlockedBy(getHasName(ModBlocks.POLISHED_AMETHYST_BLOCK), has(ModBlocks.POLISHED_AMETHYST_BLOCK))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_STAIRS.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_STAIRS.get(), Blocks.AMETHYST_BLOCK);

        // 紫水晶块和磨制紫水晶块做磨制紫水晶台阶
        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_SLAB.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_SLAB.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get(), 2);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK, 2);

        // 紫水晶块和磨制紫水晶块做磨制紫水晶墙
        wall(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_WALL.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_WALL.get(), ModBlocks.POLISHED_AMETHYST_BLOCK.get());

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMETHYST_WALL.get(), Blocks.AMETHYST_BLOCK);

        // 紫水晶做紫水晶楼梯
        stairBuilder(ModBlocks.AMETHYST_STAIRS, Ingredient.of(Blocks.AMETHYST_BLOCK))
                .unlockedBy(getHasName(Blocks.AMETHYST_BLOCK), has(Blocks.AMETHYST_BLOCK))
                .save(output);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_STAIRS.get(), Blocks.AMETHYST_BLOCK);

        // 紫水晶做紫水晶台阶
        slab(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK);

        stonecutterResultFromBase( RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_SLAB.get(), Blocks.AMETHYST_BLOCK, 2);

        // 紫水晶做紫水晶墙
        wall(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_WALL.get(), Blocks.AMETHYST_BLOCK);

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_WALL.get(), Blocks.AMETHYST_BLOCK);

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

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, ModBlocks.IRON_GRATE, Blocks.IRON_BLOCK, 4);

        // 紫水晶母岩再生
        shaped(RecipeCategory.MISC, Blocks.BUDDING_AMETHYST, 2)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.AMETHYST_SHARD)
                .define('B', Items.BUDDING_AMETHYST)
                .unlockedBy(getHasName(Items.BUDDING_AMETHYST), has(Items.BUDDING_AMETHYST))
                .save(output, recipeKey("budding_amethyst_from_amethyst_shard"));

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

    // 这里统一改写成模组命名空间。
    @Override
    protected void stonecutterResultFromBase(RecipeCategory category, ItemLike result, ItemLike base, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), category, result, count)
                .unlockedBy(getHasName(base), has(base))
                .save(this.output, recipeKey(getConversionRecipeName(result, base) + "_stonecutting"));
    }

    private static ResourceKey<Recipe<?>> recipeKey(String path) {
        return ResourceKey.create(Registries.RECIPE, CrystalCraft.of(path));
    }

//    public static MultiRegistryBootstrap create() {
//        return new MultiRegistryBootstrap() {
//            @Override
//            public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
//                return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
//            }
//
//            @Override
//            public void run(MultiRegistryBootstrap.BootstrapGetter registries) {
//                new ModRecipesProvider(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
//            }
//        };
//    }
}
