package blokplugins.kitroom.commands;

import blokplugins.kitroom.menus.kitroomadmin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandKitAdmin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission("kitroom.admin")) {
            return true;
        } else {
            new kitroomadmin(player, "vanillapvp");
        }
        return false;
    }
}
