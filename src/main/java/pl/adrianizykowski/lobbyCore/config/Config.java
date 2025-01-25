package pl.adrianizykowski.lobbyCore.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
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

        // Example defaults
        config.addDefault("settings.server-name", "MójSerwer");
        config.addDefault("settings.max-players", 100);
        config.addDefault("messages.join-message", "&aWitaj na serwerze, {PLAYER}!");
        config.addDefault("messages.quit-message", "&c{PLAYER} opuścił serwer.");
        saveConfig();
    }

    public void saveConfig() {
        try {
            config.save(configFile);
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