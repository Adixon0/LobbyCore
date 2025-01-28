package pl.adrianizykowski.lobbyCore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;
import pl.adrianizykowski.lobbyCore.LobbyCore;

public class PlayerJoinQuitListener implements Listener {

    private final LobbyCore plugin;

    public PlayerJoinQuitListener(LobbyCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);

        boolean teleportOnJoin = plugin.getConfig().getBoolean("settings.teleport-on-join", true);
        if (teleportOnJoin) {
            double spawn_x = plugin.getConfig().getDouble("spawn.x", player.getWorld().getSpawnLocation().getX());
            double spawn_y = plugin.getConfig().getDouble("spawn.y", player.getWorld().getSpawnLocation().getY());
            double spawn_z = plugin.getConfig().getDouble("spawn.z", player.getWorld().getSpawnLocation().getZ());
            float spawn_yaw = (float) plugin.getConfig().getDouble("spawn.yaw", player.getLocation().getYaw());
            float spawn_pitch = (float) plugin.getConfig().getDouble("spawn.pitch", player.getLocation().getPitch());

            player.teleport(new Location(player.getWorld(), spawn_x, spawn_y, spawn_z, spawn_yaw, spawn_pitch));
        }

        boolean joinEnabled = plugin.getCustomConfig().getBoolean("messages.join.enabled");
        if (joinEnabled) {
            String joinMessage = plugin.getCustomConfig().getString("messages.join.text");
            joinMessage = joinMessage.replace("{PLAYER}", player.getName());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
        }

        boolean titleEnabled = plugin.getCustomConfig().getBoolean("title.enabled");
        if (titleEnabled) {
            String title = plugin.getCustomConfig().getString("title.text");
            String subtitle = plugin.getCustomConfig().getString("title.subtitle");
            title = ChatColor.translateAlternateColorCodes('&', title.replace("{PLAYER}", player.getName()));
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle.replace("{PLAYER}", player.getName()));

            player.sendTitle(title, subtitle, 10, 70, 20);
        }

        // Odtwarzanie dźwięku
        boolean soundEnabled = plugin.getCustomConfig().getBoolean("sound.enabled");
        if (soundEnabled) {
            String soundName = plugin.getCustomConfig().getString("sound.name");
            float volume = (float) plugin.getCustomConfig().getDouble("sound.volume");
            float pitch = (float) plugin.getCustomConfig().getDouble("sound.pitch");

            try {
                Sound sound = Sound.valueOf(soundName.toUpperCase());
                player.playSound(player.getLocation(), sound, volume, pitch);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Nieprawidłowy dźwięk w config.yml: " + soundName);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(null);

        boolean quitEnabled = plugin.getCustomConfig().getBoolean("messages.quit.enabled");
        if (quitEnabled) {
            String quitMessage = plugin.getCustomConfig().getString("messages.quit.text");
            quitMessage = quitMessage.replace("{PLAYER}", player.getName());
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', quitMessage));
        }
    }
}
