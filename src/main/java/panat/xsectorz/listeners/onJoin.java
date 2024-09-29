package panat.xsectorz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import panat.xsectorz.configuration.config;
import panat.xsectorz.core.XSLobby;
import panat.xsectorz.utils.XSUtils;

public class onJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("lobby")) {
            XSUtils.loadItemsJoin(p);


            if(config.customConfig.getBoolean("force_resource.enable")) {
                p.setResourcePack(config.customConfig.getString("force_resource.resource"));
            }


        } else if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("creative")) {
            XSUtils.loadCreativeItems(p);
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(XSLobby.getPlugin(), new Runnable() {
            @Override
            public void run() {
                XSUtils.spawn(p);
            }
        }, 2L);

        e.setJoinMessage(null);

    }

}
