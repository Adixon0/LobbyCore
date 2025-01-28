package pl.adrianizykowski.lobbyCore.managers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import pl.adrianizykowski.lobbyCore.LobbyCore;

public class WeatherAndTimeManager implements Listener {

    private final LobbyCore plugin;

    public WeatherAndTimeManager(LobbyCore plugin) {
        this.plugin = plugin;
        setupWeatherAndTime();
    }

    private void setupWeatherAndTime() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    if (plugin.getConfig().getBoolean("settings.disable-rain", true)) {
                        if (world.hasStorm() || world.isThundering()) {
                            world.setStorm(false);
                            world.setThundering(false);
                        }
                    }

                    if (plugin.getConfig().getBoolean("settings.lock-time", true)) {
                        String timeSetting = plugin.getConfig().getString("settings.time", "day");
                        if ("day".equalsIgnoreCase(timeSetting)) {
                            world.setTime(1000);
                        } else if ("night".equalsIgnoreCase(timeSetting)) {
                            world.setTime(13000);
                        } else if ("none".equalsIgnoreCase(timeSetting)) {
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 100L);
    }
}
