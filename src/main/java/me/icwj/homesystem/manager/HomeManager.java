package me.icwj.homesystem.manager;

import me.icwj.homesystem.HomeSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeManager {

    private final DataSource dataSource = HomeSystem.getInstance().getDataSource();

    public void createHome(final Player player, final String homeName) throws SQLException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO homes (player_user_id, home_name) VALUES (?, ?)")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, homeName);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteHome(final Player player, final String homeName) throws SQLException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM homes WHERE player_user_id = ? AND home_name = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, homeName);
            preparedStatement.executeUpdate();
        }
    }

    public Location getHomeLocation(final Player player, final String homeName) {
        return null;
    }

    public List<String> getAllHomes(final Player player) {
        final List<String> homes = new ArrayList<>();

        try (final Connection connection = dataSource.getConnection();
        final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM homes WHERE player_user_id = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    homes.add(resultSet.getString("home_name"));
                }
            }
        } catch (SQLException exception) {
            HomeSystem.getInstance().getComponentLogger().error(
                    Component.text("Fehler beim Abrufen aller Homes aus der Datenbank.", NamedTextColor.RED)
                            .append(Component.text(" Spieler: " + player.getName() + " (" + player.getUniqueId() + ")", NamedTextColor.GRAY))
            );
        }
        return homes;
    }

    public void deleteAllHomes(final Player player) throws SQLException {
        try (final Connection connection = dataSource.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM homes WHERE player_user_id = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }
}