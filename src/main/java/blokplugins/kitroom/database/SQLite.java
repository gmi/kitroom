package blokplugins.kitroom.database;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public class SQLite implements DatabaseManager{
    private final JavaPlugin plugin;
    private Connection connection;

    public SQLite(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void connect() {
        String dbFileName = "database.db";
        String dataFolder = plugin.getDataFolder().getPath();
        String dbPath = dataFolder + "/" + dbFileName;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

            String createTableSQLplayer = "CREATE TABLE IF NOT EXISTS players (" +
                    "uuid TEXT PRIMARY KEY, " +
                    "k1 TEXT DEFAULT NULL, k2 TEXT DEFAULT NULL, k3 TEXT DEFAULT NULL, " +
                    "k4 TEXT DEFAULT NULL, k5 TEXT DEFAULT NULL, k6 TEXT DEFAULT NULL, " +
                    "k7 TEXT DEFAULT NULL, k8 TEXT DEFAULT NULL, k9 TEXT DEFAULT NULL, " +
                    "ec1 TEXT DEFAULT NULL, ec2 TEXT DEFAULT NULL, ec3 TEXT DEFAULT NULL, " +
                    "ec4 TEXT DEFAULT NULL, ec5 TEXT DEFAULT NULL, ec6 TEXT DEFAULT NULL, " +
                    "ec7 TEXT DEFAULT NULL, ec8 TEXT DEFAULT NULL, ec9 TEXT DEFAULT NULL" +
                    ")";

            Statement query = connection.createStatement();
            query.execute(createTableSQLplayer);
            query.close();
            plugin.getLogger().info("[SQLite] Connected to SQLite database");
        } catch (SQLException e) {
            plugin.getLogger().severe("[SQLite] SQLite connection has failed >> " + e.getMessage());
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                plugin.getLogger().info("[SQLite] Disconnected from sqlite db");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void playerJoin(Player p) {
        String insertPlayerSQL = "INSERT OR IGNORE INTO players (uuid) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(insertPlayerSQL)) {
            statement.setString(1, p.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("[SQLite] Failed to insert player: " + e.getMessage());
        }
    }

    @Override
    public String getKit(Player p, String kitType) {
        String kit = null;
        String querySQL = "SELECT " + kitType + " FROM players WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(querySQL)) {
            statement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                kit = resultSet.getString(kitType);
                // If the value is SQL NULL, explicitly return null
                if (kit == null) {
                    return null;
                }
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("[SQLite] Failed to fetch kit: " + e.getMessage());
        }
        return kit;
    }

    @Override
    public void saveKit(Player p, String kitType, String kitData) {
        String updateSQL = "UPDATE players SET " + kitType + " = ? WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, kitData);
            statement.setString(2, p.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("[SQLite] Failed to save kit: " + e.getMessage());
        }
    }

}
