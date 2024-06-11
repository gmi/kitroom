package blokplugins.kitroom.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class test implements CommandExecutor {
    public Inventory inv;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            inv = Bukkit.createInventory(null, 54, player.getDisplayName() + "'s kits");
            initializeItems();
            player.openInventory(inv);
            return true;

        } else {
            sender.sendMessage("can only be ran by player");
            return false;
        }
    }
    public void initializeItems() {
        ItemStack purpleGlass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta meta = purpleGlass.getItemMeta();
        meta.setDisplayName(null);
        purpleGlass.setItemMeta(meta);
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, purpleGlass);
        }
        for (int i = 44; i < 54; i++) {
            inv.setItem(i, purpleGlass);
        }
        inv.setItem(36, purpleGlass);
    }
}
