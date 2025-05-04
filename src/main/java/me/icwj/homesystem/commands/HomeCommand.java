package me.icwj.homesystem.commands;

import me.icwj.homesystem.utilities.ConfigMessages;
import me.icwj.homesystem.utilities.InventoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class HomeCommand implements CommandExecutor {

    private final InventoryBuilder inventoryBuilder = new InventoryBuilder();

    private void openGUI(final Player player) {
        final Inventory homeInventory = Bukkit.createInventory(null, 9 * 6, ConfigMessages.INVENTORY_TITLE);

        inventoryBuilder.buildInventory(homeInventory);

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