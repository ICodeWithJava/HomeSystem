package me.icwj.homesystem.listeners;

import me.icwj.homesystem.manager.HomeManager;
import me.icwj.homesystem.utilities.ConfigMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class HomeInventoryListener implements Listener {

    private final HomeManager homeManager = new HomeManager();

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

        final ItemStack clickedItem = inventoryClickEvent.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        final Player player = (Player) inventoryClickEvent.getWhoClicked();
        final Material clickedMaterial = clickedItem.getType();

        switch (clickedMaterial) {
            case BUCKET:
                player.closeInventory();

                if (homeManager.getAllHomes(player).isEmpty()) {
                    player.sendMessage(ConfigMessages.PLUGIN_PREFIX.append(Component.text("§cDu hast zurzeit keine Home-Punkte gesetzt.")));
                    return;
                }

                try {
                    homeManager.deleteAllHomes(player);

                    player.sendMessage(ConfigMessages.PLUGIN_PREFIX.append(Component.text("§aAlle Home-Punkte wurden erfolgreich gelöscht.")));
                } catch (SQLException exception) {
                    player.sendMessage(ConfigMessages.PLUGIN_PREFIX.append(Component.text("§cFehler beim Löschen deiner Home-Punkte. Bitte versuche es erneut.")));
                }
                break;
            case RED_BED:
                player.closeInventory();
                break;
        }
    }
}