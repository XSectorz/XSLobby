package panat.xsectorz.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import panat.xsectorz.configuration.config;

public class onModify implements Listener {

    @EventHandler
    public void modifyItemSlot(InventoryDragEvent e) {
        Player p = (Player) e.getWhoClicked();

        if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("creative")) {
            if(e.getInventorySlots().contains(8)) {
                e.setCancelled(true);
            }
        } else {
            if(!p.hasPermission("xsapi.developer")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void modifyItemSlot(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("creative")) {
            if(e.getItemDrop().getItemStack().getType().equals(Material.COMMAND_BLOCK)) {
                e.setCancelled(true);
            }
        } else {
            if(!p.hasPermission("xsapi.developer")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void modifyItemSlot(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("creative")) {
            if(e.getSlot() == 8) {
                e.setCancelled(true);
            }
        } else {
            if(!p.hasPermission("xsapi.developer")) {
                if (e.getSlot() != e.getRawSlot()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void modifyItemSlot(InventoryMoveItemEvent e) {
        e.setCancelled(true);
    }
}
