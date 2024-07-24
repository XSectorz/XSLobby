package panat.xsectorz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import panat.xsectorz.configuration.config;
import panat.xsectorz.utils.XSUtils;

public class onDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

         Player p = e.getEntity();

         if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("creative")) {
             XSUtils.spawn(p);
         }
    }
}
