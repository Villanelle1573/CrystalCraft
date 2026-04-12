package net.villanelle.crystal_craft.recipe.chipping;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class ChippingInput implements RecipeInput {
    private final ItemStack ingredient;
    private final ItemStack tool;

    public ChippingInput(ItemStack input, ItemStack tool) {
        this.ingredient = input;
        this.tool = tool;
    }

    @Override
    public ItemStack getItem(int i) {
        if (i == 0) {
            return ingredient;
        } else if (i == 1) {
            return tool;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 2;
    }
}
