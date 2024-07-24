package panat.xsectorz.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panat.xsectorz.configuration.config;
import panat.xsectorz.utils.XSUtils;

public class XSCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        if(sender instanceof Player) {
            if(cmd.getName().equalsIgnoreCase("facebook")) {
                Player p = (Player) sender;

                TextComponent message = new TextComponent("คุณสามารถติดต่อพูดคุยกับทีมงานส่วนตัว ");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.customConfig.getString("facebook_URL")));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(XSUtils.transColor("&fกดเพื่อเปิดลิงค์")).create()));
                message.setColor(ChatColor.of("#84A0F3"));

                TextComponent message2 = new TextComponent(config.customConfig.getString("facebook_URL"));
                message2.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.customConfig.getString("facebook_URL")));
                message2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(XSUtils.transColor("&fกดเพื่อเปิดลิงค์")).create()));
                message2.setColor(ChatColor.of("#4D66D2"));
                message2.setUnderlined(true);

                TextComponent message3 = new TextComponent("❙ ");
                message3.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.customConfig.getString("facebook_URL")));
                message3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(XSUtils.transColor("&fกดเพื่อเปิดลิงค์")).create()));
                message3.setColor(ChatColor.of("#E9DC30"));

                message.addExtra(message2);
                message3.addExtra(message);

                p.sendMessage("");
                sender.spigot().sendMessage(message3);
                p.sendMessage("");
                p.sendMessage(XSUtils.transColor("&x&5&2&c&1&4&4❙ &fคุณสามารถติดต่อ &x&f&3&5&b&5&6https://discord.siamcraft.net/ &fเพื่อแก้ไขปัญหาดังกล่าวได้"));
                p.sendMessage("");
                return true;
            } else if(cmd.getName().equalsIgnoreCase("discord")) {
                Player p = (Player) sender;

                TextComponent message = new TextComponent("เข้าร่วมคอมมูนิตี้เพื่อพูดคุยแลกเปลี่ยน ");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.customConfig.getString("discord_URL")));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(XSUtils.transColor("&fกดเพื่อเปิดลิงค์")).create()));
                message.setColor(ChatColor.of("#4EE991"));

                TextComponent message2 = new TextComponent(config.customConfig.getString("discord_URL"));
                message2.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.customConfig.getString("discord_URL")));
                message2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(XSUtils.transColor("&fกดเพื่อเปิดลิงค์")).create()));
                message2.setColor(ChatColor.of("#20D02D"));
                message2.setUnderlined(true);

                TextComponent message3 = new TextComponent("❙ ");
                message3.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.customConfig.getString("discord_URL")));
                message3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(XSUtils.transColor("&fกดเพื่อเปิดลิงค์")).create()));
                message3.setColor(ChatColor.of("#EC992C"));

                message.addExtra(message2);
                message3.addExtra(message);

                p.sendMessage("");
                sender.spigot().sendMessage(message3);
                p.sendMessage("");
                p.sendMessage(XSUtils.transColor("&x&e&3&4&1&e&c❙ &fคุณสามารถติดต่อ &x&f&3&5&b&5&6https://discord.siamcraft.net/ &fเพื่อแก้ไขปัญหาดังกล่าวได้"));
                p.sendMessage("");
                return true;
            } else if(cmd.getName().equalsIgnoreCase("spawn")) {
                Player p = (Player) sender;
                if(config.customConfig.getString("configuration_mode").equalsIgnoreCase("creative")) {
                    XSUtils.spawn(p);
                }
            }
        } else {

        }

        return false;
    }
}
