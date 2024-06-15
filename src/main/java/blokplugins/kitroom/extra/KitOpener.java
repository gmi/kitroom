package blokplugins.kitroom.extra;

import blokplugins.kitroom.extra.InventorySerializations;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class KitOpener {

    private final InventorySerializations inventorySerializations;

    public KitOpener(InventorySerializations inventorySerializations) {
        this.inventorySerializations = inventorySerializations;
    }

    public Inventory getKitInventory(Player player, String kitName) {
        String playerUUID = player.getUniqueId().toString();
        Inventory inventory = inventorySerializations.deserializeInventory(playerUUID, kitName);

        if (inventory != null) {
            return inventory;
        } else {
            player.sendMessage("Failed to load kit.");
            return null;
        }
    }
}
