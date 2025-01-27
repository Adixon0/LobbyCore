package pl.adrianizykowski.lobbyCore.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import pl.adrianizykowski.lobbyCore.LobbyCore;

import java.util.EnumSet;
import java.util.Set;

public class EntitySpawnListener implements Listener {

    private final LobbyCore plugin;

    private static final Set<EntityType> ANIMALS = EnumSet.of(
            EntityType.COW, EntityType.SHEEP, EntityType.CHICKEN, EntityType.PIG,
            EntityType.HORSE, EntityType.RABBIT, EntityType.CAT, EntityType.DONKEY,
            EntityType.MULE, EntityType.LLAMA, EntityType.FOX, EntityType.WOLF,
            EntityType.PARROT, EntityType.BEE, EntityType.PANDA, EntityType.GOAT
    );

    private static final Set<EntityType> MONSTERS = EnumSet.of(
            EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER,
            EntityType.SPIDER, EntityType.ENDERMAN, EntityType.WITCH,
            EntityType.BLAZE, EntityType.GHAST, EntityType.SLIME, EntityType.MAGMA_CUBE,
            EntityType.PILLAGER, EntityType.VINDICATOR, EntityType.EVOKER,
            EntityType.DROWNED, EntityType.HUSK, EntityType.STRAY
    );

    public EntitySpawnListener(LobbyCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {

        boolean disableAnimals = plugin.getCustomConfig().getBoolean("settings.disable-animals");
        boolean disableMonsters = plugin.getCustomConfig().getBoolean("settings.disable-monsters");

        EntityType entityType = event.getEntityType();

        if (disableAnimals && ANIMALS.contains(entityType)) {
            event.setCancelled(true);
        }

        if (disableMonsters && MONSTERS.contains(entityType)) {
            event.setCancelled(true);
        }
    }
}
