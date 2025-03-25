package blokplugins.kitroom.listeners.inventories;

import org.bukkit.event.inventory.InventoryClickEvent;

public class KitMenuListener {
    public static void handleInventoryClick(InventoryClickEvent e) {
        int slot = e.getRawSlot();

        switch (slot) {
            case 9, 10, 11, 12, 13, 14, 15, 16, 17:
            case 18, 19, 20, 21, 22, 23, 24, 25, 26:
        }

        e.setCancelled(true);
    }
}
