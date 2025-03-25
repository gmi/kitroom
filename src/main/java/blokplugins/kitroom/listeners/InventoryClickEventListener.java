package blokplugins.kitroom.listeners;

import blokplugins.kitroom.holders.KitMenuHolder;
import blokplugins.kitroom.listeners.inventories.KitMenuListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickEventListener implements Listener {
    @EventHandler
    public void onInventoryCLick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof KitMenuHolder) {
            KitMenuListener.handleInventoryClick(e);
        }
    }
}
