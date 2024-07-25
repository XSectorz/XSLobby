package panat.xsectorz.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import panat.xsectorz.configuration.messages;

import java.util.ArrayList;
import java.util.Arrays;

public class onPlace implements Listener {

    private static ArrayList<Material> placeMaterial = new ArrayList<>(Arrays.asList(Material.OBSERVER,Material.COMPARATOR,Material.REPEATER,Material.REDSTONE,Material.DETECTOR_RAIL,Material.REDSTONE_TORCH));

    public static ArrayList<Material> getPlaceMaterial() {
        return placeMaterial;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        Player p = e.getPlayer();

        if(!p.hasPermission("xsapi.staff")) {
            if(getPlaceMaterial().contains(e.getBlockPlaced().getType())) {
                p.sendMessage(messages.customConfig.getString("deny_place").replace('&','§'));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(!p.hasPermission("xsapi.staff")) {
            if(p.getInventory().getItemInMainHand() != null && (getPlaceMaterial().contains(p.getInventory().getItemInMainHand().getType()))) {
                p.sendMessage(messages.customConfig.getString("deny_place").replace('&','§'));
                e.setCancelled(true);
            } else if(p.getInventory().getItemInOffHand() != null && (getPlaceMaterial().contains(p.getInventory().getItemInOffHand().getType()))) {
                p.sendMessage(messages.customConfig.getString("deny_place").replace('&','§'));
                e.setCancelled(true);
            }
        }
    }
}
