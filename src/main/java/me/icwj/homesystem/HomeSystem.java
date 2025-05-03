package me.icwj.homesystem;

import me.icwj.homesystem.commands.HomeCommand;
import me.icwj.homesystem.database.Credentials;
import me.icwj.homesystem.database.Database;
import me.icwj.homesystem.exceptions.InvalidDatabaseCredentialsException;
import me.icwj.homesystem.manager.HomeManager;
import me.icwj.homesystem.utilities.CredentialValidator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.DataSource;
import java.util.Objects;

public class HomeSystem extends JavaPlugin {

    private static HomeSystem instance;

    private final Database database = new Database();
    private DataSource dataSource;
    public final String PREFIX = getConfig().getString("General.Prefix");

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        initializePlugin();
    }

    @Override
    public void onDisable() {
        getComponentLogger().info(Component.text("Das Plugin wurde deaktiviert.", NamedTextColor.RED));
    }

    private void initializePlugin() {
        try {
            loadConfig();
            connectDatabase();
            registerEvents();

            getComponentLogger().info(Component.text("Das Plugin wurde erfolgreich geladen!", NamedTextColor.GREEN));
        } catch (Exception exception) {
            getComponentLogger().error(
                    Component.text("Fehler beim Laden des Plugins: ", NamedTextColor.RED)
                            .append(Component.text(exception.getMessage(), NamedTextColor.GRAY))
            );
        }
    }

    private void loadConfig() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void connectDatabase() {
        final String host = getConfig().getString("Database.Host");
        final int port = getConfig().getInt("Database.Port");
        final String database = getConfig().getString("Database.Database");
        final String username = getConfig().getString("Database.Username");
        final String password = getConfig().getString("Database.Password");
        final int maximumConnectionPoolSize = getConfig().getInt("Database.MaximumConnectionPoolSize");

        if (CredentialValidator.isInvalid(host, database, username, password)) {
            throw new InvalidDatabaseCredentialsException("Ein oder mehrere Datenbank-Konfigurationswerte fehlen oder sind leer.");
        }

        try {
            dataSource = Database.createDataSource(new Credentials(host, port, database, username, password, maximumConnectionPoolSize));
            this.database.createTable();
            new HomeManager(dataSource);

            getComponentLogger().info(
                    Component.text("Erfolgreich mit der Datenbank verbunden!", NamedTextColor.GREEN)
            );
        } catch (Exception exception) {
            getComponentLogger().error(
                    Component.text("Fehler beim Verbinden der Datenbank:", NamedTextColor.RED)
                            .append(Component.text(" Fehlernachricht: " + exception.getMessage(), NamedTextColor.GRAY))
            );
        }
    }

    private void registerEvents() {
        Objects.requireNonNull(getCommand("home")).setExecutor(new HomeCommand(this));
    }

    public static HomeSystem getInstance() {
        return instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}