package blokplugins.kitroom.extra;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
import java.util.logging.Level;

import blokplugins.kitroom.database.PointsDatabase;
import org.bukkit.potion.PotionType;

public class InventorySerializations {

    private final JavaPlugin plugin;
    private final PointsDatabase database;
    private final Gson gson;

    public InventorySerializations(JavaPlugin plugin, PointsDatabase database) {
        this.plugin = plugin;
        this.database = database;
        this.gson = new Gson();
    }

    public void serializeInventory(Inventory inventory, String playerUUID, String kitName, Integer kitsize) {
        List<Map<String, Object>> inventoryData = new ArrayList<>();
        for (int slot = 0; slot < Math.min(inventory.getSize(), kitsize); slot++) {
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
            plugin.getLogger().log(Level.SEVERE, "Failed to upload kit data", e);
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

    public Inventory deserializeInventory(String playerUUID, String kitName) {
        try {
            List<Map<String, Object>> inventoryData = database.getKitData(playerUUID, kitName);
            if (inventoryData == null) {
                return null;
            }

            Inventory inventory = Bukkit.createInventory(null, 54);
            for (Map<String, Object> itemData : inventoryData) {
                int slot = ((Number) itemData.get("slot")).intValue();
                String type = (String) itemData.get("type");
                int amount = ((Number) itemData.get("amount")).intValue();

                ItemStack item = new ItemStack(Material.valueOf(type), amount);
                ItemMeta meta = item.getItemMeta();

                if (itemData.containsKey("displayName")) {
                    meta.setDisplayName((String) itemData.get("displayName"));
                }

                if (itemData.containsKey("lore")) {
                    meta.setLore((List<String>) itemData.get("lore"));
                }

                if (itemData.containsKey("enchantments")) {
                    Map<String, Double> enchantments = (Map<String, Double>) itemData.get("enchantments");
                    for (Map.Entry<String, Double> enchantment : enchantments.entrySet()) {
                        meta.addEnchant(Enchantment.getByKey(org.bukkit.NamespacedKey.minecraft(enchantment.getKey())), enchantment.getValue().intValue(), true);
                    }
                }

                if (meta instanceof EnchantmentStorageMeta && itemData.containsKey("storedEnchantments")) {
                    Map<String, Double> storedEnchantments = (Map<String, Double>) itemData.get("storedEnchantments");
                    for (Map.Entry<String, Double> enchantment : storedEnchantments.entrySet()) {
                        ((EnchantmentStorageMeta) meta).addStoredEnchant(Enchantment.getByKey(org.bukkit.NamespacedKey.minecraft(enchantment.getKey())), enchantment.getValue().intValue(), true);
                    }
                }

                if (meta instanceof PotionMeta && itemData.containsKey("potionType")) {
                    PotionData potionData = new PotionData(PotionType.valueOf((String) itemData.get("potionType")));
                    ((PotionMeta) meta).setBasePotionData(potionData);
                }

                if (meta instanceof BlockStateMeta && itemData.containsKey("shulkerContents")) {
                    ShulkerBox shulkerBox = (ShulkerBox) ((BlockStateMeta) meta).getBlockState();
                    Inventory shulkerInventory = shulkerBox.getInventory();
                    List<Map<String, Object>> shulkerContents = (List<Map<String, Object>>) itemData.get("shulkerContents");

                    for (Map<String, Object> shulkerItemData : shulkerContents) {
                        int shulkerSlot = ((Number) shulkerItemData.get("slot")).intValue();
                        String shulkerType = (String) shulkerItemData.get("type");
                        int shulkerAmount = ((Number) shulkerItemData.get("amount")).intValue();

                        ItemStack shulkerItem = new ItemStack(Material.valueOf(shulkerType), shulkerAmount);
                        ItemMeta shulkerMeta = shulkerItem.getItemMeta();

                        if (shulkerItemData.containsKey("displayName")) {
                            shulkerMeta.setDisplayName((String) shulkerItemData.get("displayName"));
                        }

                        if (shulkerItemData.containsKey("lore")) {
                            shulkerMeta.setLore((List<String>) shulkerItemData.get("lore"));
                        }

                        if (shulkerItemData.containsKey("enchantments")) {
                            Map<String, Double> shulkerEnchantments = (Map<String, Double>) shulkerItemData.get("enchantments");
                            for (Map.Entry<String, Double> enchantment : shulkerEnchantments.entrySet()) {
                                shulkerMeta.addEnchant(Enchantment.getByKey(org.bukkit.NamespacedKey.minecraft(enchantment.getKey())), enchantment.getValue().intValue(), true);
                            }
                        }

                        if (shulkerMeta instanceof EnchantmentStorageMeta && shulkerItemData.containsKey("storedEnchantments")) {
                            Map<String, Double> shulkerStoredEnchantments = (Map<String, Double>) shulkerItemData.get("storedEnchantments");
                            for (Map.Entry<String, Double> enchantment : shulkerStoredEnchantments.entrySet()) {
                                ((EnchantmentStorageMeta) shulkerMeta).addStoredEnchant(Enchantment.getByKey(org.bukkit.NamespacedKey.minecraft(enchantment.getKey())), enchantment.getValue().intValue(), true);
                            }
                        }

                        if (shulkerMeta instanceof PotionMeta && shulkerItemData.containsKey("potionType")) {
                            PotionData shulkerPotionData = new PotionData(PotionType.valueOf((String) shulkerItemData.get("potionType")));
                            ((PotionMeta) shulkerMeta).setBasePotionData(shulkerPotionData);
                        }

                        shulkerItem.setItemMeta(shulkerMeta);
                        shulkerInventory.setItem(shulkerSlot, shulkerItem);
                    }

                    shulkerBox.update();
                    ((BlockStateMeta) meta).setBlockState(shulkerBox);
                }

                item.setItemMeta(meta);
                inventory.setItem(slot, item);
            }

            return inventory;
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to retrieve kit data", e);
        }

        return null;
    }
}