package blokplugins.kitroom.inventories;

import blokplugins.kitroom.Kitroom;
import blokplugins.kitroom.holders.EChestEditorHolder;
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

public class EChestEditor {
    public Inventory inv;
    static SerializeInventory serializeInventory = new SerializeInventory();
    private static CreateItem createItem = new CreateItem();

    public EChestEditor(Player p, Integer echest, Inventory prekit) {
        inv = Bukkit.createInventory(new EChestEditorHolder(), 36, ChatColor.LIGHT_PURPLE + "Ender Chest: " + String.valueOf(echest));
        InitializeItems();
        String userkit = Kitroom.getDbManager().getKit(p, "ec" + echest);
        if (userkit != null) {
            InitializeItemsKit(userkit);
        }
        if (prekit != null) {
            for(int i = 0; i < 27; i++) {
                inv.setItem(i, prekit.getItem(i));
            }
        }
        p.openInventory(inv);
    }

    public void InitializeItems() {
        ItemStack filler = createItem.CreateItem(Material.PURPLE_STAINED_GLASS_PANE, " ");
        for(int i = 27; i <= 31; i++) {
            inv.setItem(i, filler);
        }

        List<String> chestlore = new ArrayList<>();
        chestlore.add(ChatColor.GRAY + "- Imports current ender chest");
        ItemStack chest = createItem.CreateItem(Material.CHEST, ChatColor.GREEN + "" + ChatColor.BOLD + "IMPORT", chestlore);
        inv.setItem(33, chest);

        List<String> savelore = new ArrayList<>();
        savelore.add(ChatColor.GRAY + "- Saves current ender chest");
        ItemStack save = createItem.CreateItem(Material.LIME_DYE, ChatColor.GREEN + "" + ChatColor.BOLD + "SAVE", savelore);
        inv.setItem(32, save);

        List<String> clearlore = new ArrayList<>();
        clearlore.add(ChatColor.GRAY + "- Shift Click to clear");
        ItemStack clear = createItem.CreateItem(Material.BARRIER, ChatColor.RED + "" + ChatColor.BOLD + "CLEAR ENDER CHEST", clearlore);
        inv.setItem(34, clear);

        ItemStack back = createItem.CreateItem(Material.OAK_DOOR, ChatColor.RED + "" + ChatColor.BOLD + "BACK");
        inv.setItem(35, back);
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
