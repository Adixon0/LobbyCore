package pl.adrianizykowski.lobbyCore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.adrianizykowski.lobbyCore.LobbyCore;

import java.util.List;

public class CommandBlockerListener implements Listener {

    private final LobbyCore plugin;

    public CommandBlockerListener(LobbyCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split(" ")[0].toLowerCase();
        List<String> blockedCommands = plugin.getCustomConfig().getConfig().getStringList("settings.blocked-commands");
        String bypassPermission = plugin.getCustomConfig().getString("settings.command-bypass-permission");

        if (blockedCommands.contains(command)) {

            if (event.getPlayer().hasPermission(bypassPermission) || event.getPlayer().isOp()) {
                return;
            }

            event.setCancelled(true);
            String blockMessage = plugin.getCustomConfig().getString("messages.command-blocked");
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', blockMessage));
        }
    }
}
