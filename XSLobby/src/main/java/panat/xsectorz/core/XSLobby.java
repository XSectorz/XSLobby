package panat.xsectorz.core;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import panat.xsectorz.commands.XSCommand;
import panat.xsectorz.configuration.loadConfig;
import panat.xsectorz.listeners.*;

import java.util.ArrayList;

public final class XSLobby extends JavaPlugin {

    public static XSLobby plugin;
    public static LuckPerms luckPerms;

    public static ArrayList<Player> pvp = new ArrayList<>();

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
        Bukkit.getPluginManager().registerEvents(new onInteract(),this);
        Bukkit.getPluginManager().registerEvents(new onChangeSlot(),this);
        Bukkit.getPluginManager().registerEvents(new onQuit(),this);
        Bukkit.getPluginManager().registerEvents(new onDamage(),this);

        getCommand("facebook").setExecutor(new XSCommand());
        getCommand("discord").setExecutor(new XSCommand());

        new loadConfig();

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                luckPerms = LuckPermsProvider.get();
            }
        }, 100L);
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
