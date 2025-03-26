package blokplugins.kitroom.commands;

import blokplugins.kitroom.Kitroom;
import blokplugins.kitroom.utils.SerializeInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class CommandEc implements CommandExecutor {
    private static SerializeInventory serializeInventory = new SerializeInventory();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        Player p = (Player) sender;
        String commandName = command.getName().toLowerCase();

        if (commandName.startsWith("ec")) {
            int kitNumber = Integer.parseInt(commandName.substring(2));
            String serializedInv =  Kitroom.getDbManager().getKit(p, "ec" + kitNumber);
            Inventory inv = serializeInventory.Deserialize(serializedInv);
            for (int i = 0; i < 27; i++) {
                p.getEnderChest().setItem(i, inv.getItem(i));
            }
            p.sendMessage(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Loaded Ender Chest");
        } else {
            p.sendMessage(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Ender Chest Doesnt Exist");
        }

        return true;
    }
}
