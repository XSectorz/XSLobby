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
import java.util.HashMap;

public final class XSLobby extends JavaPlugin {

    public static XSLobby plugin;
    public static LuckPerms luckPerms;

    public static ArrayList<Player> pvp = new ArrayList<>();
    public static ArrayList<Player> insideArea = new ArrayList<>();

    public static XSLobby getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§e***************************");
        Bukkit.getConsoleSender().sendMessage("§r");
        Bukkit.getConsoleSender().sendMessage("§a    XSLobby - v2.0");
        Bukkit.getConsoleSender().sendMessage("§e   Status: §aEnabled");
        Bukkit.getConsoleSender().sendMessage("§c   By Panat Xsectorz");
        Bukkit.getConsoleSender().sendMessage("§r");
        Bukkit.getConsoleSender().sendMessage("§e***************************");

        plugin = this;

        Bukkit.getPluginManager().registerEvents(new onChat(),this);
        Bukkit.getPluginManager().registerEvents(new onJoin(),this);
        Bukkit.getPluginManager().registerEvents(new onQuit(),this);
        Bukkit.getPluginManager().registerEvents(new onModify(),this);
        Bukkit.getPluginManager().registerEvents(new onInteract(),this);
        Bukkit.getPluginManager().registerEvents(new onClickInventory(),this);
        Bukkit.getPluginManager().registerEvents(new onDeath(),this);
        Bukkit.getPluginManager().registerEvents(new onEntitySpawn(),this);
        Bukkit.getPluginManager().registerEvents(new onPlace(),this);
        Bukkit.getPluginManager().registerEvents(new onDeclineRs(),this);

        if(getConfig().getString("configuration_mode").equalsIgnoreCase("lobby")) {
            Bukkit.getPluginManager().registerEvents(new onFall(),this);
            Bukkit.getPluginManager().registerEvents(new onChangeSlot(),this);
            Bukkit.getPluginManager().registerEvents(new onDamage(),this);
            Bukkit.getPluginManager().registerEvents(new onPlayerMove(),this);
        }

        getCommand("facebook").setExecutor(new XSCommand());
        getCommand("discord").setExecutor(new XSCommand());
        getCommand("spawn").setExecutor(new XSCommand());

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
        Bukkit.getConsoleSender().sendMessage("§e***************************");
        Bukkit.getConsoleSender().sendMessage("§r");
        Bukkit.getConsoleSender().sendMessage("§a    XSLobby - v2.0");
        Bukkit.getConsoleSender().sendMessage("§e   Status: §cDisabled");
        Bukkit.getConsoleSender().sendMessage("§c   By Panat Xsectorz");
        Bukkit.getConsoleSender().sendMessage("§r");
        Bukkit.getConsoleSender().sendMessage("§e***************************");

    }
}
