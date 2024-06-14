package blokplugins.kitroom.listners;

import blokplugins.kitroom.extra.KitRoomMainHolder;
import blokplugins.kitroom.menus.editechest;
import blokplugins.kitroom.menus.editkit;
import blokplugins.kitroom.menus.kitroomitems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class mainmenulistner implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getHolder() instanceof KitRoomMainHolder){
            Player player = (Player) e.getWhoClicked();
            if(e.isShiftClick() && e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
                player.getInventory().clear();
            } else if(e.getCurrentItem().getType() == Material.NETHER_STAR) {
                new kitroomitems(player);
            } else if (e.getRawSlot() >= 9 && e.getRawSlot() <= 17){
                if (e.isRightClick()){
                    new editkit(player, e.getRawSlot()-8, null);
                } else if (e.isLeftClick()) {
                    player.sendMessage("not added yet");
                }
            } else if (e.getRawSlot() >= 18 && e.getRawSlot() <= 26) {
                if (e.isRightClick()){
                    new editechest(player, e.getRawSlot()-17, null);
                } else if (e.isLeftClick()) {
                    player.sendMessage("not added yet");
                }
            }
            e.setCancelled(true);
        }
    }
}
