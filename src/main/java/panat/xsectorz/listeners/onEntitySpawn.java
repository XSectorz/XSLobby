package panat.xsectorz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import panat.xsectorz.configuration.config;
import panat.xsectorz.utils.XSUtils;

public class onEntitySpawn implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("creative")) {
            e.setCancelled(true);
        }
    }
}
