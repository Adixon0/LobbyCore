package pl.adrianizykowski.lobbyCore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.adrianizykowski.lobbyCore.LobbyCore;


public class PlayerJoinQuitListener implements Listener {

    private final LobbyCore plugin;

    public PlayerJoinQuitListener(LobbyCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        event.setJoinMessage(null);


        boolean joinEnabled = plugin.getCustomConfig().getBoolean("messages.join.enabled");
        if (joinEnabled) {
            String joinMessage = plugin.getCustomConfig().getString("messages.join.text");
            joinMessage = joinMessage.replace("{PLAYER}", event.getPlayer().getName());
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        event.setQuitMessage(null);

        boolean quitEnabled = plugin.getCustomConfig().getBoolean("messages.quit.enabled");
        if (quitEnabled) {
            String quitMessage = plugin.getCustomConfig().getString("messages.quit.text");
            quitMessage = quitMessage.replace("{PLAYER}", event.getPlayer().getName());
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', quitMessage));
        }
    }
}
