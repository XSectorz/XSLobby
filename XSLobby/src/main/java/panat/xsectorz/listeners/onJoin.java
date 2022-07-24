package panat.xsectorz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import panat.xsectorz.utils.XSUtils;

public class onJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        XSUtils.spawn(p);
        XSUtils.loadItemsJoin(p);

    }

}
