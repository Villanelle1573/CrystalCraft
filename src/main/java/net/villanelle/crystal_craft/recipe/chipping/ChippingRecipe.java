package net.villanelle.crystal_craft.recipe.chipping;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.villanelle.crystal_craft.init.ModRecipeTypes;

public record ChippingRecipe(Ingredient ingredient, Ingredient tool, Identifier resultId, int count) implements Recipe<ChippingInput> {

    public ItemStack getResultStack() {
        var itemOpt = BuiltInRegistries.ITEM.get(resultId);
        return itemOpt.map(holder -> new ItemStack(holder.value(), count)).orElse(ItemStack.EMPTY);
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredient);
        list.add(tool);
        return list;
    }

    @Override
    public boolean matches(ChippingInput input, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return ingredient.test(input.getItem(0)) && tool.test(input.getItem(1));
    }

    @Override
    public ItemStack assemble(ChippingInput input) {
        return getResultStack();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeType<? extends Recipe<ChippingInput>> getType() {
        return ModRecipeTypes.CHIPPING.get();
    }

    @Override
    public RecipeSerializer<? extends Recipe<ChippingInput>> getSerializer() {
        return ModRecipeTypes.CHIPPING_SERIALIZER.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(ingredient);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public static final RecipeSerializer<ChippingRecipe> SERIALIZER = new RecipeSerializer<>(
            RecordCodecBuilder.mapCodec(inst -> inst.group(
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(ChippingRecipe::ingredient),
                    Ingredient.CODEC.fieldOf("tool").forGetter(ChippingRecipe::tool),
                    Identifier.CODEC.fieldOf("result").forGetter(ChippingRecipe::resultId),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(ChippingRecipe::count)
            ).apply(inst, ChippingRecipe::new)),
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, ChippingRecipe::ingredient,
                    Ingredient.CONTENTS_STREAM_CODEC, ChippingRecipe::tool,
                    Identifier.STREAM_CODEC, ChippingRecipe::resultId,
                    StreamCodec.of((buf, val) -> buf.writeVarInt(val), buf -> buf.readVarInt()), ChippingRecipe::count,
                    ChippingRecipe::new));
}
