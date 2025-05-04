package me.icwj.homesystem.listeners;

import me.icwj.homesystem.utilities.ConfigMessages;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HomeInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getClickedInventory() == null) {
            return;
        }

        final String inventoryTitle = PlainTextComponentSerializer.plainText().serialize(inventoryClickEvent.getView().title());

        if (!(inventoryTitle.equalsIgnoreCase(PlainTextComponentSerializer.plainText().serialize(ConfigMessages.INVENTORY_TITLE)))) {
            return;
        }

        inventoryClickEvent.setCancelled(true);
    }
}