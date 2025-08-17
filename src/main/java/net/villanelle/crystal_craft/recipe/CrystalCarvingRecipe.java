package net.villanelle.crystal_craft.recipe;

import com.mojang.serialization.Codec;
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

import java.util.List;
import java.util.Map;

public class CrystalCarvingRecipe implements Recipe<CrystalCarvingRecipe.Input> {
    private final List<String> pattern;
    private final Map<Character, Ingredient> key;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final ResourceLocation id;

    public CrystalCarvingRecipe(ResourceLocation id, List<String> pattern, Map<Character, Ingredient> key, ItemStack result) {
        this.id = id;
        this.pattern = pattern;
        this.key = key;
        this.result = result;

        this.ingredients = NonNullList.withSize(9, Ingredient.EMPTY);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (row < pattern.size() && col < pattern.get(row).length()) {
                    char c = pattern.get(row).charAt(col);
                    if (c != ' ' && key.containsKey(c)) {
                        this.ingredients.set(col + row * 3, key.get(c));
                    }
                }
            }
        }
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
        int maxOffsetX = 3 - getMaxPatternWidth();
        int maxOffsetY = 3 - pattern.size();

        for (int offsetX = 0; offsetX <= maxOffsetX; offsetX++) {
            for (int offsetY = 0; offsetY <= maxOffsetY; offsetY++) {
                if (matchesAtPosition(items, offsetX, offsetY)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getMaxPatternWidth() {
        int maxWidth = 0;
        for (String row : pattern) {
            maxWidth = Math.max(maxWidth, row.length());
        }
        return maxWidth;
    }

    private boolean matchesAtPosition(NonNullList<ItemStack> items, int offsetX, int offsetY) {
        for (int row = 0; row < pattern.size(); row++) {
            for (int col = 0; col < pattern.get(row).length(); col++) {
                char patternChar = pattern.get(row).charAt(col);
                int gridRow = row + offsetY;
                int gridCol = col + offsetX;
                int gridIndex = gridCol + gridRow * 3;

                if (gridRow >= 3 || gridCol >= 3) {
                    return false;
                }

                ItemStack actualItem = items.get(gridIndex);

                if (patternChar == ' ') {
                    if (!actualItem.isEmpty()) {
                        return false;
                    }
                } else if (key.containsKey(patternChar)) {
                    if (!key.get(patternChar).test(actualItem)) {
                        return false;
                    }
                }
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boolean inPatternArea = (row >= offsetY && row < offsetY + pattern.size() &&
                        col >= offsetX && col < offsetX + getPatternWidthAtRow(row - offsetY));

                if (!inPatternArea) {
                    int gridIndex = col + row * 3;
                    if (!items.get(gridIndex).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int getPatternWidthAtRow(int row) {
        if (row >= 0 && row < pattern.size()) {
            return pattern.get(row).length();
        }
        return 0;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public List<String> getPattern() {
        return pattern;
    }

    public Map<Character, Ingredient> getKey() {
        return key;
    }

    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean checkMatchAtPosition(NonNullList<ItemStack> items, int offsetX, int offsetY) {
        return matchesAtPosition(items, offsetX, offsetY);
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
        private static final MapCodec<CrystalCarvingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.listOf().fieldOf("pattern").forGetter(CrystalCarvingRecipe::getPattern),
                Codec.unboundedMap(Codec.STRING, Ingredient.CODEC).xmap(
                        map ->{
                            Map<Character, Ingredient> result = new java.util.HashMap<>();
                            for (Map.Entry<String, Ingredient> entry : map.entrySet()) {
                                if (entry.getKey().length() == 1) {
                                    result.put(entry.getKey().charAt(0), entry.getValue());
                                }
                            }
                            return result;
                        },
                        map -> {
                            Map<String, Ingredient> result = new java.util.HashMap<>();
                            for (Map.Entry<Character, Ingredient> entry : map.entrySet()) {
                                result.put(String.valueOf(entry.getKey()), entry.getValue());
                            }
                            return result;
                        }
                ).fieldOf("key").forGetter(CrystalCarvingRecipe::getKey),
                        ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
                ).apply(instance, (pattern, key, result) ->
                        new CrystalCarvingRecipe(ResourceLocation.parse("crystal_craft:crystal_carving"), pattern, key, result)));

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
            buf.writeCollection(recipe.pattern, (b, s) -> b.writeUtf(s));
            buf.writeInt(recipe.key.size());
            for (Map.Entry<Character, Ingredient> entry : recipe.key.entrySet()) {
                buf.writeChar(entry.getKey());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, entry.getValue());
            }
            ItemStack.STREAM_CODEC.encode(buf, recipe.result);
        }

        private static CrystalCarvingRecipe decode(RegistryFriendlyByteBuf buf) {
            List<String> pattern = buf.readList(b -> b.readUtf(32767));
            int keySize = buf.readInt();
            Map<Character, Ingredient> key = new java.util.HashMap<>();
            for (int i = 0; i < keySize; i++) {
                char c = buf.readChar();
                Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                key.put(c, ingredient);
            }
            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
            return new CrystalCarvingRecipe(ResourceLocation.parse("crystal_craft:crystal_carving"), pattern, key, result);
        }
    }
}
