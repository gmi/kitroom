package blokplugins.kitroom;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.Console;

public final class Kitroom extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("kitroom enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
