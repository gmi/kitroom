package blokplugins.kitroom.listners;

import blokplugins.kitroom.extra.KitRoomItemsMainHolder;
import blokplugins.kitroom.menus.mainmenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class kitroomitemslistner implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getHolder() instanceof KitRoomItemsMainHolder){
            if(e.getRawSlot() >= 45 && e.getRawSlot() <= 53) {
                Player player = (Player) e.getWhoClicked();
                if(e.getRawSlot() == 53) {
                    new mainmenu(player);
                }
                e.setCancelled(true);
            }

        }
    }
}
