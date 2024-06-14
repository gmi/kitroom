package blokplugins.kitroom.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.ShulkerBox;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventorySerializations implements CommandExecutor {

    private final JavaPlugin plugin;
    private final Gson gson;

    public InventorySerializations(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        Inventory inventory = player.getInventory();

        List<Map<String, Object>> inventoryData = new ArrayList<>();

        for (int slot = 0; slot < inventory.getSize(); slot++) {
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

        String json = gson.toJson(inventoryData);
        try (FileWriter writer = new FileWriter(plugin.getDataFolder() + "/inventory.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage("An error occurred while saving the inventory.");
            return true;
        }

        player.sendMessage("Inventory saved to inventory.json");
        return true;
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
