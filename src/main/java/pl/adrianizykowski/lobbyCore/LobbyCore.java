package pl.adrianizykowski.lobbyCore;

import org.bukkit.plugin.java.JavaPlugin;
import pl.adrianizykowski.lobbyCore.config.Config;

public final class LobbyCore extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        getLogger().info("Plugin zostaje uruchomiony...");

        config = new Config("config.yml", getLogger());
        loadCustomConfigSettings();

        getLogger().info("Plugin został pomyślnie uruchomiony!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin zostaje wyłączony...");
    }

    private void loadCustomConfigSettings() {
        String serverName = config.getString("settings.server-name");
        int maxPlayers = config.getInt("settings.max-players");

        getLogger().info("Nazwa serwera: " + serverName);
        getLogger().info("Maksymalna liczba graczy: " + maxPlayers);
    }

    public Config getCustomConfig() {
        return config;
    }
}