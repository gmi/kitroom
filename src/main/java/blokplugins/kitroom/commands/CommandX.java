package blokplugins.kitroom.commands;

import blokplugins.kitroom.extra.InventorySerializations;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CommandX implements CommandExecutor {
    private final JavaPlugin plugin;

    public CommandX(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be run by a player");
            return true;
        }

        Player player = (Player) commandSender;
        Inventory inventory = player.getInventory();
        InventorySerializations serializer = new InventorySerializations(plugin);

        serializer.serializeInventory(inventory, "inventory.json");

        player.sendMessage("Inventory saved to inventory.json");
        return true;
    }
}
