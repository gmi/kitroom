package blokplugins.kitroom.listners;

import blokplugins.kitroom.database.PointsDatabase;
import blokplugins.kitroom.extra.InventorySerializations;
import blokplugins.kitroom.extra.KitEditHolder;
import blokplugins.kitroom.menus.editkit;
import blokplugins.kitroom.menus.mainmenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.SQLException;

public class editkitlistnet implements Listener {
    private final PointsDatabase pointsDatabase;
    private final InventorySerializations inventorySerializer;

    public editkitlistnet(InventorySerializations inventorySerializer, PointsDatabase pointsDatabase) {
        this.inventorySerializer = inventorySerializer;
        this.pointsDatabase = pointsDatabase;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws SQLException {
        if (e.getInventory().getHolder() instanceof KitEditHolder) {
            if(e.getRawSlot() <= 53 && e.getRawSlot() >= 41) {
                String title = e.getView().getTitle();
                String lastLetter = title.substring(title.length() - 1);
                Player player = (Player) e.getWhoClicked();
                 if(e.getRawSlot() == 53) {
                    new mainmenu(player);
                 } else if (e.getRawSlot() == 51) {
                     new editkit(player,Integer.valueOf(lastLetter), player.getInventory(), null);
                 } else if (e.getRawSlot() == 50) {
                     inventorySerializer.serializeInventory(e.getClickedInventory(), e.getWhoClicked().getUniqueId().toString(), "Kit " + lastLetter, true);
                 } else if (e.getRawSlot() == 52) {
                     if (e.isShiftClick()) {
                         String uuid = player.getUniqueId().toString();
                         String kitName = "Kit " + lastLetter;
                         pointsDatabase.deleteKit(uuid, kitName);
                         new editkit(player, Integer.valueOf(lastLetter), null, null);
                     }

                 }
                e.setCancelled(true);
            }
        }
    }
}
