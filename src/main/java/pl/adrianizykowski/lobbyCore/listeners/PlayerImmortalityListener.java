package pl.adrianizykowski.lobbyCore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.entity.Player;
import pl.adrianizykowski.lobbyCore.LobbyCore;

public class PlayerImmortalityListener implements Listener {

    private final LobbyCore plugin;

    public PlayerImmortalityListener(LobbyCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        boolean immortalityEnabled = plugin.getCustomConfig().getBoolean("settings.immortality");
        if (!immortalityEnabled) return;


        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
            ((Player) event.getEntity()).setHealth(20.0);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {

        boolean immortalityEnabled = plugin.getCustomConfig().getBoolean("settings.immortality");
        if (!immortalityEnabled) return;

        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
            ((Player) event.getEntity()).setFoodLevel(20);
            ((Player) event.getEntity()).setSaturation(20.0f);
        }
    }
}
