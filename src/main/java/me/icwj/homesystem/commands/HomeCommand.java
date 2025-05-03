package me.icwj.homesystem.commands;

import me.icwj.homesystem.HomeSystem;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomeCommand implements CommandExecutor {

    private final HomeSystem plugin;

    public HomeCommand(final HomeSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }

        player.sendMessage(Component.text(plugin.pluginPrefix + "Test!"));

        return false;
    }
}