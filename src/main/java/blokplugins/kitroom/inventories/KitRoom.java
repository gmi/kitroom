package blokplugins.kitroom.inventories;

import blokplugins.kitroom.Kitroom;
import blokplugins.kitroom.holders.KitRoomHolder;
import blokplugins.kitroom.utils.CreateItem;
import blokplugins.kitroom.utils.SerializeInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitRoom {
    private static CreateItem createItem = new CreateItem();
    static SerializeInventory serializeInventory = new SerializeInventory();
    public Inventory inv;
    public KitRoom(Player p, String room, boolean isAdmin) {
        inv = Bukkit.createInventory(new KitRoomHolder(), 54, room);
        InitializeItems(isAdmin);
        String kitroompre = Kitroom.getDbManager().getKitRoom(room);
        if(kitroompre != null) {
            Inventory kitinv = serializeInventory.Deserialize(kitroompre);
            for (int i = 0; i < 45; i++) {
                if (kitinv.getItem(i) != null) {
                    inv.setItem(i, kitinv.getItem(i));
                }

            }
        }
        p.openInventory(inv);
    }
    public void InitializeItems(boolean isAdmin) {
        ItemStack filler = createItem.CreateItem(Material.PURPLE_STAINED_GLASS_PANE, " ");
        inv.setItem(46, filler); inv.setItem(52, filler);
        inv.setItem(47, filler); inv.setItem(51, filler);

        ItemStack refill = createItem.CreateItem(Material.BEACON, ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "REFILL");
        inv.setItem(45, refill);

        ItemStack back = createItem.CreateItem(Material.OAK_DOOR, ChatColor.RED + "" + ChatColor.BOLD + "BACK");
        inv.setItem(53, back);

        ItemStack armory = createItem.CreateItem(Material.POTION, ChatColor.GREEN + "Potions & Arrows");
        inv.setItem(50, armory);

        ItemStack potion = createItem.CreateItem(Material.END_CRYSTAL, ChatColor.BLUE + "Crystal PVP");
        inv.setItem(49, potion);

        ItemStack equipment = createItem.CreateItem(Material.NETHERITE_CHESTPLATE, ChatColor.AQUA + "Equiptment");
        ItemMeta eqipmentmeta = equipment.getItemMeta(); eqipmentmeta.addEnchant(Enchantment.LUCK, 1, true); eqipmentmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); equipment.setItemMeta(eqipmentmeta);
        inv.setItem(48, equipment);

        if(isAdmin) {

            List<String> saveLore = new ArrayList<>();
            saveLore.add(ChatColor.GRAY + "- Saves current kitroom");
            ItemStack save = createItem.CreateItem(Material.LIME_DYE, ChatColor.GREEN + "" + ChatColor.BOLD + "SAVE", saveLore);
            inv.setItem(45, save);
        }

    }
}
