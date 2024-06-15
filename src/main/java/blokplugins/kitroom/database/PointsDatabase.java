package blokplugins.kitroom.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class PointsDatabase {
    private final Connection connection;

    public PointsDatabase(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS kits(
                    uuid TEXT,
                    kit TEXT,
                    kitdata BLOB)
               """);
        }
    }
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()){
            connection.close();
        }
    }
    public void uploadKit(String uuid, String kitName, List<Map<String, Object>> kitData) throws SQLException {
        Gson gson = new Gson();

        // Check if kit with the same UUID and kit name exists
        boolean kitExists = false;
        try (PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM kits WHERE uuid = ? AND kit = ?")) {
            checkStatement.setString(1, uuid);
            checkStatement.setString(2, kitName);

            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                kitExists = count > 0;
            }
        }

        if (kitExists) {
            try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE kits SET kitdata = ? WHERE uuid = ? AND kit = ?")) {
                String kitDataJson = gson.toJson(kitData);
                updateStatement.setString(1, kitDataJson);
                updateStatement.setString(2, uuid);
                updateStatement.setString(3, kitName);
                updateStatement.executeUpdate();
            }
        } else {
            try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO kits (uuid, kit, kitdata) VALUES (?, ?, ?)")) {
                String kitDataJson = gson.toJson(kitData);
                insertStatement.setString(1, uuid);
                insertStatement.setString(2, kitName);
                insertStatement.setString(3, kitDataJson);
                insertStatement.executeUpdate();
            }
        }
    }
    public List<Map<String, Object>> getKitData(String uuid, String kitName) throws SQLException {
        Gson gson = new Gson();

        try (PreparedStatement statement = connection.prepareStatement("SELECT kitdata FROM kits WHERE uuid = ? AND kit = ?")) {
            statement.setString(1, uuid);
            statement.setString(2, kitName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String kitDataJson = resultSet.getString("kitdata");
                
                Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
                List<Map<String, Object>> kitData = gson.fromJson(kitDataJson, listType);

                return kitData;
            }
        }

        return null;
    }
    public void deleteKit(String uuid, String kitName) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM kits WHERE uuid = ? AND kit = ?")) {
            statement.setString(1, uuid);
            statement.setString(2, kitName);
            statement.executeUpdate();
        }
    }
    public boolean kitExists(String uuid, String kitName) throws SQLException {
        try (PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM kits WHERE uuid = ? AND kit = ?")) {
            checkStatement.setString(1, uuid);
            checkStatement.setString(2, kitName);

            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        }
        return false;
    }

}
