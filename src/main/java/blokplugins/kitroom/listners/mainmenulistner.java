package blokplugins.kitroom.listners;

import blokplugins.kitroom.extra.KitRoomMainHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class mainmenulistner implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getHolder() instanceof KitRoomMainHolder){
            Server server = Bukkit.getServer();
            Player player = (Player) e.getWhoClicked();
            if(e.isShiftClick() && e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
                player.getInventory().clear();
                player.closeInventory();
            }
            e.setCancelled(true);
        }
    }
}
