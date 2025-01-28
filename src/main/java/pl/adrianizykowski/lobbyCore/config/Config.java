package pl.adrianizykowski.lobbyCore.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Config {

    private final File configFile;
    private final FileConfiguration config;
    private final Logger logger;

    public Config(String fileName, Logger logger) {
        this.configFile = new File("plugins/LobbyCore", fileName);
        this.config = new YamlConfiguration();
        this.logger = logger;

        createOrLoadConfig();
    }

    private void createOrLoadConfig() {
        if (!configFile.exists()) {
            try {
                configFile.getParentFile().mkdirs();
                if (configFile.createNewFile()) {
                    logger.info("Utworzono nowy plik konfiguracyjny: " + configFile.getName());
                }
                saveDefaultConfig();
            } catch (IOException e) {
                logger.severe("Nie udało się utworzyć pliku konfiguracyjnego: " + configFile.getName());
                e.printStackTrace();
            }
        }

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            logger.severe("Błąd ładowania pliku konfiguracyjnego: " + configFile.getName());
            e.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        config.options().copyDefaults(true);

        config.addDefault("messages.join.enabled", true);
        config.addDefault("messages.join.text", "&aWitaj na serwerze, {PLAYER}!");
        config.addDefault("messages.quit.enabled", true);
        config.addDefault("messages.quit.text", "&c{PLAYER} opuścił serwer.");
        config.addDefault("messages.command-blocked", "&cTa komenda jest zablokowana na tym serwerze!");
        config.addDefault("messages.spawn-set", "&aSpawn został pomyślnie ustawiony!");
        config.addDefault("title.enabled", true);
        config.addDefault("title.text", "&6Witaj, {PLAYER}!");
        config.addDefault("title.subtitle", "&eMiłej gry na naszym serwerze!");
        config.addDefault("sound.enabled", true);
        config.addDefault("sound.name", "ENTITY_PLAYER_LEVELUP");
        config.addDefault("sound.volume", 1.0);
        config.addDefault("sound.pitch", 1.0);
        config.addDefault("settings.immortality", true);
        config.addDefault("settings.disable-animals", true);
        config.addDefault("settings.disable-monsters", true);
        config.addDefault("settings.blocked-commands", List.of("/pl", "/plugins", "/version", "/ver"));
        config.addDefault("settings.command-bypass-permission", "lobbycore.bypass.commands");
        config.addDefault("settings.setspawn-permission", "lobbycore.setspawn");
        config.addDefault("settings.teleport-on-join", true);
        config.addDefault("spawn.x", 0.0);
        config.addDefault("spawn.y", 90.0);
        config.addDefault("spawn.z", 0.0);
        config.addDefault("spawn.yaw", 0.0);
        config.addDefault("spawn.pitch", 0.0);
        config.addDefault("settings.disable-rain", true);
        config.addDefault("settings.lock-time", true);
        config.addDefault("settings.time", "day");


        saveConfig();
    }



    public void saveConfig() {
        try {
            config.save(configFile);
            logger.info("Zapisano zmiany w pliku konfiguracyjnym: " + configFile.getName());
        } catch (IOException e) {
            logger.severe("Błąd podczas zapisywania pliku konfiguracyjnego: " + configFile.getName());
            e.printStackTrace();
        }
    }

    public String getString(String path) {
        return config.getString(path, "");
    }

    public int getInt(String path) {
        return config.getInt(path, 0);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path, false);
    }

    public double getDouble(String path) {
        return config.getDouble(path, 0.0);
    }

    public void set(String path, Object value) {
        config.set(path, value);
        saveConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
