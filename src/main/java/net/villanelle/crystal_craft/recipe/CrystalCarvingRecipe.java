package net.villanelle.crystal_craft.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.villanelle.crystal_craft.init.ModRecipeTypes;

public class CrystalCarvingRecipe implements Recipe<CrystalCarvingRecipe.Input> {
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final ResourceLocation id;

    public CrystalCarvingRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result) {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.CRYSTAL_CARVING_TYPE.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.CRYSTAL_CARVING_SERIALIZER.get();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public ItemStack assemble(Input input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean matches(Input input, Level level) {
        return matches(input.items(), level);
    }

    public boolean matches(NonNullList<ItemStack> items, Level level) {
        if (items.size() != ingredients.size()) {
            return false;
        }

        for (int i = 0; i < ingredients.size(); i++) {
            if (!ingredients.get(i).test(items.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public record Input(NonNullList<ItemStack> items) implements RecipeInput {
        @Override
        public ItemStack getItem(int index) {
            return items.get(index);
        }

        @Override
        public int size() {
            return items.size();
        }
    }

    public static class Serializer implements RecipeSerializer<CrystalCarvingRecipe> {
        private static final MapCodec<CrystalCarvingRecipe> CODEC = RecordCodecBuilder.mapCodec(ins -> ins.group(
            Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(CrystalCarvingRecipe::getIngredients),
            ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
        ).apply(ins, (ingredients, result) -> {
            NonNullList<Ingredient> ingredientList = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                ingredientList.set(i, ingredients.get(i));
            }
            return new CrystalCarvingRecipe(ResourceLocation.parse("crystal_craft:crystal_carving"), ingredientList, result);
        }));

        public static final StreamCodec<RegistryFriendlyByteBuf, CrystalCarvingRecipe> STREAM_CODEC = StreamCodec.of(
            Serializer::encode, Serializer::decode
        );

        @Override
        public MapCodec<CrystalCarvingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrystalCarvingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static void encode(RegistryFriendlyByteBuf buf, CrystalCarvingRecipe recipe) {
            buf.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            ItemStack.STREAM_CODEC.encode(buf, recipe.result);
        }

        private static CrystalCarvingRecipe decode(RegistryFriendlyByteBuf buf) {
            int size = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
            ingredients.replaceAll(i -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
            return new CrystalCarvingRecipe(ResourceLocation.parse("crystal_craft:crystal_carving"), ingredients, result);
        }
    }
}
