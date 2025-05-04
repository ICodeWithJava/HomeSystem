package me.icwj.homesystem.utilities;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder {

    private static final ItemStack SLOT_FILLER = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(Component.text(" ")).build();

    public void buildInventory(final Inventory inventory) {
        final int inventorySize = inventory.getSize();

        if (inventorySize % 9 != 0) {
            return;
        }

        final int inventoryRows = inventorySize / 9;

        fillRow(inventory, 0);
        fillRow(inventory, inventoryRows - 1);

        for (int row = 1; row < inventoryRows - 1; row++) {
            final int start = row * 9;
            inventory.setItem(start, SLOT_FILLER.clone());
            inventory.setItem(start + 8, SLOT_FILLER.clone());
        }
    }

    private void fillRow(final Inventory inventory, final int row) {
        final int start = row * 9;

        for (int i = 0; i < 9; i++) {
            inventory.setItem(start + i, SLOT_FILLER.clone());
        }
    }
}