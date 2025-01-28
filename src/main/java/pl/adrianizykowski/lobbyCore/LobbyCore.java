package pl.adrianizykowski.lobbyCore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.adrianizykowski.lobbyCore.commands.SetSpawnCommand;
import pl.adrianizykowski.lobbyCore.config.Config;
import pl.adrianizykowski.lobbyCore.listeners.CommandBlockerListener;
import pl.adrianizykowski.lobbyCore.listeners.EntitySpawnListener;
import pl.adrianizykowski.lobbyCore.listeners.PlayerImmortalityListener;
import pl.adrianizykowski.lobbyCore.listeners.PlayerJoinQuitListener;
import pl.adrianizykowski.lobbyCore.managers.WeatherAndTimeManager;

public final class LobbyCore extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        getLogger().info("Plugin zostaje uruchomiony...");

        config = new Config("config.yml", getLogger());
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerImmortalityListener(this), this);
        getServer().getPluginManager().registerEvents(new EntitySpawnListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandBlockerListener(this), this);
        new WeatherAndTimeManager(this);
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));

        getLogger().info("Plugin został pomyślnie uruchomiony!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin zostaje wyłączony...");
    }


    public Config getCustomConfig() {
        return config;
    }
}