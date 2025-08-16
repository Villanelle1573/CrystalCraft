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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.villanelle.crystal_craft.init.ModBlocks;
import net.villanelle.crystal_craft.init.ModRecipeTypes;
import net.villanelle.crystal_craft.recipe.CrystalCarvingRecipe;

import javax.annotation.Nullable;

// 创建一个专门的结果槽类，禁止放置物品
class CrystalCarvingResultSlot extends Slot {
    public CrystalCarvingResultSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false; // 禁止在结果槽放置物品
    }

    @Override
    public boolean mayPickup(Player player) {
        return hasItem(); // 只有当槽位有物品时才能取走
    }
}

public class CrystalCarvingMenu extends AbstractContainerMenu {
    public static final int RESULT_SLOT = 0;
    public static final int CRAFT_SLOT_START = 1;
    public static final int CRAFT_SLOT_END = 10;
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

        // 结果槽 - 使用自定义的只读槽位
        addSlot(new CrystalCarvingResultSlot(resultContainer, 0, 124, 35));

        // 3x3合成网格
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                addSlot(new Slot(craftingContainer, col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        // 玩家背包
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
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
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index >= RESULT_SLOT && index < CRAFT_SLOT_END) {
            // 如果是从结果槽或合成槽移动，只能移动到玩家背包
            if (!moveItemStackTo(copyOfSourceStack, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= INV_SLOT_START && index < USE_ROW_SLOT_END) {
            // 如果是从玩家背包移动，优先移动到合成槽
            if (!moveItemStackTo(copyOfSourceStack, CRAFT_SLOT_START, CRAFT_SLOT_END, false)) {
                if (index < INV_SLOT_END) {
                    // 从背包移动到快捷栏
                    if (!moveItemStackTo(copyOfSourceStack, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // 从快捷栏移动到背包
                    if (!moveItemStackTo(copyOfSourceStack, INV_SLOT_START, INV_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (copyOfSourceStack.isEmpty()) {
            sourceSlot.setByPlayer(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        if (copyOfSourceStack.getCount() == sourceStack.getCount()) {
            return ItemStack.EMPTY;
        }

        sourceStack.setCount(copyOfSourceStack.getCount());
        sourceSlot.onTake(player, copyOfSourceStack);
        return sourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.CRYSTAL_CARVING_TABLE.get());
    }

    @Override
    public void slotsChanged(Container container) {
        access.execute((level, pos) -> changedCraftingSlots(this, level, player, craftingContainer, resultContainer));
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
            NonNullList<ItemStack> items = NonNullList.withSize(craftingContainer.getContainerSize(), ItemStack.EMPTY);
            for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
                items.set(i, craftingContainer.getItem(i));
            }
            CrystalCarvingRecipe.Input input = new CrystalCarvingRecipe.Input(items);

            RecipeHolder<CrystalCarvingRecipe> recipeHolder = level.getRecipeManager()
                    .getRecipeFor(ModRecipeTypes.CRYSTAL_CARVING_TYPE.get(), input, level)
                    .orElse(null);

            if (recipeHolder != null) {
                CrystalCarvingRecipe recipe = recipeHolder.value();
                if (recipe.matches(input, level)) {
                    if (resultContainer.setRecipeUsed(level, (net.minecraft.server.level.ServerPlayer) player, recipeHolder)) {
                        itemStack = recipe.assemble(input, level.registryAccess());
                    }
                }
            }
            resultContainer.setItem(0, itemStack);
            menu.setRemoteSlot(RESULT_SLOT, itemStack);
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
