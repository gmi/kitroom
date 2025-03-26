package blokplugins.kitroom.inventories;

import blokplugins.kitroom.Kitroom;
import blokplugins.kitroom.database.DatabaseManager;
import blokplugins.kitroom.holders.KitEditorHolder;
import blokplugins.kitroom.utils.CreateItem;
import blokplugins.kitroom.utils.SerializeInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitEditor {
    public Inventory inv;
    static SerializeInventory serializeInventory = new SerializeInventory();
    private static CreateItem createItem = new CreateItem();

    public KitEditor(Player p, int kit, Inventory prekit) {
        inv = Bukkit.createInventory(new KitEditorHolder(), 54, ChatColor.LIGHT_PURPLE + "Kit: " + String.valueOf(kit));
        String userkit = Kitroom.getDbManager().getKit(p, "k" + kit);
        if (userkit != null) {
            InitializeItemsKit(userkit);
        }
        if (prekit != null) {
            for(int i = 0; i < 41; i++) {
                inv.setItem(i, prekit.getItem(i));
            }
        }
        InitializeItems();
        p.openInventory(inv);
    }
    public void InitializeItems() {
        ItemStack filler = createItem.CreateItem(Material.PURPLE_STAINED_GLASS_PANE, " ");
        inv.setItem(41, filler); inv.setItem(42, filler); inv.setItem(43, filler); inv.setItem(44, filler);

        ItemStack helmet = createItem.CreateItem(Material.CHAINMAIL_HELMET, ChatColor.GRAY + "" + ChatColor.BOLD + "HELMET");
        ItemStack chestplate = createItem.CreateItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.GRAY + "" + ChatColor.BOLD + "CHESTPLATE");
        ItemStack leggings = createItem.CreateItem(Material.CHAINMAIL_LEGGINGS, ChatColor.GRAY + "" + ChatColor.BOLD + "LEGGINGS");
        ItemStack boots = createItem.CreateItem(Material.CHAINMAIL_BOOTS, ChatColor.GRAY + "" + ChatColor.BOLD + "BOOTS");
        ItemStack shield = createItem.CreateItem(Material.SHIELD, ChatColor.GRAY + "" + ChatColor.BOLD + "SHIELD");

        inv.setItem(49, shield); inv.setItem(48, helmet); inv.setItem(47, chestplate); inv.setItem(46, leggings); inv.setItem(45, boots);


        List<String> saveLore = new ArrayList<>();
        saveLore.add(ChatColor.GRAY + "- Saves current kit");
        ItemStack save = createItem.CreateItem(Material.LIME_DYE, ChatColor.GREEN + "" + ChatColor.BOLD + "SAVE", saveLore);
        inv.setItem(50, save);

        List<String> chestlore = new ArrayList<>();
        chestlore.add(ChatColor.GRAY + "- Imports current inventory");
        ItemStack chest = createItem.CreateItem(Material.CHEST, ChatColor.GREEN + "" + ChatColor.BOLD + "IMPORT", chestlore);
        inv.setItem(51, chest);

        List<String> clearLore = new ArrayList<>();
        clearLore.add(ChatColor.GRAY + "- Shift Click to clear");
        ItemStack clear = createItem.CreateItem(Material.BARRIER, ChatColor.RED + "" + ChatColor.BOLD + "CLEAR KIT", clearLore);
        inv.setItem(52, clear);

        ItemStack back = createItem.CreateItem(Material.OAK_DOOR, ChatColor.RED + "" + ChatColor.BOLD + "BACK", new ArrayList<>());
        inv.setItem(53, back);

    }

    public void InitializeItemsKit(String userkit) {
        Inventory kitinv = serializeInventory.Deserialize(userkit);
        for (int i = 0; i < kitinv.getSize(); i++) {
            if(kitinv.getItem(i) != null) {
                inv.setItem(i, kitinv.getItem(i));
            }

        }
    }
}
