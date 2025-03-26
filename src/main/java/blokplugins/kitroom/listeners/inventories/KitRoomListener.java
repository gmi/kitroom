package blokplugins.kitroom.listeners.inventories;

import blokplugins.kitroom.Kitroom;
import blokplugins.kitroom.inventories.KitMenu;
import blokplugins.kitroom.inventories.KitRoom;
import blokplugins.kitroom.utils.SerializeInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitRoomListener {
    private static SerializeInventory serializeInventory = new SerializeInventory();

    public static void handleInventoryClick(InventoryClickEvent e) {
        int slot = e.getRawSlot();
        Player p = (Player) e.getWhoClicked();
        ItemStack adminItem = e.getClickedInventory().getItem(45);

        switch (slot) {
            case 46, 47, 51, 52:
                e.setCancelled(true);
                break;
            case 45:
                ItemStack clickedItem = e.getCurrentItem();
                if (clickedItem.getType() == Material.BEACON) {
                    new KitRoom(p, e.getView().getTitle(), false); // Switch to edit mode
                }
                if (clickedItem.getType() == Material.LIME_DYE) {
                    Inventory saveinv = e.getClickedInventory();
                    for (int i = 45; i < saveinv.getSize(); i++) {
                        saveinv.setItem(i, null);
                    }
                    if (p.hasPermission("kitroom.adminkitroom.admin")) { // Save only if admin
                        Kitroom.getDbManager().saveKitRoom(e.getView().getTitle(), serializeInventory.Serialze(saveinv));
                    }
                    new KitRoom(p, e.getView().getTitle(), true);
                }

                e.setCancelled(true);
                break;
            case 48:
                if (adminItem.getType() == Material.LIME_DYE) {
                    new KitRoom(p, "equipment", true); // Edit mode
                } else {
                    new KitRoom(p, "equipment", false); // View mode
                }
                e.setCancelled(true);
                break;
            case 49:
                if (adminItem.getType() == Material.LIME_DYE) {
                    new KitRoom(p, "pvp", true); // Edit mode
                } else {
                    new KitRoom(p, "pvp", false); // View mode
                }
                e.setCancelled(true);
                break;
            case 50:
                if (adminItem.getType() == Material.LIME_DYE) {
                    new KitRoom(p, "potions", true); // Edit mode
                } else {
                    new KitRoom(p, "potions", false); // View mode
                }
                e.setCancelled(true);
                break;
            case 53:
                new KitMenu(p);
                e.setCancelled(true);
                break;
        }
    }
}