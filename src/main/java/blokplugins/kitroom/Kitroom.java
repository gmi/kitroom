package blokplugins.kitroom;

import blokplugins.kitroom.commands.kit;
import blokplugins.kitroom.listners.kitroomitemslistner;
import blokplugins.kitroom.listners.mainmenulistner;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kitroom extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("kit").setExecutor(new kit());
        getServer().getPluginManager().registerEvents(new mainmenulistner(), this);
        getServer().getPluginManager().registerEvents(new kitroomitemslistner(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
