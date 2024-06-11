package blokplugins.kitroom.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class test implements CommandExecutor {
    public Inventory inv;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            inv = Bukkit.createInventory(null, 45, ChatColor.LIGHT_PURPLE + player.getDisplayName() + "'s kits");
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
        meta.setDisplayName(" ");
        purpleGlass.setItemMeta(meta);
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, purpleGlass);
        }
        for (int i = 35; i < 45; i++) {
            inv.setItem(i, purpleGlass);
        }
        inv.setItem(27, purpleGlass);

        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta metachest = chest.getItemMeta();
        for (int i = 9; i < 18; i++) {
            metachest.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "KIT " + Integer.toString(i-8));
            List<String> chestlore = new ArrayList<>();
            chestlore.add(ChatColor.GRAY + "- Left click to load kit");
            chestlore.add(ChatColor.GRAY + "- Right click to edit kit");
            metachest.setLore(chestlore);
            chest.setItemMeta(metachest);
            inv.setItem(i, chest);
        }

        ItemStack echest = new ItemStack(Material.ENDER_CHEST);
        ItemMeta metaechest = echest.getItemMeta();
        for (int i = 18; i < 27; i++) {
            metaechest.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "ENDERCHEST");
            List<String> echestlore = new ArrayList<>();
            echestlore.add(ChatColor.GRAY + "- Use /enderchest " + ChatColor.GREEN + "save" + ChatColor.GRAY + "/" + ChatColor.GOLD + "load");
            metaechest.setLore(echestlore);
            echest.setItemMeta(metaechest);
            inv.setItem(i, echest);
        }

        ItemStack kitroom = new ItemStack(Material.NETHER_STAR);
        ItemMeta kitroommeta = kitroom.getItemMeta();
        kitroommeta.setDisplayName(ChatColor.GREEN + "KIT ROOM");
        kitroom.setItemMeta(kitroommeta);
        inv.setItem(30, kitroom);

        ItemStack info = new ItemStack(Material.NETHER_STAR);
        ItemMeta infometa = info.getItemMeta();
        infometa.setDisplayName(ChatColor.GREEN + "INFO");
        List<String> infolore = new ArrayList<>();
        infolore.add(ChatColor.GRAY + "- Click a kit slot to load your kit");
        infolore.add(ChatColor.GRAY + "- Right click a kit slot to edit your kit");
        infometa.setLore(infolore);
        info.setItemMeta(infometa);
        inv.setItem(30, info);

        ItemStack clear = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta clearmeta = clear.getItemMeta();
        clearmeta.setDisplayName(ChatColor.RED + "CLEAR INVENTORY");
        List<String> clearlore = new ArrayList<>();
        clearlore.add(ChatColor.GRAY + "- Shift Click");
        clearmeta.setLore(clearlore);
        clear.setItemMeta(clearmeta);
        inv.setItem(31, clear);
    }
}
