package blokplugins.kitroom.listners;

import blokplugins.kitroom.extra.KitEChestHolder;
import blokplugins.kitroom.extra.KitEditHolder;
import blokplugins.kitroom.menus.editechest;
import blokplugins.kitroom.menus.mainmenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class echestlistner implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getHolder() instanceof KitEChestHolder) {
            if(e.getRawSlot() >= 27 && e.getRawSlot() <= 35) {
                Player player = (Player) e.getWhoClicked();
                if (e.getRawSlot() == 35) {
                    new mainmenu(player);
                } else if(e.getRawSlot() == 33) {
                    String title = e.getView().getTitle();
                    String lastLetter = title.substring(title.length() - 1);
                    new editechest(player, Integer.valueOf(lastLetter), player.getEnderChest());
                }
                e.setCancelled(true);
            }
        }
    }
}
