package blokplugins.kitroom.menus;

import blokplugins.kitroom.extra.KitAdminHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class kitroomadmin {
    public Inventory inv;
    public kitroomadmin(Player player, String tab, Inventory prekit) {
        inv = Bukkit.createInventory(new KitAdminHolder(), 54, tab);
        initializeItems();
        if (prekit != null) {
            ItemStack[] contents = prekit.getContents();
            for (int i = 0; i < contents.length; i++) {
                if (contents[i] != null && contents[i].getType() != Material.AIR) {
                    inv.setItem(i, contents[i]);
                }
            }
        }
        player.openInventory(inv);
    }

    private void initializeItems() {

        ItemStack vanillapvp = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta vanillapvpmeta = vanillapvp.getItemMeta();
        vanillapvpmeta.setDisplayName(ChatColor.DARK_PURPLE + "Vanilla PVP");
        vanillapvpmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        vanillapvp.setItemMeta(vanillapvpmeta);
        inv.setItem(47, vanillapvp);

        ItemStack dvanillapvp = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta dvanillapvpmeta = dvanillapvp.getItemMeta();
        dvanillapvpmeta.setDisplayName(ChatColor.AQUA + "Diamond Crystal");
        dvanillapvp.setItemMeta(dvanillapvpmeta);
        inv.setItem(48, dvanillapvp);

        ItemStack potions = new ItemStack(Material.SPLASH_POTION);
        ItemMeta potionmeta = potions.getItemMeta();
        potionmeta.setDisplayName(ChatColor.BLUE + "Potions");
        potions.setItemMeta(potionmeta);
        inv.setItem(49, potions);

        ItemStack armory = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta armorymeta = armory.getItemMeta();
        armorymeta.setDisplayName(ChatColor.GREEN + "Armory");
        armory.setItemMeta(armorymeta);
        inv.setItem(50, armory);

        ItemStack axe = new ItemStack(Material.SHIELD);
        ItemMeta axemeta = axe.getItemMeta();
        axemeta.setDisplayName(ChatColor.GOLD + "Axe & UHC");
        axe.setItemMeta(axemeta);
        inv.setItem(51, axe);

        ItemStack back = new ItemStack(Material.OAK_DOOR);
        ItemMeta backmeta = back.getItemMeta();
        backmeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "BACK");
        back.setItemMeta(backmeta);
        inv.setItem(53, back);

        ItemStack save = new ItemStack(Material.LIME_DYE);
        ItemMeta savemeta = save.getItemMeta();
        savemeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "SAVE");
        List<String> savelore = new ArrayList<>();
        savelore.add(ChatColor.GRAY + "- Saves current kit");
        savemeta.setLore(savelore);
        save.setItemMeta(savemeta);
        inv.setItem(45, save);

        ItemStack purpleGlass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta meta = purpleGlass.getItemMeta();
        meta.setDisplayName(" ");
        purpleGlass.setItemMeta(meta);
        inv.setItem(52, purpleGlass);
        inv.setItem(46, purpleGlass);
    }
}
