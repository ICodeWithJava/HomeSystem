package me.icwj.homesystem.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.icwj.homesystem.HomeSystem;
import me.icwj.homesystem.utilities.SQLBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Database {

    private final SQLBuilder sqlBuilder = new SQLBuilder();

    public void createTable() {
        try {
            sqlBuilder.buildSQL();

            HomeSystem.getInstance().getComponentLogger().info(
                    Component.text("SQL-Befehl erfolgreich ausgeführt!", NamedTextColor.GREEN)
            );
        } catch (SQLException exception) {
            HomeSystem.getInstance().getComponentLogger().error(
                    Component.text("Fehler beim Ausführen von SQL-Befehlen: ", NamedTextColor.RED)
                            .append(Component.text(exception.getMessage(), NamedTextColor.GRAY))
            );
        } catch (Exception exception) {
            HomeSystem.getInstance().getComponentLogger().error(
                    Component.text("Ein unerwarteter Fehler ist aufgetreten: ", NamedTextColor.RED)
                            .append(Component.text(exception.getMessage(), NamedTextColor.GRAY))
            );
        }
    }

    public static DataSource createDataSource(final Credentials credentials) {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s", credentials.host(), credentials.port(), credentials.database()));
        config.setUsername(credentials.username());
        config.setPassword(credentials.password());
        config.setMaximumPoolSize(credentials.maximumConnectionPoolSize());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }
}