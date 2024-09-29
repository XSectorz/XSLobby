package panat.xsectorz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import panat.xsectorz.configuration.config;
import panat.xsectorz.configuration.messages;

public class onDeclineRs implements Listener {


    @EventHandler
    public void onDecline(PlayerResourcePackStatusEvent e) {

        Player p = e.getPlayer();
        if(config.customConfig.getBoolean("force_resource.enable")) {
            if(e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
                String kickMsg = "";
                for(String s : messages.customConfig.getStringList("kick_decline_rs")) {
                    kickMsg += s.replace('&','§') + "\n";
                }

                p.kickPlayer(kickMsg);
            }
        }
    }
}
