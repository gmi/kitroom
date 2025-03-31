package blokplugins.kitroom.commands;

import blokplugins.kitroom.inventories.KitRoom;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandKitadmin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("you must be a player to do this");
            return true;
        }

        Player p = (Player) commandSender;

        if(!p.hasPermission("kitroom.admin")) {
            p.sendMessage("You do not have permission for this");
            return true;
        }

        new KitRoom(p, "pvp", true);

        return true;
    }
}
