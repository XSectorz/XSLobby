package panat.xsectorz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import panat.xsectorz.utils.XSUtils;

public class onFall implements Listener {

    @EventHandler
    public void onFall(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        if(p.getLocation().getBlockY() <= 10) {
            XSUtils.spawn(p);
        }

    }

}
