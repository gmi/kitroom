package blokplugins.kitroom;

import blokplugins.kitroom.commands.CommandKit;
import blokplugins.kitroom.listeners.InventoryClickEventListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Kitroom extends JavaPlugin {
    @Override
    public void onEnable() {

        getCommand("kit").setExecutor(new CommandKit());

        getServer().getPluginManager().registerEvents(new InventoryClickEventListener(), this);

    }

    @Override
    public void onDisable() {
    }
}
