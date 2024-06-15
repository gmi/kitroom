package blokplugins.kitroom.commands;

import blokplugins.kitroom.database.PointsDatabase;
import blokplugins.kitroom.extra.InventorySerializations;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CommandEchestLoader implements CommandExecutor {
    private final PointsDatabase pointsDatabase;
    private final InventorySerializations inventorySerializer;

    public CommandEchestLoader(InventorySerializations inventorySerializer, PointsDatabase pointsDatabase) {
        this.inventorySerializer = inventorySerializer;
        this.pointsDatabase = pointsDatabase;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        String commandName = command.getName().toLowerCase();

        if (commandName.startsWith("ec")) {
            int kitNumber = Integer.parseInt(commandName.substring(2));
            String kitname = "EC " + kitNumber;
            Inventory deserializedInventory = inventorySerializer.deserializeInventory(player.getUniqueId().toString(), kitname);
            player.getEnderChest().clear();
            if (deserializedInventory != null) {
                ItemStack[] contents = deserializedInventory.getContents();
                for (int i = 0; i < contents.length; i++) {
                    if (contents[i] != null && contents[i].getType() != Material.AIR) {
                        player.getEnderChest().setItem(i, contents[i]);
                    }
                }
                player.sendMessage(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Loaded Ender Chest");
            } else {
                player.sendMessage(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Ender Chest Doesnt Exist");
            }
            return true;
            }
        return false;
    }
}