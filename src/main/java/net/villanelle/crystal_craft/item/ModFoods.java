package net.villanelle.crystal_craft.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties AMETHYST_CANDY = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(1.0f) // 饱和度为 2 * 1.0 * 2 = 4.0
            .fast()
            .build();
}
