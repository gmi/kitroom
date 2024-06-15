package blokplugins.kitroom.menus;

import blokplugins.kitroom.extra.KitEChestHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class editechest {
    public Inventory inv;
    public editechest(Player player, Integer echest, Inventory playerechest, Inventory prekit) {
        inv = Bukkit.createInventory(new KitEChestHolder(), 36, ChatColor.LIGHT_PURPLE + "Ender Chest: " + String.valueOf(echest));
        initializeItems();
        
        if (playerechest != null) {
            ItemStack[] contents = playerechest.getContents();
            for (int i = 0; i < contents.length; i++) {
                if (contents[i] != null && contents[i].getType() != Material.AIR) {
                    inv.setItem(i, contents[i]);
                }
            }
        }
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
    public void initializeItems() {
        ItemStack purpleGlass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta meta = purpleGlass.getItemMeta();
        meta.setDisplayName(" ");
        purpleGlass.setItemMeta(meta);
        for(int i = 27; i <= 31; i++) {
            inv.setItem(i, purpleGlass);
        }

        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestmeta = chest.getItemMeta();
        chestmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "IMPORT");
        List<String> chestlore = new ArrayList<>();
        chestlore.add(ChatColor.GRAY + "- Imports current ender chest");
        chestmeta.setLore(chestlore);
        chest.setItemMeta(chestmeta);
        inv.setItem(33, chest);


        ItemStack save = new ItemStack(Material.LIME_DYE);
        ItemMeta savemeta = save.getItemMeta();
        savemeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "SAVE");
        List<String> savelore = new ArrayList<>();
        savelore.add(ChatColor.GRAY + "- Saves current ender chest");
        savemeta.setLore(savelore);
        save.setItemMeta(savemeta);
        inv.setItem(32, save);

        ItemStack clear = new ItemStack(Material.BARRIER);
        ItemMeta clearmeta = clear.getItemMeta();
        clearmeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "CLEAR ENDER CHEST");
        List<String> clearlore = new ArrayList<>();
        clearlore.add(ChatColor.GRAY + "- Shift Click to clear");
        clearmeta.setLore(clearlore);
        clear.setItemMeta(clearmeta);
        inv.setItem(34, clear);

        ItemStack back = new ItemStack(Material.OAK_DOOR);
        ItemMeta backmeta = back.getItemMeta();
        backmeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "BACK");
        back.setItemMeta(backmeta);
        inv.setItem(35, back);

    }
}
