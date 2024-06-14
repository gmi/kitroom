package blokplugins.kitroom.extra;

import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blokplugins.kitroom.database.PointsDatabase;

public class InventorySerializations {

    private final JavaPlugin plugin;
    private final PointsDatabase database;

    public InventorySerializations(JavaPlugin plugin, PointsDatabase database) {
        this.plugin = plugin;
        this.database = database;
    }

    public void serializeInventory(Inventory inventory, String playerUUID, String kitName) {
        List<Map<String, Object>> inventoryData = new ArrayList<>();

        for (int slot = 0; slot < Math.min(inventory.getSize(), 41); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (item != null) {
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("slot", slot);
                itemData.put("type", item.getType().toString());
                itemData.put("amount", item.getAmount());

                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    itemData.put("displayName", meta.getDisplayName());
                    itemData.put("lore", meta.getLore());
                    itemData.put("enchantments", getEnchantments(meta));

                    if (meta instanceof EnchantmentStorageMeta) {
                        itemData.put("storedEnchantments", getStoredEnchantments((EnchantmentStorageMeta) meta));
                    }

                    if (meta instanceof PotionMeta) {
                        itemData.put("potionType", getPotionType((PotionMeta) meta));
                    }

                    if (meta instanceof BlockStateMeta && ((BlockStateMeta) meta).getBlockState() instanceof ShulkerBox) {
                        ShulkerBox shulkerBox = (ShulkerBox) ((BlockStateMeta) meta).getBlockState();
                        itemData.put("shulkerContents", getShulkerContents(shulkerBox.getInventory()));
                    }
                }

                inventoryData.add(itemData);
            }
        }

        try {
            database.uploadKit(playerUUID, kitName, inventoryData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Integer> getEnchantments(ItemMeta meta) {
        Map<String, Integer> enchantments = new HashMap<>();
        for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
            enchantments.put(entry.getKey().getKey().getKey(), entry.getValue());
        }
        return enchantments;
    }

    private Map<String, Integer> getStoredEnchantments(EnchantmentStorageMeta meta) {
        Map<String, Integer> storedEnchantments = new HashMap<>();
        for (Map.Entry<Enchantment, Integer> entry : meta.getStoredEnchants().entrySet()) {
            storedEnchantments.put(entry.getKey().getKey().getKey(), entry.getValue());
        }
        return storedEnchantments;
    }

    private String getPotionType(PotionMeta meta) {
        PotionData data = meta.getBasePotionData();
        return data.getType().toString();
    }

    private List<Map<String, Object>> getShulkerContents(Inventory shulkerInventory) {
        List<Map<String, Object>> contents = new ArrayList<>();

        for (int slot = 0; slot < shulkerInventory.getSize(); slot++) {
            ItemStack item = shulkerInventory.getItem(slot);
            if (item != null) {
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("slot", slot);
                itemData.put("type", item.getType().toString());
                itemData.put("amount", item.getAmount());

                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    itemData.put("displayName", meta.getDisplayName());
                    itemData.put("lore", meta.getLore());
                    itemData.put("enchantments", getEnchantments(meta));

                    if (meta instanceof EnchantmentStorageMeta) {
                        itemData.put("storedEnchantments", getStoredEnchantments((EnchantmentStorageMeta) meta));
                    }

                    if (meta instanceof PotionMeta) {
                        itemData.put("potionType", getPotionType((PotionMeta) meta));
                    }
                }

                contents.add(itemData);
            }
        }

        return contents;
    }
}
