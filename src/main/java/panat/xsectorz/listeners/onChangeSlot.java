package panat.xsectorz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import panat.xsectorz.configuration.messages;
import panat.xsectorz.core.XSLobby;
import panat.xsectorz.utils.XSUtils;

public class onChangeSlot implements Listener {

    @EventHandler
    public void onChangSlot(PlayerItemHeldEvent e) {

        Player p = e.getPlayer();

        Location coord2 = new Location(Bukkit.getWorld("Hub"),853,63,-85);
        Location coord1 = new Location(Bukkit.getWorld("Hub"),843,50,-96);

        if((p.getLocation().getBlockX() > coord1.getBlockX()) && (p.getLocation().getBlockX() < coord2.getBlockX())){
            if((p.getLocation().getBlockY() > coord1.getBlockY()) && (p.getLocation().getBlockY() < coord2.getBlockY())){
                if((p.getLocation().getBlockZ() > coord1.getBlockZ()) && (p.getLocation().getBlockZ() < coord2.getBlockZ())){
                    return;
                }
            }
        }

        if(e.getNewSlot() == 7) {
            if(!XSLobby.pvp.contains(p)) {
                XSLobby.pvp.add(p);
            }
            p.sendMessage(XSUtils.transColor(messages.customConfig.getString("pvp_on")));
            XSUtils.loadItemsPvp(p);
        } else {
            if(XSLobby.pvp.contains(p)) {
                XSLobby.pvp.remove(p);
                p.sendMessage(XSUtils.transColor(messages.customConfig.getString("pvp_off")));
            }
            if(p.getInventory().getItem(0) == null) {
                XSUtils.loadItemsJoin(p);
            }
         }
    }

}
