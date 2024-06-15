package blokplugins.kitroom.listners;

import blokplugins.kitroom.database.PointsDatabase;
import blokplugins.kitroom.extra.InventorySerializations;
import blokplugins.kitroom.extra.KitEChestHolder;
import blokplugins.kitroom.extra.KitEditHolder;
import blokplugins.kitroom.menus.editechest;
import blokplugins.kitroom.menus.mainmenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.SQLException;

public class echestlistner implements Listener {
    private final PointsDatabase pointsDatabase;
    private final InventorySerializations inventorySerializer;

    public echestlistner(InventorySerializations inventorySerializer, PointsDatabase pointsDatabase) {
        this.inventorySerializer = inventorySerializer;
        this.pointsDatabase = pointsDatabase;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws SQLException {
        if(e.getInventory().getHolder() instanceof KitEChestHolder) {
            if(e.getRawSlot() >= 27 && e.getRawSlot() <= 35) {
                String title = e.getView().getTitle();
                String lastLetter = title.substring(title.length() - 1);
                Player player = (Player) e.getWhoClicked();
                String uuid = player.getUniqueId().toString();
                if (e.getRawSlot() == 35) {
                    new mainmenu(player);
                } else if(e.getRawSlot() == 33) {
                    new editechest(player, Integer.valueOf(lastLetter), player.getEnderChest(), null);
                } else if (e.getRawSlot() == 32) {
                    inventorySerializer.serializeInventory(e.getClickedInventory(), e.getWhoClicked().getUniqueId().toString(), "EC " + lastLetter, false);
                } else if (e.getRawSlot() == 34 && e.isShiftClick()) {
                    pointsDatabase.deleteKit(uuid, "EC " + lastLetter);
                }
                e.setCancelled(true);
            }
        }
    }
}
