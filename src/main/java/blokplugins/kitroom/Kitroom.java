package blokplugins.kitroom;

import blokplugins.kitroom.commands.kit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kitroom extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("kit").setExecutor(new kit());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
