package panat.xsectorz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class onInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(p.getInventory().getHeldItemSlot() == 0) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"dm open server " + p.getName());
                e.setCancelled(true);
            }
        }
    }

}
