package panat.xsectorz.listeners;

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

        if(e.getNewSlot() == 7) {
            if(!XSLobby.pvp.contains(p)) {
                XSLobby.pvp.add(p);
                p.sendMessage(XSUtils.transColor(messages.customConfig.getString("pvp_on")));
                XSUtils.loadItemsPvp(p);
            }
        } else {
            if(XSLobby.pvp.contains(p)) {
                XSLobby.pvp.remove(p);
                p.sendMessage(XSUtils.transColor(messages.customConfig.getString("pvp_off")));
                XSUtils.loadItemsJoin(p);
            }
         }
    }

}
