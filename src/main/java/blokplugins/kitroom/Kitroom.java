package blokplugins.kitroom;

import blokplugins.kitroom.commands.*;
import blokplugins.kitroom.database.PointsDatabase;
import blokplugins.kitroom.extra.DBstartup;
import blokplugins.kitroom.extra.InventorySerializations;
import blokplugins.kitroom.listners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

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
        List<String> filePaths = Arrays.asList("vanillapvp", "diamoncrystal", "potions", "armory", "axe");
        DBstartup dBstartup = new DBstartup(getLogger(), getDataFolder(), pointsDatabase);
        try {
            dBstartup.defaultDB(filePaths);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.getCommand("kitadmin").setExecutor(new CommandKitAdmin(inventorySerializer, pointsDatabase));
        this.getCommand("kit").setExecutor(new CommandKit());
        this.getCommand("kitroom").setExecutor(new CommandKitroom());
        getServer().getPluginManager().registerEvents(new kitadminlistner(inventorySerializer, pointsDatabase), this);
        getServer().getPluginManager().registerEvents(new mainmenulistner(inventorySerializer, pointsDatabase), this);
        getServer().getPluginManager().registerEvents(new editkitlistnet(inventorySerializer, pointsDatabase), this);
        getServer().getPluginManager().registerEvents(new kitroomitemslistner(inventorySerializer, pointsDatabase), this);
        getServer().getPluginManager().registerEvents(new echestlistner(inventorySerializer, pointsDatabase), this);
        for (int i = 1; i <= 9; i++) {
            this.getCommand("k" + i).setExecutor(new CommandKitLoader(inventorySerializer, pointsDatabase));
        }
        for (int i = 1; i <= 9; i++) {
            this.getCommand("ec" + i).setExecutor(new CommandEchestLoader(inventorySerializer, pointsDatabase));
        }
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
