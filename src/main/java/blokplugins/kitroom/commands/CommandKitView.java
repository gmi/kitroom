package blokplugins.kitroom.commands;

import blokplugins.kitroom.menus.mainmenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandKitView implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("only players can use  this");
        }
        Player p = (Player) commandSender;
        if(!p.hasPermission("kitroom.viewothers")) {
            return true;
        }
        if(strings.length != 1) {
            p.sendMessage("Correct usage /kitview <username>");
            return true;
        }
        Player target = Bukkit.getPlayer(strings[0]);
        new mainmenu(p, target);

        return true;
    }
}
