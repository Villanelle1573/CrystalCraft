package net.villanelle.crystal_craft.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.villanelle.crystal_craft.init.ModBlocks;
import net.villanelle.crystal_craft.init.ModRecipeTypes;
import net.villanelle.crystal_craft.recipe.CrystalCarvingRecipe;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

// 创建一个专门的结果槽类，禁止放置物品
class CrystalCarvingResultSlot extends Slot {
    private final CrystalCarvingMenu menu;

    public CrystalCarvingResultSlot(CrystalCarvingMenu menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public boolean mayPickup(Player player) {
        return !getItem().isEmpty();
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        menu.handleCrafting(player);
        super.onTake(player, stack);
    }
}

public class CrystalCarvingMenu extends AbstractContainerMenu {
    public static final int CRAFT_SLOT_START = 0;
    public static final int CRAFT_SLOT_END = 9;
    public static final int RESULT_SLOT = 9;
    public static final int INV_SLOT_START = 10;
    public static final int INV_SLOT_END = 37;
    public static final int USE_ROW_SLOT_START = 37;
    public static final int USE_ROW_SLOT_END = 46;

    private final CraftingContainer craftingContainer = new TransientCraftingContainer(this, 3, 3);
    private final ResultContainer resultContainer = new ResultContainer();
    private final ContainerLevelAccess access;
    private final Player player;

    public CrystalCarvingMenu(@Nullable MenuType<?> menuType, int containerId, Inventory inventory) {
        this(menuType, containerId, inventory, ContainerLevelAccess.NULL);
    }

    public CrystalCarvingMenu(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(menuType, containerId);
        this.access = access;
        this.player = inventory.player;

        // 3x3合成网格 (索引 0-8)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                addSlot(new Slot(craftingContainer, col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        // 结果槽 (索引 9)
        addSlot(new CrystalCarvingResultSlot(this, resultContainer, 0, 124, 35));

        // 玩家背包 (索引 10-45)
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    public int getResultSlotIndex() {
        return RESULT_SLOT;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        if (index == RESULT_SLOT) {
            // 处理从结果槽取出物品（包括批量合成）
            return handleResultSlotQuickMove(player, sourceSlot);
        } else if (index >= CRAFT_SLOT_START && index < CRAFT_SLOT_END) {
            // 从合成槽移动到玩家背包
            ItemStack sourceStack = sourceSlot.getItem();
            ItemStack resultStack = sourceStack.copy();
            if (!moveItemStackTo(resultStack, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                return ItemStack.EMPTY;
            }
            if (resultStack.isEmpty()) {
                sourceSlot.setByPlayer(ItemStack.EMPTY);
            } else {
                sourceSlot.setChanged();
            }
            return resultStack;
        } else if (index >= INV_SLOT_START && index < USE_ROW_SLOT_END) {
            // 从玩家背包移动到合成槽
            ItemStack sourceStack = sourceSlot.getItem();
            ItemStack resultStack = sourceStack.copy();
            if (!moveItemStackTo(resultStack, CRAFT_SLOT_START, CRAFT_SLOT_END, false)) {
                if (index < INV_SLOT_END) {
                    // 从背包移动到快捷栏
                    if (!moveItemStackTo(resultStack, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // 从快捷栏移动到背包
                    if (!moveItemStackTo(resultStack, INV_SLOT_START, INV_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            if (resultStack.isEmpty()) {
                sourceSlot.setByPlayer(ItemStack.EMPTY);
            } else {
                sourceSlot.setChanged();
            }
            return resultStack;
        }

        return ItemStack.EMPTY;
    }

    private ItemStack handleResultSlotQuickMove(Player player, Slot resultSlot) {
        ItemStack resultStack = resultSlot.getItem();
        if (resultStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        // 计算可以合成的最大次数
        int maxCraftTimes = calculateMaxCraftTimes();

        if (maxCraftTimes <= 0) {
            return ItemStack.EMPTY;
        }

        // 创建总的结果物品堆
        ItemStack totalResult = resultStack.copy();
        totalResult.setCount(resultStack.getCount() * maxCraftTimes);

        // 执行多次合成
        for (int i = 0; i < maxCraftTimes; i++) {
            handleCrafting(player);
        }

        // 将总结果移动到玩家背包
        if (!moveItemStackTo(totalResult, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
            return ItemStack.EMPTY;
        }

        resultSlot.onTake(player, resultStack);
        return resultStack;
    }

    private int calculateMaxCraftTimes() {
        // 获取当前输入
        CrystalCarvingRecipe.Input input = getInputFromCraftingContainer();
        NonNullList<ItemStack> items = input.items();

        Level level = player.level();
        List<RecipeHolder<CrystalCarvingRecipe>> recipes = level.getRecipeManager()
                .getRecipesFor(ModRecipeTypes.CRYSTAL_CARVING_TYPE.get(), input, level);

        for (RecipeHolder<CrystalCarvingRecipe> recipeHolder : recipes) {
            CrystalCarvingRecipe recipe = recipeHolder.value();
            if (recipe.matches(input, level)) {
                // 计算可以合成多少次
                int maxOffsetX = 3 - recipe.getPattern().get(0).length();
                int maxOffsetY = 3 - recipe.getPattern().size();

                for (int offsetX = 0; offsetX <= maxOffsetX; offsetX++) {
                    for (int offsetY = 0; offsetY <= maxOffsetY; offsetY++) {
                        if (recipe.checkMatchAtPosition(items, offsetX, offsetY)) {
                            // 计算这个位置可以合成多少次
                            return calculateMaxCraftTimesAtPosition(offsetX, offsetY, recipe);
                        }
                    }
                }
            }
        }

        return 0;
    }

    private int calculateMaxCraftTimesAtPosition(int offsetX, int offsetY, CrystalCarvingRecipe recipe) {
        List<String> pattern = recipe.getPattern();
        Map<Character, Ingredient> key = recipe.getKey();

        // 计算可以合成多少次
        int minCraftTimes = Integer.MAX_VALUE;

        for (int row = 0; row < pattern.size(); row++) {
            for (int col = 0; col < pattern.get(row).length(); col++) {
                char patternChar = pattern.get(row).charAt(col);
                if (patternChar != ' ' && key.containsKey(patternChar)) {
                    int gridRow = row + offsetY;
                    int gridCol = col + offsetX;
                    int slotIndex = gridCol + gridRow * 3;

                    // 检查这个槽位的材料数量
                    Slot slot = slots.get(slotIndex);
                    if (slot.hasItem()) {
                        ItemStack stack = slot.getItem();
                        int craftTimes = stack.getCount();
                        minCraftTimes = Math.min(minCraftTimes, craftTimes);
                    } else {
                        return 0; // 如果需要材料但没有材料，则无法合成
                    }
                }
            }
        }

        return Math.max(minCraftTimes, 0);
    }

    protected void handleCrafting(Player player) {
        // 获取当前输入
        CrystalCarvingRecipe.Input input = getInputFromCraftingContainer();
        NonNullList<ItemStack> items = input.items();

        // 查找匹配的配方来确定哪些槽位需要消耗
        Level level = player.level();
        List<RecipeHolder<CrystalCarvingRecipe>> recipes = level.getRecipeManager()
                .getRecipesFor(ModRecipeTypes.CRYSTAL_CARVING_TYPE.get(), input, level);

        // 找到实际匹配的配方和位置
        for (RecipeHolder<CrystalCarvingRecipe> recipeHolder : recipes) {
            CrystalCarvingRecipe recipe = recipeHolder.value();
            if (recipe.matches(input, level)) {
                // 找到实际匹配的位置
                int maxOffsetX = 3 - recipe.getPattern().getFirst().length();
                int maxOffsetY = 3 - recipe.getPattern().size();

                for (int offsetX = 0; offsetX <= maxOffsetX; offsetX++) {
                    for (int offsetY = 0; offsetY <= maxOffsetY; offsetY++) {
                        if (recipe.checkMatchAtPosition(items, offsetX, offsetY)) {
                            // 消耗这个位置的材料
                            consumeIngredientsAtPosition(offsetX, offsetY, recipe);
                            break;
                        }
                    }
                }
                break;
            }
        }

        // 更新配方结果
        slotsChanged(craftingContainer);
    }

    private void consumeIngredientsAtPosition(int offsetX, int offsetY, CrystalCarvingRecipe recipe) {
        List<String> pattern = recipe.getPattern();
        Map<Character, Ingredient> key = recipe.getKey();

        // 消耗匹配位置的材料
        for (int row = 0; row < pattern.size(); row++) {
            for (int col = 0; col < pattern.get(row).length(); col++) {
                char patternChar = pattern.get(row).charAt(col);
                if (patternChar != ' ' && key.containsKey(patternChar)) {
                    int gridRow = row + offsetY;
                    int gridCol = col + offsetX;
                    int slotIndex = gridCol + gridRow * 3;

                    // 消耗这个槽位的材料
                    Slot slot = slots.get(slotIndex);
                    if (slot.hasItem()) {
                        ItemStack stack = slot.getItem();
                        if (stack.getCount() > 0) {
                            stack.shrink(1);
                            if (stack.isEmpty()) {
                                slot.setByPlayer(ItemStack.EMPTY);
                            } else {
                                slot.setChanged();
                            }
                        }
                    }
                }
            }
        }
    }

    private CrystalCarvingRecipe.Input getInputFromCraftingContainer() {
        NonNullList<ItemStack> items = NonNullList.withSize(craftingContainer.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
            items.set(i, craftingContainer.getItem(i));
        }
        return new CrystalCarvingRecipe.Input(items);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.CRYSTAL_CARVING_TABLE.get());
    }

    @Override
    public void slotsChanged(Container container) {
        access.execute((level, pos) -> changedCraftingSlots(this, level, player, craftingContainer, resultContainer));
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
    }

    private static void changedCraftingSlots(
            CrystalCarvingMenu menu,
            Level level,
            Player player,
            CraftingContainer craftingContainer,
            ResultContainer resultContainer
    ) {
        if (!level.isClientSide()) {
            ItemStack itemStack = ItemStack.EMPTY;
            CrystalCarvingRecipe.Input input = menu.getInputFromCraftingContainer();

            List<RecipeHolder<CrystalCarvingRecipe>> recipes = level.getRecipeManager()
                    .getRecipesFor(ModRecipeTypes.CRYSTAL_CARVING_TYPE.get(), input, level);

            for (RecipeHolder<CrystalCarvingRecipe> recipeHolder : recipes) {
                CrystalCarvingRecipe recipe = recipeHolder.value();
                if (recipe.matches(input, level)) {
                    itemStack = recipe.assemble(input, level.registryAccess());
                    break;
                }
            }

            resultContainer.setItem(0, itemStack);
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        access.execute((level, pos) -> {
            clearContainer(player, craftingContainer);
        });
    }
}
