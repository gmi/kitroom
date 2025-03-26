package blokplugins.kitroom.inventories;

import blokplugins.kitroom.holders.KitMenuHolder;
import blokplugins.kitroom.utils.CreateItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitRoom {
    private static CreateItem createItem = new CreateItem();
    public Inventory inv;
    public KitRoom(Player p) {
        inv = Bukkit.createInventory(new KitMenuHolder(), 45, ChatColor.LIGHT_PURPLE + p.getDisplayName() + "'s kits");
        InitializeItems();
        p.openInventory(inv);
    }
    public void InitializeItems() {
        ItemStack filler = createItem.CreateItem(Material.PURPLE_STAINED_GLASS_PANE, " ");
        inv.setItem(46, filler); inv.setItem(52, filler);

        ItemStack refill = createItem.CreateItem(Material.BEACON, ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "REFILL");
        inv.setItem(45, refill);

        ItemStack back = createItem.CreateItem(Material.OAK_DOOR, ChatColor.RED + "" + ChatColor.BOLD + "BACK");
        inv.setItem(53, back);

        ItemStack axe = createItem.CreateItem(Material.SHIELD, ChatColor.GOLD + "Axe & UHC");
        inv.setItem(51, axe);

        ItemStack armory = createItem.CreateItem(Material.NETHERITE_SWORD, ChatColor.GREEN + "Armory");
        inv.setItem(50, armory);

        ItemStack potion = createItem.CreateItem(Material.SPLASH_POTION, ChatColor.BLUE + "Potions");
        inv.setItem(49, potion);

        ItemStack dvanillapvp = createItem.CreateItem(Material.DIAMOND_CHESTPLATE, ChatColor.AQUA + "Diamond Crystal");
        inv.setItem(48, dvanillapvp);

        ItemStack vanillapvp = createItem.CreateItem(Material.NETHERITE_CHESTPLATE, ChatColor.DARK_PURPLE + "Vanilla PVP");
        inv.setItem(47, dvanillapvp);
    }
}
