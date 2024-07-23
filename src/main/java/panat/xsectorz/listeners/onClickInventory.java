package panat.xsectorz.listeners;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import com.plotsquared.core.plot.flag.implementations.*;
import com.plotsquared.core.util.query.PlotQuery;
import com.sk89q.worldedit.world.biome.BiomeType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import panat.xsectorz.configuration.config;
import panat.xsectorz.configuration.messages;
import panat.xsectorz.menu.XSMenuHandler;
import panat.xsectorz.utils.XSUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static panat.xsectorz.menu.XSMenuHandler.getInventoryKey;
import static panat.xsectorz.menu.XSMenuHandler.getPage;

public class onClickInventory implements Listener {

    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        if(e.getView().getTitle().equalsIgnoreCase(XSUtils.replaceColor(config.customConfig.getString("creative_settings.title")))
    || e.getView().getTitle().equalsIgnoreCase(XSUtils.replaceColor(config.customConfig.getString("creative_mainMenu.title")))
                || e.getView().getTitle().equalsIgnoreCase(XSUtils.replaceColor(config.customConfig.getString("biome_settings.title")))) {
            e.setCancelled(true);

            if(!XSMenuHandler.getInventoryData().containsKey(p)) {
                return;
            }

            if(!XSMenuHandler.getInventoryData().get(p).containsKey(slot)) {
                return;
            }

            if(e.getCurrentItem() == null) {
                return;
            }

            String key = XSMenuHandler.getInventoryData().get(p).get(slot);

            if(key.equalsIgnoreCase("create_plot")) {
                p.performCommand("plot auto");
                p.closeInventory();
                return;
            } else if(key.equalsIgnoreCase("home_plot")) {
                p.performCommand("plot home");
                p.closeInventory();
                return;
            }

            Location location = BukkitUtil.adapt(p.getLocation());
            PlotArea area = location.getPlotArea();
            Plot plot = null;
            if(area != null) {
                plot =  area.getPlot(location);
            }

            if (plot == null) {
                return;
            }


            if(key.equalsIgnoreCase("weather_setting") && p.hasPermission("xsapi.creative.titan") && plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString())) {

                if(plot.getFlag(WeatherFlag.class).toString().equalsIgnoreCase("CLEAR")) {
                    plot.setFlag(WeatherFlag.class,"rain");
                } else {
                    plot.setFlag(WeatherFlag.class,"clear");
                }
            } else if(key.equalsIgnoreCase("time_setting") && p.hasPermission("xsapi.creative.hero") && plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString())) {

                String currentTime = plot.getFlag(TimeFlag.class).toString();
                if(XSMenuHandler.getTimeList().contains(currentTime)) {

                    int index = XSMenuHandler.getTimeList().indexOf(currentTime);
                    index = index+1;

                    if(index >= XSMenuHandler.getTimeList().size()) {
                        index = 0;
                    }

                    plot.setFlag(TimeFlag.class,XSMenuHandler.getTimeList().get(index));

                } else {
                    plot.setFlag(TimeFlag.class,XSMenuHandler.getTimeList().get(0));
                }

            } else if(key.equalsIgnoreCase("pvp_setting") && p.hasPermission("xsapi.creative.hero") && plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString())) {
                if(plot.getFlag(PvpFlag.class).toString().equalsIgnoreCase("true")) {
                    plot.setFlag(PvpFlag.class,"false");
                } else {
                    plot.setFlag(PvpFlag.class,"true");
                }
            } else if(key.equalsIgnoreCase("gamemode_setting") && p.hasPermission("xsapi.creative.hero") && plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString())) {
                Set<String> keys = messages.customConfig.getConfigurationSection("settings.gamemode").getKeys(false);
                List<String> gamemodeList = new ArrayList<>(keys);

                int index = gamemodeList.indexOf(plot.getFlagContainer().getFlagErased(GuestGamemodeFlag.class).toString());

                index = index + 1 >= gamemodeList.size() ? 0 : index + 1;

                plot.setFlag(GuestGamemodeFlag.class,gamemodeList.get(index));

            } else if(key.equalsIgnoreCase("invincible_setting") && p.hasPermission("xsapi.creative.hero") && plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString())) {
                if(plot.getFlag(InvincibleFlag.class).toString().equalsIgnoreCase("true")) {
                    plot.setFlag(InvincibleFlag.class,"false");
                } else {
                    plot.setFlag(InvincibleFlag.class,"true");
                }
            } else if(key.equalsIgnoreCase("setting_plot")) {
                XSMenuHandler.onOpenMenu(p,"creative_settings");
                return;
            } else if(key.equalsIgnoreCase("biome_plot")) {
                XSMenuHandler.onOpenMenu(p,"biome_settings");
                return;
            } else if(key.equalsIgnoreCase("close")) {
                p.closeInventory();
                return;
            } else if(key.equalsIgnoreCase("back_menu")) {
                XSMenuHandler.onOpenMenu(p,"creative_mainMenu");
                return;
            } else if(key.equalsIgnoreCase("next")) {
                getPage().put(p,getPage().get(p)+1);
            } else if(key.equalsIgnoreCase("back")) {
                getPage().put(p,getPage().get(p)-1);
            } else if(e.getView().getTitle().equalsIgnoreCase(XSUtils.replaceColor(config.customConfig.getString("biome_settings.title")))) {
                if(!key.isEmpty() && plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString())) {
                    plot.getPlotModificationManager().setBiome(BiomeType.REGISTRY.get(key), null);
                    p.closeInventory();
                }
            }


            int plotcount = PlotQuery.newQuery().ownedBy(p.getUniqueId()).asSet().size();
            XSMenuHandler.updateInventoryContent(XSMenuHandler.getInventoryHashMap().get(p),p,plot,getInventoryKey().get(p)+".contents",plotcount);
        }

    }
}
