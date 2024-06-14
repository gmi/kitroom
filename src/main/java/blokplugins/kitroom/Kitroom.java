package blokplugins.kitroom;

import blokplugins.kitroom.commands.CommandKit;
import blokplugins.kitroom.commands.CommandKitroom;
import blokplugins.kitroom.database.PointsDatabase;
import blokplugins.kitroom.extra.FilesStartup;
import blokplugins.kitroom.listners.echestlistner;
import blokplugins.kitroom.listners.editkitlistnet;
import blokplugins.kitroom.listners.kitroomitemslistner;
import blokplugins.kitroom.listners.mainmenulistner;
import blokplugins.kitroom.testing.InventorySaver;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public final class Kitroom extends JavaPlugin {
    private PointsDatabase pointsDatabase;

    @Override
    public void onEnable() {
        List<String> filePaths = Arrays.asList("vanillapvp.json", "diamoncrystal.json", "potions.json", "armory.json", "axe.json");
        FilesStartup filesstartup = new FilesStartup(getLogger(), getDataFolder());
        filesstartup.createFiles(filePaths);
        this.getCommand("x").setExecutor(new InventorySaver());
        this.getCommand("kit").setExecutor(new CommandKit());
        this.getCommand("kitroom").setExecutor(new CommandKitroom());
        getServer().getPluginManager().registerEvents(new mainmenulistner(), this);
        getServer().getPluginManager().registerEvents(new editkitlistnet(), this);
        getServer().getPluginManager().registerEvents(new kitroomitemslistner(), this);
        getServer().getPluginManager().registerEvents(new echestlistner(), this);

        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            pointsDatabase = new PointsDatabase(getDataFolder().getAbsolutePath() + "/kits.db");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Fails to connect to database" + ex.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        try {
            pointsDatabase.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
