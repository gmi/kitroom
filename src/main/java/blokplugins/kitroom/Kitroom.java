package blokplugins.kitroom;

import blokplugins.kitroom.commands.CommandKit;
import blokplugins.kitroom.commands.CommandKitroom;
import blokplugins.kitroom.extra.FilesStartup;
import blokplugins.kitroom.listners.kitroomitemslistner;
import blokplugins.kitroom.listners.mainmenulistner;
import blokplugins.kitroom.testing.InventorySaver;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public final class Kitroom extends JavaPlugin {
    @Override
    public void onEnable() {
        List<String> filePaths = Arrays.asList("vanillapvp.json", "diamoncrystal.json", "potions.json", "armory.json", "axe.json");
        FilesStartup filesstartup = new FilesStartup(getLogger(), getDataFolder());
        filesstartup.createFiles(filePaths);
        this.getCommand("y").setExecutor(new xxx());
        this.getCommand("x").setExecutor(new InventorySaver());
        this.getCommand("kit").setExecutor(new CommandKit());
        this.getCommand("kitroom").setExecutor(new CommandKitroom());
        getServer().getPluginManager().registerEvents(new mainmenulistner(), this);
        getServer().getPluginManager().registerEvents(new kitroomitemslistner(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
