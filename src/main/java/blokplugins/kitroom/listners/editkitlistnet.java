package blokplugins.kitroom.listners;

import blokplugins.kitroom.extra.KitEditHolder;
import blokplugins.kitroom.menus.editkit;
import blokplugins.kitroom.menus.mainmenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class editkitlistnet implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof KitEditHolder) {
            if(e.getRawSlot() <= 53 && e.getRawSlot() >= 41) {
                Player player = (Player) e.getWhoClicked();
                 if(e.getRawSlot() == 53) {
                    new mainmenu(player);
                 } else if (e.getRawSlot() == 51) {
                     String title = e.getView().getTitle();
                     String lastLetter = title.substring(title.length() - 1);
                     new editkit(player,Integer.valueOf(lastLetter), player.getInventory());
                 }
                e.setCancelled(true);
            }
        }
    }
}
