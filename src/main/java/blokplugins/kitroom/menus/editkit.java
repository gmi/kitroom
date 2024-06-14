package blokplugins.kitroom.menus;

import blokplugins.kitroom.extra.KitEditHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class editkit {
    public Inventory inv;
    public editkit(Player player, int kit, Inventory playerinv) {
        inv = Bukkit.createInventory(new KitEditHolder(), 54, ChatColor.LIGHT_PURPLE + "Kit: " + String.valueOf(kit));
        initializeItems();

        if (playerinv != null) {
            ItemStack[] contents = playerinv.getContents();
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
        inv.setItem(41, purpleGlass);
        inv.setItem(42, purpleGlass);
        inv.setItem(43, purpleGlass);
        inv.setItem(44, purpleGlass);
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "HELMET");
        helmet.setItemMeta(helmetMeta);
        inv.setItem(48, helmet);

        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "CHESTPLATE");
        chestplate.setItemMeta(chestplateMeta);
        inv.setItem(47, chestplate);

        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemMeta leggingsMeta = leggings.getItemMeta();
        leggingsMeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "LEGGINGS");
        leggings.setItemMeta(leggingsMeta);
        inv.setItem(46, leggings);

        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "BOOTS");
        boots.setItemMeta(bootsMeta);
        inv.setItem(45, boots);

        ItemStack sheild = new ItemStack(Material.SHIELD);
        ItemMeta sheildmeta = sheild.getItemMeta();
        sheildmeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "OFFHAND");
        sheild.setItemMeta(sheildmeta);
        inv.setItem(49, sheild);

        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestmeta = chest.getItemMeta();
        chestmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "IMPORT");
        List<String> chestlore = new ArrayList<>();
        chestlore.add(ChatColor.GRAY + "- Imports current inventory");
        chestmeta.setLore(chestlore);
        chest.setItemMeta(chestmeta);
        inv.setItem(51, chest);


        ItemStack save = new ItemStack(Material.LIME_DYE);
        ItemMeta savemeta = save.getItemMeta();
        savemeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "SAVE");
        List<String> savelore = new ArrayList<>();
        savelore.add(ChatColor.GRAY + "- Saves current kit");
        savemeta.setLore(savelore);
        save.setItemMeta(savemeta);
        inv.setItem(50, save);

        ItemStack clear = new ItemStack(Material.BARRIER);
        ItemMeta clearmeta = clear.getItemMeta();
        clearmeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "CLEAR KIT");
        List<String> clearlore = new ArrayList<>();
        clearlore.add(ChatColor.GRAY + "- Shift Click to clear");
        clearmeta.setLore(clearlore);
        clear.setItemMeta(clearmeta);
        inv.setItem(52, clear);

        ItemStack back = new ItemStack(Material.OAK_DOOR);
        ItemMeta backmeta = back.getItemMeta();
        backmeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "BACK");
        back.setItemMeta(backmeta);
        inv.setItem(53, back);
    }


}
