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

    private void openHomes(final Player player) {
        final Inventory homeInventory = Bukkit.createInventory(null, 9 * 6, ConfigMessages.INVENTORY_TITLE);

        inventoryBuilder.buildInventory(homeInventory);

        final List<String> homes = homeManager.getAllHomes(player);

        if (homes == null) {
            player.sendMessage(ConfigMessages.PLUGIN_PREFIX.append(Component.text("Beim Laden deiner Homes ist ein Fehler aufgetreten.", NamedTextColor.RED)));
            return;
        }

        homeInventory.setItem(4, new ItemBuilder(Material.BOOK, 1).setDisplayName(Component.text("§3Homes")).setLore(homes.isEmpty() ? "§8➥ §cDu hast zurzeit keine Home-Punkte gesetzt." : String.format("§8➥ §7Du hast zurzeit §3%s §7Home-Punkte gesetzt.", homes.size())).build());
        homeInventory.setItem(49, new ItemBuilder(Material.BUCKET, 1).setDisplayName(Component.text("§4§lAlle Home-Punkte Löschen")).setLore("§8➥ §7Klicke §4hier§7, um alle Home-Punkte zu löschen.").build());

        for (String home : homes) {
            homeInventory.addItem(new ItemBuilder(Material.RED_BED, 1)
                    .setDisplayName(Component.text("§3".concat(home)))
                    .setLore("§8➥ §aLinksklick §7um dich zum Home-Punkt zu §ateleportieren§7.",
                            "§8➥ §cRechtsklick §7um diesen Home-Punkt zu §clöschen§7.")
                    .build());
        }

        player.openInventory(homeInventory);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(ConfigMessages.SENDER_IS_CONSOLE);
            return true;
        }

        openHomes(player);

        return false;
    }
}