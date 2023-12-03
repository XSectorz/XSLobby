package panat.xsectorz.listeners;

import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import panat.xsectorz.configuration.messages;
import panat.xsectorz.core.XSLobby;
import panat.xsectorz.utils.XSUtils;

public class onPlayerMove implements Listener {

    @EventHandler
    public void onPlayerMoveToPad(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Block block = e.getPlayer().getWorld().getBlockAt(p.getLocation());
        if (block.getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
            p.setVelocity(p.getLocation().getDirection().multiply(1));
            p.setVelocity(new Vector(p.getVelocity().getX(), 1, p.getVelocity().getZ()));
        }

        Location coord2 = new Location(Bukkit.getWorld("Hub"),853,63,-85);
        Location coord1 = new Location(Bukkit.getWorld("Hub"),843,50,-96);

        if((p.getLocation().getBlockX() > coord1.getBlockX()) && (p.getLocation().getBlockX() < coord2.getBlockX())){
            if((p.getLocation().getBlockY() > coord1.getBlockY()) && (p.getLocation().getBlockY() < coord2.getBlockY())){
                if((p.getLocation().getBlockZ() > coord1.getBlockZ()) && (p.getLocation().getBlockZ() < coord2.getBlockZ())){
                    if(XSLobby.pvp.contains(p)) {
                        XSLobby.pvp.remove(p);
                        p.sendMessage(XSUtils.transColor(messages.customConfig.getString("pvp_off")));
                    }

                    if(!XSLobby.insideArea.contains(p)) {
                        XSLobby.insideArea.add(p);
                    }
                    return;
                }
            }
        }

        if(XSLobby.insideArea.contains(p)) {
            XSLobby.insideArea.remove(p);
            Bukkit.getScheduler().scheduleSyncDelayedTask(XSLobby.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    XSUtils.loadItemsJoin(p);
                }
            }, 2L);

        }

    }
}
