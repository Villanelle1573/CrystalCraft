package net.villanelle.crystal_craft.inventory;

import net.minecraft.util.Prediction;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.villanelle.crystal_craft.init.ModBlocks;
import net.villanelle.crystal_craft.init.ModItemTags;
import net.villanelle.crystal_craft.init.ModMenuTypes;
import net.villanelle.crystal_craft.init.ModRecipeTypes;
import net.villanelle.crystal_craft.recipe.chipping.ChippingInput;
import net.villanelle.crystal_craft.recipe.chipping.ChippingRecipe;

import java.util.Optional;

public class ChippingMenu extends AbstractContainerMenu {
    public static final int CHIPPING_SLOT_A = 0;
    public static final int CHIPPING_SLOT_B = 1;
    public static final int RESULT_SLOT = 2;
    public static final int INV_SLOT_START = 3;
    public static final int INV_SLOT_END = 30;
    public static final int USE_ROW_SLOT_START = 30;
    public static final int USE_ROW_SLOT_END = 39;
    private final ContainerLevelAccess access;
    private final Level level;
    private final Player player;
    private final Container container;

    public ChippingMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public ChippingMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(ModMenuTypes.CHIPPING_MENU.get(), containerId);
        this.access = access;
        this.level = inventory.player.level();
        this.player = inventory.player;
        this.container = new SimpleContainer(3) {
            @Override
            public void setChanged() {
                super.setChanged();
                ChippingMenu.this.slotsChanged(this);
            }
        };

        addSlot(new Slot(container, CHIPPING_SLOT_A,33, 33));

        addSlot(new Slot(container, CHIPPING_SLOT_B, 65, 33) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItemTags.CHIPPING_TOOLS);
            }
        });
        addSlot(new Slot(container, RESULT_SLOT, 129, 33) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player player) {
                return true;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                super.onTake(player, stack);
                consumeResources();
            }

            private void consumeResources() {
                // 消耗原料
                container.removeItem(CHIPPING_SLOT_A, 1);

                // 消耗工具耐久
                ItemStack tool = container.getItem(CHIPPING_SLOT_B);
                if (!tool.isEmpty() && tool.isDamageableItem()) {
                    int newDamage = tool.getDamageValue() + 1;
                    if (newDamage >= tool.getMaxDamage()) {
                        container.setItem(CHIPPING_SLOT_B, ItemStack.EMPTY);
                    } else {
                        tool.setDamageValue(newDamage);
                    }
                }
            }

            @Override
            protected void onQuickCraft(ItemStack stack, int amount) {
                // 快速合成时，根据实际移出的物品数量计算合成次数
                // 假设每次合成产出stack.getCount()个物品
                int craftTimes = amount / stack.getCount();
                for (int i = 0; i < craftTimes; i++) {
                    consumeResources();
                }
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public void slotsChanged(Container container) {
        if (!this.level.isClientSide()) {
            ItemStack result = ItemStack.EMPTY;
            ChippingInput input = new ChippingInput(
                    this.container.getItem(CHIPPING_SLOT_A),
                    this.container.getItem(CHIPPING_SLOT_B));

            Optional<RecipeHolder<ChippingRecipe>> recipe = this.level.getServer()
                    .getRecipeManager()
                    .getRecipeFor(ModRecipeTypes.CHIPPING.get(), input, this.level);

            if (recipe.isPresent()) {
                result = recipe.get().value().assemble(input);
            }

            ItemStack currentResult = this.container.getItem(RESULT_SLOT);
            if (!ItemStack.matches(currentResult, result)) {
                this.container.setItem(RESULT_SLOT, result);
            }
        }
        super.slotsChanged(container);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.CHIPPING_TABLE.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        if (!this.level.isClientSide()) {
            for (int i = 0; i < 3; ++i) {
                ItemStack itemStack = this.container.getItem(i);
                if (!itemStack.isEmpty()) {
                    player.getInventory().placeItemBackInInventory(itemStack, Prediction.SERVER_ONLY);
                }
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            if (index == RESULT_SLOT) {
                if (!this.moveItemStackTo(itemStack1, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index >= INV_SLOT_START) {
                if (!this.moveItemStackTo(itemStack1, CHIPPING_SLOT_A, RESULT_SLOT, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(itemStack1, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemStack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }
}