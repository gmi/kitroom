package blokplugins.kitroom.listeners;

import blokplugins.kitroom.Kitroom;
import blokplugins.kitroom.database.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {
    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e) {
        Kitroom.getDbManager().playerJoin(e.getPlayer());
    }
}