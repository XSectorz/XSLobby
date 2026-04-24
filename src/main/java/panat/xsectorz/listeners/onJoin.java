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

import java.util.HashMap;
import java.util.UUID;

public class onJoin implements Listener {
    HashMap<UUID,Long> data = new HashMap<>();
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("lobby")) {
            XSUtils.loadItemsJoin(p);


            if(config.customConfig.getBoolean("force_resource.enable")) {
                if(data.containsKey(p.getUniqueId())) {
                    if(System.currentTimeMillis() - data.get(p.getUniqueId()) >= 10000L) {
                        p.setResourcePack(config.customConfig.getString("force_resource.resource"));
                    }
                } else {
                    p.setResourcePack(config.customConfig.getString("force_resource.resource"));
                }
                data.put(p.getUniqueId(),System.currentTimeMillis());
            }


        } else if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("creative")) {
            XSUtils.loadCreativeItems(p);
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(XSLobby.getPlugin(), new Runnable() {
            @Override
            public void run() {
                XSUtils.spawn(p);
            }
        }, 10L);

        e.setJoinMessage(null);

    }

}
