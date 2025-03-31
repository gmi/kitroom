package blokplugins.kitroom;

import blokplugins.kitroom.commands.CommandEc;
import blokplugins.kitroom.commands.CommandK;
import blokplugins.kitroom.commands.CommandKit;
import blokplugins.kitroom.commands.CommandKitadmin;
import blokplugins.kitroom.database.DatabaseManager;
import blokplugins.kitroom.database.MySQL;
import blokplugins.kitroom.database.SQLite;
import blokplugins.kitroom.listeners.InventoryClickEventListener;
import blokplugins.kitroom.listeners.PlayerJoinEventListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Kitroom extends JavaPlugin {
    private static DatabaseManager databaseManager;
    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        saveResource("config.yml", false);
        saveDefaultConfig();

        getCommand("kit").setExecutor(new CommandKit());
        for (int i = 1; i <= 9; i++) {
            this.getCommand("k" + i).setExecutor(new CommandK());
        }
        for (int i = 1; i <= 9; i++) {
            this.getCommand("ec" + i).setExecutor(new CommandEc());
        }
        getCommand("kitadmin").setExecutor(new CommandKitadmin());

        getServer().getPluginManager().registerEvents(new InventoryClickEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(), this);

        String databaseType = getConfig().getString("databasetype");
        switch (databaseType.toLowerCase()) {
            case "sqlite":
                databaseManager = new SQLite(this);
                break;
            case "mysql":
                databaseManager = new MySQL(this);
                break;
            default:
                getLogger().severe("[config] Invalid database type, plugin is disabled");
                getServer().getPluginManager().disablePlugin(this);
                return;
        }
        getServer().getScheduler().runTaskAsynchronously(this, () -> {
            databaseManager.connect();
        });
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.disconnect();
        }
    }

    public static DatabaseManager getDbManager() {
        return databaseManager;
    }
}
