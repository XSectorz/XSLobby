package panat.xsectorz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import panat.xsectorz.configuration.config;
import panat.xsectorz.core.XSLobby;

public class onQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("lobby")) {
            if (XSLobby.pvp.contains(p)) {
                XSLobby.pvp.remove(p);
            }
        }
        e.setQuitMessage(null);
    }
}
