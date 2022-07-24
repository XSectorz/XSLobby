package panat.xsectorz.core;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import panat.xsectorz.configuration.loadConfig;
import panat.xsectorz.listeners.onChat;
import panat.xsectorz.listeners.onFall;
import panat.xsectorz.listeners.onJoin;
import panat.xsectorz.listeners.onModify;

public final class XSLobby extends JavaPlugin {

    public static XSLobby plugin;
    public static LuckPerms luckPerms = LuckPermsProvider.get();

    public static XSLobby getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("§e***************************");
        Bukkit.getLogger().info("§r");
        Bukkit.getLogger().info("§a    XSLobby - v1.0");
        Bukkit.getLogger().info("§e   Status: §aEnabled");
        Bukkit.getLogger().info("§c   By Panat Xsectorz");
        Bukkit.getLogger().info("§r");
        Bukkit.getLogger().info("§e***************************");

        plugin = this;

        Bukkit.getPluginManager().registerEvents(new onChat(),this);
        Bukkit.getPluginManager().registerEvents(new onJoin(),this);
        Bukkit.getPluginManager().registerEvents(new onModify(),this);
        Bukkit.getPluginManager().registerEvents(new onFall(),this);

        new loadConfig();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("§e***************************");
        Bukkit.getLogger().info("§r");
        Bukkit.getLogger().info("§a    XSLobby - v1.0");
        Bukkit.getLogger().info("§e   Status: §cDisabled");
        Bukkit.getLogger().info("§c   By Panat Xsectorz");
        Bukkit.getLogger().info("§r");
        Bukkit.getLogger().info("§e***************************");

    }
}
