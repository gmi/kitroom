package blokplugins.kitroom.listners;

import blokplugins.kitroom.extra.InventorySerializations;
import blokplugins.kitroom.extra.KitRoomMainHolder;
import blokplugins.kitroom.menus.editechest;
import blokplugins.kitroom.menus.editkit;
import blokplugins.kitroom.menus.kitroomitems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class mainmenulistner implements Listener {
    private final InventorySerializations inventorySerializations;

    public mainmenulistner(InventorySerializations inventorySerializations) {
        this.inventorySerializations = inventorySerializations;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getHolder() instanceof KitRoomMainHolder){
            Player player = (Player) e.getWhoClicked();
            if(e.isShiftClick() && e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
                player.getInventory().clear();
            } else if(e.getCurrentItem().getType() == Material.NETHER_STAR) {
                new kitroomitems(player);
            } else if (e.getRawSlot() >= 9 && e.getRawSlot() <= 17){
                if (e.isRightClick()){
                    handleKitClickKit(player, e.getRawSlot(), e.isRightClick());
                } else if (e.isLeftClick()) {
                    player.sendMessage("not added yet");
                }
            } else if (e.getRawSlot() >= 18 && e.getRawSlot() <= 26) {
                if (e.isRightClick()){
                    handleKitClickEchest(player, e.getRawSlot(), e.isRightClick());
                } else if (e.isLeftClick()) {
                    player.sendMessage("not added yet");
                }
            }
            e.setCancelled(true);
        }
    }
    private void handleKitClickKit(Player player, int rawSlot, boolean isRightClick) {
        String playerUUID = player.getUniqueId().toString();
        String kitName = "Kit " + (rawSlot - 8);

        Inventory deserializedInventory = inventorySerializations.deserializeInventory(playerUUID, kitName);

        if (isRightClick) {
            new editkit(player, rawSlot - 8,null, deserializedInventory);
        } else {
            player.sendMessage("Kit name: " + kitName + " for player UUID: " + playerUUID);
        }
    }
    private void handleKitClickEchest(Player player, int rawSlot, boolean isRightClick) {
        String playerUUID = player.getUniqueId().toString();
        String kitName = "EC " + (rawSlot - 17);

        Inventory deserializedInventory = inventorySerializations.deserializeInventory(playerUUID, kitName);

        if (isRightClick) {
            new editechest(player, rawSlot - 17,null, deserializedInventory);
        } else {
            player.sendMessage("Kit name: " + kitName + " for player UUID: " + playerUUID);
        }
    }
}
