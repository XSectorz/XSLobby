package panat.xsectorz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import panat.xsectorz.configuration.messages;
import panat.xsectorz.core.XSLobby;
import panat.xsectorz.utils.XSUtils;

public class onDamage implements Listener {

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {

        if(e.getDamager() instanceof Player) {
            if(e.getEntity() instanceof Player) {

                Player target = (Player) e.getEntity();


                Location coord2 = new Location(Bukkit.getWorld("Hub"),853,63,-85);
                Location coord1 = new Location(Bukkit.getWorld("Hub"),843,50,-96);

                if((target.getLocation().getBlockX() > coord1.getBlockX()) && (target.getLocation().getBlockX() < coord2.getBlockX())){
                    if((target.getLocation().getBlockY() > coord1.getBlockY()) && (target.getLocation().getBlockY() < coord2.getBlockY())){
                        if((target.getLocation().getBlockZ() > coord1.getBlockZ()) && (target.getLocation().getBlockZ() < coord2.getBlockZ())){
                            return;
                        }
                    }
                }

                if(!XSLobby.pvp.contains(target)) {
                    e.setCancelled(true);
                }

                Player attacker = (Player) e.getDamager();

                if(!XSLobby.pvp.contains(attacker)) {
                    e.setCancelled(true);
                }

                if(target.getHealth()-e.getFinalDamage() <= 0) {
                    XSUtils.spawn(target);
                    XSUtils.loadItemsJoin(target);
                    if(XSLobby.pvp.contains(target)) {
                        XSLobby.pvp.remove(target);
                    }
                    Bukkit.broadcastMessage(XSUtils.transColor(messages.customConfig.getString("pvp_death")
                            .replace("%killer%",attacker.getName()).replace("%death%",target.getName())));
                    e.setCancelled(true);
                }

            }
        }

    }

}
