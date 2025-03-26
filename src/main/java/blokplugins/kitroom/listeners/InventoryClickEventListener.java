package blokplugins.kitroom.listeners;

import blokplugins.kitroom.holders.EChestEditorHolder;
import blokplugins.kitroom.holders.KitEditorHolder;
import blokplugins.kitroom.holders.KitMenuHolder;
import blokplugins.kitroom.holders.KitRoomHolder;
import blokplugins.kitroom.listeners.inventories.EChestEditorListener;
import blokplugins.kitroom.listeners.inventories.KitEditorListener;
import blokplugins.kitroom.listeners.inventories.KitMenuListener;
import blokplugins.kitroom.listeners.inventories.KitRoomListener;
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
        if (holder instanceof KitEditorHolder) {
            KitEditorListener.handleInventoryClick(e);
        }
        if (holder instanceof EChestEditorHolder) {
            EChestEditorListener.handleInventoryClick(e);
        }
        if (holder instanceof KitRoomHolder) {
            KitRoomListener.handleInventoryClick(e);
        }
    }
}
