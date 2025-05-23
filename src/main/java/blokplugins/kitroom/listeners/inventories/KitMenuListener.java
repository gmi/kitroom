package blokplugins.kitroom.listeners.inventories;

import blokplugins.kitroom.inventories.EChestEditor;
import blokplugins.kitroom.inventories.KitEditor;
import blokplugins.kitroom.inventories.KitRoom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KitMenuListener {
    public static void handleInventoryClick(InventoryClickEvent e) {
        int slot = e.getRawSlot();
        Player p = (Player) e.getWhoClicked();
        switch (slot) {
            case 9, 10, 11, 12, 13, 14, 15, 16, 17:
                if(e.isRightClick()) {
                    new KitEditor(p, e.getRawSlot() - 8, null);
                    e.setCancelled(true);
                    break;
                }
                if (e.isLeftClick()) {
                    p.performCommand("k" + (e.getRawSlot() - 8));
                    e.setCancelled(true);
                    p.closeInventory();
                    break;
                }
                break;
            case 18, 19, 20, 21, 22, 23, 24, 25, 26:
                if(e.isRightClick()) {
                    new EChestEditor(p, e.getRawSlot() - 17, null);
                    e.setCancelled(true);
                    break;
                }
                if (e.isLeftClick()) {
                    p.performCommand("ec" + (e.getRawSlot() - 17));
                    e.setCancelled(true);
                    p.closeInventory();
                    break;
                }
                break;
            case 30: new KitRoom(p,"equipment", false); e.setCancelled(true); break;
            case 32:
                if (e.isShiftClick()) { p.getInventory().clear();} break;

            case 40:
                if(p.hasPermission("kitroom.admin")) {
                    new KitRoom(p,"equipment", true); e.setCancelled(true); break;
                }
        }

        e.setCancelled(true);
    }
}
