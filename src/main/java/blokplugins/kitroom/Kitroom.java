package blokplugins.kitroom;

import blokplugins.kitroom.commands.CommandEchestLoader;
import blokplugins.kitroom.commands.CommandKit;
import blokplugins.kitroom.commands.CommandKitLoader;
import blokplugins.kitroom.commands.CommandKitroom;
import blokplugins.kitroom.database.PointsDatabase;
import blokplugins.kitroom.extra.InventorySerializations;
import blokplugins.kitroom.listners.echestlistner;
import blokplugins.kitroom.listners.editkitlistnet;
import blokplugins.kitroom.listners.kitroomitemslistner;
import blokplugins.kitroom.listners.mainmenulistner;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
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
        getServer().getPluginManager().registerEvents(new mainmenulistner(inventorySerializer), this);
        getServer().getPluginManager().registerEvents(new editkitlistnet(inventorySerializer, pointsDatabase), this);
        getServer().getPluginManager().registerEvents(new kitroomitemslistner(), this);
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
