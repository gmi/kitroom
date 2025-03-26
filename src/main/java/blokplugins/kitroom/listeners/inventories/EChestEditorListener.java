package blokplugins.kitroom.listeners.inventories;

import blokplugins.kitroom.Kitroom;
import blokplugins.kitroom.inventories.EChestEditor;
import blokplugins.kitroom.inventories.KitEditor;
import blokplugins.kitroom.inventories.KitMenu;
import blokplugins.kitroom.utils.SerializeInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class EChestEditorListener {
    private static SerializeInventory serializeInventory = new SerializeInventory();
    public static void handleInventoryClick(InventoryClickEvent e) {
        int slot = e.getRawSlot();
        Player p = (Player) e.getWhoClicked();
        String title = e.getView().getTitle();
        String lastLetter = title.substring(title.length() - 1);
        if (slot < 27) { return; }
        if (slot < 32) { e.setCancelled(true); return; }
        switch (slot) {
            case 32:
                Inventory saveinv = e.getClickedInventory();
                for (int i = 27; i < saveinv.getSize(); i++) {
                    saveinv.setItem(i, null);
                }
                Kitroom.getDbManager().saveKit(p, "ec" + lastLetter, serializeInventory.Serialze(saveinv));
                new KitMenu(p);
                e.setCancelled(true);
                break;
            case 33:
                new EChestEditor(p, Integer.valueOf(lastLetter), p.getEnderChest());
                e.setCancelled(true);
                break;
            case 34:
                if(!e.isShiftClick()) {
                    e.setCancelled(true);
                    break;
                }
                Kitroom.getDbManager().saveKit(p, "ec" + lastLetter, null);
                new EChestEditor(p, Integer.valueOf(lastLetter), null);
                e.setCancelled(true);
                break;
            case 35:
                new KitMenu(p);
                e.setCancelled(true);
                break;
        }
    }
}
