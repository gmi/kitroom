package blokplugins.kitroom.commands;

import blokplugins.kitroom.Kitroom;
import blokplugins.kitroom.utils.SerializeInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandK implements CommandExecutor {
    private static SerializeInventory serializeInventory = new SerializeInventory();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        Player p = (Player) sender;
        String commandName = command.getName().toLowerCase();

        if (commandName.startsWith("k")) {

            int kitNumber = Integer.parseInt(commandName.substring(1));
            String serializedInv =  Kitroom.getDbManager().getKit(p, "k" + kitNumber);
            if (serializedInv == null) {
                p.sendMessage("kit doesnt exist");
                return true;
            }
            Inventory inv = serializeInventory.Deserialize(serializedInv);
            for (int i = 0; i < 41; i++) {
                p.getInventory().setItem(i, inv.getItem(i));
            }
            p.sendMessage(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Loaded Kit");


        } else {
            p.sendMessage(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Kit Doesnt Exist");
        }
        return true;
    }
}
