package blokplugins.kitroom.listners;

import blokplugins.kitroom.database.PointsDatabase;
import blokplugins.kitroom.extra.InventorySerializations;
import blokplugins.kitroom.extra.KitRoomMainHolder;
import blokplugins.kitroom.menus.editechest;
import blokplugins.kitroom.menus.editkit;
import blokplugins.kitroom.menus.kitroomadmin;
import blokplugins.kitroom.menus.kitroomitems;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class mainmenulistner implements Listener {
    private final InventorySerializations inventorySerializations;
    private final PointsDatabase pointsDatabase;

    public mainmenulistner(InventorySerializations inventorySerializations, PointsDatabase pointsDatabase) {
        this.inventorySerializations = inventorySerializations;
        this.pointsDatabase = pointsDatabase;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getHolder() instanceof KitRoomMainHolder){
            Player player = (Player) e.getWhoClicked();
            if(e.isShiftClick() && e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
                player.getInventory().clear();
            } else if(e.getCurrentItem().getType() == Material.NETHER_STAR) {
                Inventory deserializedInventory = inventorySerializations.deserializeInventory("kitroom", "vanillapvp");
                new kitroomitems(player, deserializedInventory, "vanillapvp");
            } else if (e.getRawSlot() >= 9 && e.getRawSlot() <= 17){
                if (e.isRightClick()){
                    handleKitClickKit(player, e.getRawSlot(), e.isRightClick());
                } else if (e.isLeftClick()) {
                    player.closeInventory();
                    handleKitClickKit(player, e.getRawSlot(), e.isRightClick());
                }
            } else if (e.getRawSlot() >= 18 && e.getRawSlot() <= 26) {
                if (e.isRightClick()){
                    handleKitClickEchest(player, e.getRawSlot(), e.isRightClick());
                } else if (e.isLeftClick()) {
                    player.closeInventory();
                    handleKitClickEchest(player, e.getRawSlot(), e.isRightClick());
                }
            } else if (e.getRawSlot() == 40 && player.hasPermission("kitroom.admin")) {
                Inventory deserializedInventory = inventorySerializations.deserializeInventory("kitroom", "vanillapvp");
                new kitroomadmin(player, "vanillapvp", deserializedInventory);
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
            player.getInventory().clear();
            if (deserializedInventory != null) {
                ItemStack[] contents = deserializedInventory.getContents();
                for (int i = 0; i < contents.length; i++) {
                    if (contents[i] != null && contents[i].getType() != Material.AIR) {
                        player.getInventory().setItem(i, contents[i]);
                    }
                }
                player.sendMessage(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Loaded Kit");
            }
        }
    }
    private void handleKitClickEchest(Player player, int rawSlot, boolean isRightClick) {
        String playerUUID = player.getUniqueId().toString();
        String kitName = "EC " + (rawSlot - 17);

        Inventory deserializedInventory = inventorySerializations.deserializeInventory(playerUUID, kitName);

        if (isRightClick) {
            new editechest(player, rawSlot - 17,null, deserializedInventory);
        } else {
            player.getEnderChest().clear();
            if (deserializedInventory != null) {
                ItemStack[] contents = deserializedInventory.getContents();
                for (int i = 0; i < contents.length; i++) {
                    if (contents[i] != null && contents[i].getType() != Material.AIR) {
                        player.getEnderChest().setItem(i, contents[i]);
                    }
                }
            }
            player.sendMessage(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Loaded Ender Chest");
        }
    }
}
