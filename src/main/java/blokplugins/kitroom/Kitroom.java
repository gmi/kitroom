package blokplugins.kitroom;

import blokplugins.kitroom.commands.test;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kitroom extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("kit").setExecutor(new test());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
