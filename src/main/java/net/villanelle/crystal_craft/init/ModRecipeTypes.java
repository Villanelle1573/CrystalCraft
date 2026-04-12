package net.villanelle.crystal_craft.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.villanelle.crystal_craft.CrystalCraft;
import net.villanelle.crystal_craft.recipe.chipping.ChippingRecipe;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, CrystalCraft.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CrystalCraft.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ChippingRecipe>> CHIPPING =
            TYPES.register("chipping", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "chipping";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ChippingRecipe>> CHIPPING_SERIALIZER =
            SERIALIZERS.register("chipping", () -> ChippingRecipe.SERIALIZER);

    public static void register(IEventBus eventBus) {
        TYPES.register(eventBus);
        SERIALIZERS.register(eventBus);
    }
}
