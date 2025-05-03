package me.icwj.homesystem.utilities;

import me.icwj.homesystem.HomeSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class SQLBuilder {

    public void buildSQL() throws IOException, SQLException {
        try (final InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(SQLBuilder.class.getClassLoader().getResourceAsStream("setup.sql")));
             final Connection connection = HomeSystem.getInstance().getDataSource().getConnection();
             final Statement statement = connection.createStatement()) {

            final StringBuilder query = new StringBuilder();

            try (final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    query.append(line);
                }
            }

            if (!(query.isEmpty())) {
                statement.executeUpdate(query.toString());
            }
        }
    }
}