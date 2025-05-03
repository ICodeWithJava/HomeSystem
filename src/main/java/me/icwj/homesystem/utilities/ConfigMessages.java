package me.icwj.homesystem.utilities;

import me.icwj.homesystem.HomeSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Objects;

public class ConfigMessages {

    public static final Component PLUGIN_PREFIX = MiniMessage.miniMessage().deserialize(Objects.requireNonNull(HomeSystem.getInstance().getConfig().getString("General.Prefix")));
    public static final Component SENDER_IS_CONSOLE = MiniMessage.miniMessage().deserialize(Objects.requireNonNull(HomeSystem.getInstance().getConfig().getString("General.SenderIsConsole")));
}