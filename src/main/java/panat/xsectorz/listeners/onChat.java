package panat.xsectorz.listeners;

import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import panat.xsectorz.configuration.config;
import panat.xsectorz.core.XSLobby;

public class onChat implements Listener {

    @EventHandler
    public void onPlayerSendChatMessages(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        return;
        //String format = config.customConfig.getString("chat_prefix");
        //User user = XSLobby.luckPerms.getPlayerAdapter(Player.class).getUser(p);

        //String prefix = user.getCachedData().getMetaData().getPrefix();

        //format = format.replace('&','§');
        //format = format.replace("<rank>",prefix.replace('&','§'));
        //format = format.replace("<name>","%s");
        //format = format.replace("<messages>","%s");

        //e.setFormat(format);

    }

}
