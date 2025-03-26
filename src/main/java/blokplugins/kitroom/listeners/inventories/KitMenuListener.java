package blokplugins.kitroom.listeners.inventories;

import blokplugins.kitroom.inventories.EChestEditor;
import blokplugins.kitroom.inventories.KitEditor;
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
                }
                break;
            case 18, 19, 20, 21, 22, 23, 24, 25, 26:
                if(e.isRightClick()) {
                    new EChestEditor(p, e.getRawSlot() - 17, null);
                }
                break;
            case 30:
            case 32:
                if (e.isShiftClick()) { p.getInventory().clear(); }
            case 40:
        }

        e.setCancelled(true);
    }
}
