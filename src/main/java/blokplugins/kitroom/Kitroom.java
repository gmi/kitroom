package blokplugins.kitroom;

import blokplugins.kitroom.commands.CommandKit;
import blokplugins.kitroom.commands.CommandKitroom;
import blokplugins.kitroom.database.PointsDatabase;
import blokplugins.kitroom.extra.InventorySerializations;
import blokplugins.kitroom.listners.echestlistner;
import blokplugins.kitroom.listners.editkitlistnet;
import blokplugins.kitroom.listners.kitroomitemslistner;
import blokplugins.kitroom.listners.mainmenulistner;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Kitroom extends JavaPlugin {
    private PointsDatabase pointsDatabase;
    private InventorySerializations inventorySerializer;

    @Override
    public void onEnable() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            pointsDatabase = new PointsDatabase(getDataFolder().getAbsolutePath() + "/kits.db");
            inventorySerializer = new InventorySerializations(this, pointsDatabase);
        } catch (SQLException ex) {
            ex.printStackTrace();
            getLogger().severe("Failed to connect to database: " + ex.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.getCommand("kit").setExecutor(new CommandKit());
        this.getCommand("kitroom").setExecutor(new CommandKitroom());
        getServer().getPluginManager().registerEvents(new mainmenulistner(), this);
        getServer().getPluginManager().registerEvents(new editkitlistnet(inventorySerializer), this);
        getServer().getPluginManager().registerEvents(new kitroomitemslistner(), this);
        getServer().getPluginManager().registerEvents(new echestlistner(), this);
    }

    @Override
    public void onDisable() {
        try {
            if (pointsDatabase != null) {
                pointsDatabase.closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
