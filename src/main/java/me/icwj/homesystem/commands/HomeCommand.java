package me.icwj.homesystem.commands;

import me.icwj.homesystem.utilities.ConfigMessages;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(ConfigMessages.SENDER_IS_CONSOLE);
            return true;
        }

        player.sendMessage(ConfigMessages.PLUGIN_PREFIX.append(Component.text("Test!")));

        return false;
    }
}