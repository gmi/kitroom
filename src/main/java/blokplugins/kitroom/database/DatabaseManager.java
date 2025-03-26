package blokplugins.kitroom.database;

import org.bukkit.entity.Player;

public interface DatabaseManager {
    void connect();
    void disconnect();
    void playerJoin(Player p);
    String getKit(Player p, String kitType);
    void saveKit(Player p, String kitType, String kitData);
    void saveKitRoom(String name, String kit);
}
