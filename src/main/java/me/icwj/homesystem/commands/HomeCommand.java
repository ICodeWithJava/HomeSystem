package me.icwj.homesystem.commands;

import me.icwj.homesystem.manager.HomeManager;
import me.icwj.homesystem.utilities.ConfigMessages;
import me.icwj.homesystem.utilities.InventoryBuilder;
import me.icwj.homesystem.utilities.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeCommand implements CommandExecutor {

    private final HomeManager homeManager = new HomeManager();
    private final InventoryBuilder inventoryBuilder = new InventoryBuilder();

    private void openGUI(final Player player) {
        final Inventory homeInventory = Bukkit.createInventory(null, 9 * 6, ConfigMessages.INVENTORY_TITLE);

        inventoryBuilder.buildInventory(homeInventory);

        final List<String> homes = homeManager.getAllHomes(player);

        if (homes == null) {
            player.sendMessage(ConfigMessages.PLUGIN_PREFIX.append(Component.text("Beim Laden deiner Homes ist ein Fehler aufgetreten.", NamedTextColor.RED)));
            return;
        }

        homeInventory.setItem(4, new ItemBuilder(Material.BOOK, 1).setDisplayName(Component.text("Homes:")).setLore(homes.isEmpty() ? "Du hast keine Home Punkte gesetzt" : "- " + homes.size()).build());
        homeInventory.setItem(49, new ItemBuilder(Material.BUCKET, 1).setDisplayName(Component.text("Lösche alle Homes")).build());

        for (String home : homes) {
            homeInventory.addItem(new ItemBuilder(Material.RED_BED, 1).setDisplayName(Component.text(home)).setLore("- Linksklick zum Teleportieren", "- Rechtsklick zum Löschen").build());
        }

        player.openInventory(homeInventory);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(ConfigMessages.SENDER_IS_CONSOLE);
            return true;
        }

        openGUI(player);

        return false;
    }
}