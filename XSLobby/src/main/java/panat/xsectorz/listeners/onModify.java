package panat.xsectorz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class onModify implements Listener {

    @EventHandler
    public void modifyItemSlot(InventoryDragEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void modifyItemSlot(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void modifyItemSlot(InventoryClickEvent e) {
        if (e.getSlot() != e.getRawSlot()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void modifyItemSlot(InventoryMoveItemEvent e) {
        e.setCancelled(true);
    }
}
