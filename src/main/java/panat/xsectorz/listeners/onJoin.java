package panat.xsectorz.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import panat.xsectorz.configuration.config;
import panat.xsectorz.utils.XSUtils;

public class onJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        XSUtils.spawn(p);
        if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("lobby")) {
            XSUtils.loadItemsJoin(p);
        } else if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("creative")) {
            XSUtils.loadCreativeItems(p);
        }

        e.setJoinMessage(null);

    }

}
