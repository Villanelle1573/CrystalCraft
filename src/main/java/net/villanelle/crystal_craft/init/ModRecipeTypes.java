package net.villanelle.crystal_craft.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.recipe.CrystalCarvingRecipe;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, CrystalCraft.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, CrystalCraft.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<CrystalCarvingRecipe>> CRYSTAL_CARVING_TYPE =
            RECIPE_TYPES.register("crystal_carving", () -> new RecipeType<>() {});

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrystalCarvingRecipe>> CRYSTAL_CARVING_SERIALIZER =
            RECIPE_SERIALIZERS.register("crystal_carving", CrystalCarvingRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
