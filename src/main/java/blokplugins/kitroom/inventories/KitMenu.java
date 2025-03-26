package blokplugins.kitroom.inventories;

import blokplugins.kitroom.holders.KitMenuHolder;
import blokplugins.kitroom.utils.CreateItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitMenu {
    private static CreateItem createItem = new CreateItem();
    public Inventory inv;
    public KitMenu(Player p) {
        inv = Bukkit.createInventory(new KitMenuHolder(), 45, ChatColor.LIGHT_PURPLE + p.getDisplayName() + "'s kits");
        InitializeItems(p.hasPermission("kitroom.admin"));
        p.openInventory(inv);
    }

    public void InitializeItems(Boolean isAdmin) {
        ItemStack filler = createItem.CreateItem(Material.PURPLE_STAINED_GLASS_PANE, " ");
        for (int i = 0; i < 45; i++) {
            inv.setItem(i, filler);
        }

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
            metaechest.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "ENDERCHEST " + Integer.toString(i-17));
            List<String> echestlore = new ArrayList<>();
            echestlore.add(ChatColor.GRAY + "- Left click to load ender chest");
            echestlore.add(ChatColor.GRAY + "- Right click to edit ender chest");
            metaechest.setLore(echestlore);
            echest.setItemMeta(metaechest);
            inv.setItem(i, echest);
        }

        ItemStack kitroom = createItem.CreateItem(Material.NETHER_STAR, ChatColor.GREEN + "" + ChatColor.BOLD + "KIT ROOM");
        inv.setItem(30, kitroom);

        List<String> infolore = new ArrayList<>();
        infolore.add(ChatColor.GRAY + "- Click a kit slot to load your kit");
        infolore.add(ChatColor.GRAY + "- Right click a kit slot to edit your kit");
        ItemStack info = createItem.CreateItem(Material.OAK_SIGN, ChatColor.GREEN + "" + ChatColor.BOLD + "INFO", infolore);
        inv.setItem(31, info);

        List<String> clearlore = new ArrayList<>();
        clearlore.add(ChatColor.GRAY + "- Shift Click");
        ItemStack clear = createItem.CreateItem(Material.REDSTONE_BLOCK, ChatColor.RED + "" + ChatColor.BOLD + "CLEAR INVENTORY", clearlore);
        inv.setItem(32, clear);

        if (isAdmin) {
            ItemStack admin = createItem.CreateItem(Material.ENCHANTED_GOLDEN_APPLE, ChatColor.RED + "" + ChatColor.BOLD + "Admin Tools");
            inv.setItem(40, admin);
        }
    }

}
