package pl.adrianizykowski.lobbyCore.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.adrianizykowski.lobbyCore.LobbyCore;

public class SetSpawnCommand implements CommandExecutor {

    private final LobbyCore plugin;

    public SetSpawnCommand(LobbyCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Tę komendę może używać tylko gracz.");
            return true;
        }

        Player player = (Player) sender;

        // Sprawdzenie uprawnień
        String permission = plugin.getConfig().getString("settings.setspawn-permission");
        if (!player.hasPermission(permission) && !player.isOp()) {
            String noPermissionMessage = ChatColor.translateAlternateColorCodes('&', "&cBrak uprawnień do użycia tej komendy!");
            player.sendMessage(noPermissionMessage);
            return true;
        }

        Location playerLocation = player.getLocation();

        double x = playerLocation.getBlockX() + 0.5;
        double y = playerLocation.getBlockY();
        double z = playerLocation.getBlockZ() + 0.5;
        float yaw = playerLocation.getYaw();
        float pitch = playerLocation.getPitch();

        plugin.getConfig().set("spawn.x", x);
        plugin.getConfig().set("spawn.y", y);
        plugin.getConfig().set("spawn.z", z);
        plugin.getConfig().set("spawn.yaw", yaw);
        plugin.getConfig().set("spawn.pitch", pitch);
        plugin.saveConfig();

        String spawnSetMessage = plugin.getConfig().getString("messages.spawn-set");
        spawnSetMessage = ChatColor.translateAlternateColorCodes('&', spawnSetMessage);
        player.sendMessage(spawnSetMessage);

        return true;
    }
}
