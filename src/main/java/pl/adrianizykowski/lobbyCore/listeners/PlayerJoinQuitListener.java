package pl.adrianizykowski.lobbyCore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

            player.sendTitle(title, subtitle, 10, 70, 20); // Czas wyświetlania: fade-in, stay, fade-out
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
