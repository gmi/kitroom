package blokplugins.kitroom.commands;

import blokplugins.kitroom.inventories.KitMenu;
import blokplugins.kitroom.utils.SerializeInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        Player p = (Player) sender;
        new KitMenu(p);
        return true;
    }
}
