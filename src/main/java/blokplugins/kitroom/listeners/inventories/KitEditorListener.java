package blokplugins.kitroom.listeners.inventories;

import blokplugins.kitroom.Kitroom;
import blokplugins.kitroom.inventories.KitEditor;
import blokplugins.kitroom.inventories.KitMenu;
import blokplugins.kitroom.listeners.InventoryClickEventListener;
import blokplugins.kitroom.utils.SerializeInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class KitEditorListener {
    private static SerializeInventory serializeInventory = new SerializeInventory();
    public static void handleInventoryClick(InventoryClickEvent e) {
        int slot = e.getRawSlot();
        Player p = (Player) e.getWhoClicked();
        String title = e.getView().getTitle();
        String lastLetter = title.substring(title.length() - 1);
        if (slot < 41) { return; }
        if (slot < 50) { e.setCancelled(true); return; }
        switch (slot) {
            case 50:
                Inventory saveinv = e.getClickedInventory();
                for (int i = 41; i < saveinv.getSize(); i++) {
                    saveinv.setItem(i, null);
                }
                Kitroom.getDbManager().saveKit(p, "k" + lastLetter, serializeInventory.Serialze(saveinv));
                new KitMenu(p);
                e.setCancelled(true);
                break;
            case 51:
                new KitEditor(p, Integer.valueOf(lastLetter), p.getInventory());
                e.setCancelled(true);
                break;
            case 52:
                if(!e.isShiftClick()) {
                    e.setCancelled(true);
                    break;
                }
                Kitroom.getDbManager().saveKit(p, "k" + lastLetter, null);
                new KitEditor(p, Integer.valueOf(lastLetter), null);
                e.setCancelled(true);
                break;
            case 53:
                new KitMenu(p);
                e.setCancelled(true);
                break;
        }
    }
}
