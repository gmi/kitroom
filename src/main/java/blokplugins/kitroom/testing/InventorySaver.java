package blokplugins.kitroom.testing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InventorySaver implements CommandExecutor {

    public static void savePlayerInventory(Player player, String fileName) {
        // Get the player's inventory
        ItemStack[] inventoryContents = player.getInventory().getContents();

        // Convert inventory to a data structure
        Map<Integer, Map<String, Object>> serializedInventory = new HashMap<>();
        for (int i = 0; i < inventoryContents.length; i++) {
            ItemStack itemStack = inventoryContents[i];
            if (itemStack != null) {
                // Serialize each item in the inventory
                Map<String, Object> serializedItem = serializeItem(itemStack);
                serializedInventory.put(i, serializedItem);
            }
        }

        // Serialize to JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonInventory = gson.toJson(serializedInventory);

        // Save to file
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(jsonInventory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> serializeItem(ItemStack itemStack) {
        Map<String, Object> serializedItem = new HashMap<>();
        serializedItem.put("type", itemStack.getType().name());
        serializedItem.put("amount", itemStack.getAmount());
        Map<String, Integer> enchantments = new HashMap<>();
        for (Map.Entry<Enchantment, Integer> entry : itemStack.getEnchantments().entrySet()) {
            enchantments.put(entry.getKey().getKey().getKey(), entry.getValue());
        }
        serializedItem.put("enchantments", enchantments);
        return serializedItem;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        Player player = (Player) sender;
        savePlayerInventory(player, "x.json");
        return false;
    }
}
