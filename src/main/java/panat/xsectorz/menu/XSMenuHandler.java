package panat.xsectorz.menu;


import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import com.plotsquared.core.plot.flag.implementations.IceMeltFlag;
import com.plotsquared.core.util.query.PlotQuery;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import panat.xsectorz.configuration.config;
import panat.xsectorz.configuration.messages;
import panat.xsectorz.utils.XSUtils;

import java.util.*;

public class XSMenuHandler {

    private static HashMap<Player,Inventory> inventoryHashMap = new HashMap<>();
    private static HashMap<Player,String> inventoryKey = new HashMap<>();
    private static HashMap<Player,String> tempBiome = new HashMap<>();
    private static HashMap<Player,HashMap<Integer,String>> inventoryData = new HashMap<>();
    private static HashMap<Player,Integer> page = new HashMap<>();

    private static  ArrayList<String> timeList = new ArrayList<>(Arrays.asList("22800","1000","6000","13000","15000","18000"));
    private static ArrayList<Integer> slotIndex = new ArrayList<>(Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43));
    private static ArrayList<Integer> slotIndexMainMenuNotOwn = new ArrayList<>(Arrays.asList(13));
    private static ArrayList<Integer> slotIndexMainMenuOwn = new ArrayList<>(Arrays.asList(10,13,14,15,22,23,24));

    public static HashMap<Player,String> getTempBiomePlot() {
        return tempBiome;
    }

    public static HashMap<Player, String> getInventoryKey() {
        return inventoryKey;
    }

    public static ArrayList<Integer> getSlotIndex() {
        return slotIndex;
    }
    public static HashMap<Player,Inventory> getInventoryHashMap() {
        return inventoryHashMap;
    }
    public static HashMap<Player,HashMap<Integer,String>> getInventoryData() {
        return inventoryData;
    }

    public static ArrayList<Integer> getSlotIndexMainMenuNotOwn() {
        return slotIndexMainMenuNotOwn;
    }

    public static ArrayList<Integer> getSlotIndexMainMenuOwn() {
        return slotIndexMainMenuOwn;
    }

    public static HashMap<Player, Integer> getPage() {
        return page;
    }

    public static ArrayList<String> getTimeList() {
        return timeList;
    }

    public static void onOpenMenu(Player p,String titleSection) {

        int invSize;

        if(titleSection.equalsIgnoreCase("creative_mainMenu")) {
            invSize = 36;
        } else {
            invSize = 54;
        }

        Inventory inv = Bukkit.createInventory(null,invSize, XSUtils.replaceColor(Objects.requireNonNull(config.customConfig.getString(titleSection+".title"))));

        Location location = BukkitUtil.adapt(p.getLocation());
        PlotArea area = location.getPlotArea();
        Plot plot = null;
        if(area != null) {
            plot =  area.getPlot(location);
        }

        int plotcount = PlotQuery.newQuery().ownedBy(p.getUniqueId()).asSet().size();

        String settingSection;
        if(plotcount == 0) {
            settingSection = "creative_mainMenu.not_own_plot";
        } else {
            settingSection = "creative_mainMenu.own_plot";
            if(plot == null) {
                p.sendMessage(XSUtils.replaceColor(messages.customConfig.getString("only_on_plot")));
                return;
            }
        }
        getTempBiomePlot().put(p, "none");

        if(titleSection.equalsIgnoreCase("creative_settings")) {
            settingSection = "creative_settings";
        } else if(titleSection.equalsIgnoreCase("biome_settings")) {
            settingSection = "biome_settings";
            plot.getBiome(biomeType -> {
                getTempBiomePlot().put(p, String.valueOf(biomeType).replace("minecraft:",""));
            });
        }
        //Bukkit.broadcastMessage(""+plot.getFlag(IceMeltFlag.class));

        /*plot.getBiome(biomeType -> {
            // Handle the biomeType result here
            Bukkit.broadcastMessage(biomeType+"");
        });

        plot.getPlotModificationManager().setBiome(BiomeType.REGISTRY.get("plains"), null);*/
        //p.sendMessage(plot.getFlagContainer().getFlagErased(GamemodeFlag.class).toString());
        page.put(p,1);
        getInventoryKey().put(p,settingSection);
        updateInventoryContent(inv,p,plot, settingSection +".contents",plotcount);
        inventoryHashMap.put(p,inv);
        p.openInventory(inv);
    }

    public static ItemStack getItemStackFromConfig(String key,Player p,Plot plot,int plotCount) {
        String displayName = config.customConfig.getString(key + ".displayName");
        Material mat = Material.valueOf(config.customConfig.getString(key + ".material"));
        int amount = config.customConfig.getInt(key + ".amount");

        String section = key.split("\\.")[key.split("\\.").length-1];
        boolean isSameBiome = false;
        if(section.equalsIgnoreCase(XSMenuHandler.getTempBiomePlot().get(p))) {
            isSameBiome = true;
        }

        ArrayList<String> lores = XSUtils.decodePlaceholder(new ArrayList<>(config.customConfig.getStringList(key + ".lore")),p,plot,plotCount,isSameBiome);

        return isSameBiome ? XSUtils.createItemStack(mat,amount,displayName,lores,new ArrayList<String>(Arrays.asList("PROTECTION_ENVIRONMENTAL:1"))) : XSUtils.createItemStack(mat,amount,displayName,lores);
    }

    public static void updateInventoryContent(Inventory inv,Player p,Plot plot,String info,int amountPlot) {

        for(int i = 0 ; i < inv.getSize() ; i++) {
            inv.setItem(i,new ItemStack(Material.AIR));
        }

        HashMap<Integer,String> slotInfo = new HashMap<>();

        Set<String> keys = config.customConfig.getConfigurationSection(info).getKeys(false);
        List<String> keysList = new ArrayList<>(keys);

        int startIndex = 0;
        int endIndex = 0;
        List<String> subKeysList = keysList;

        if(info.equalsIgnoreCase("creative_settings.contents") || info.equalsIgnoreCase("biome_settings.contents")) {
            startIndex = (getPage().get(p)-1)*28;
            endIndex = Math.min(startIndex + 27, keysList.size());

            if(endIndex+1 < keys.size()) {
                slotInfo.put(50,"next");
                inv.setItem(50,getItemStackFromConfig("global_config.next",p,plot,amountPlot));
            }
            if(getPage().get(p) > 1) {
                slotInfo.put(48,"back");
                inv.setItem(48,getItemStackFromConfig("global_config.back",p,plot,amountPlot));
            }

            slotInfo.put(49,"back_menu");
            inv.setItem(49,getItemStackFromConfig("global_config.back_menu",p,plot,amountPlot));

            if(endIndex+1 < keysList.size()) {
                subKeysList = keysList.subList(startIndex, endIndex+1);
            } else {
                subKeysList = keysList.subList(startIndex, endIndex);
            }
        }

        int tempIndex = 0;

        ArrayList<Integer> slotList = new ArrayList<>();

        if(info.equalsIgnoreCase("creative_mainMenu.not_own_plot.contents")) {
            slotList = getSlotIndexMainMenuNotOwn();
        } else if(info.equalsIgnoreCase("creative_mainMenu.own_plot.contents")) {
            slotList = getSlotIndexMainMenuOwn();
        } else if(info.equalsIgnoreCase("creative_settings.contents") || info.equalsIgnoreCase("biome_settings.contents")) {
            slotList = getSlotIndex();
        }

        for(String section : subKeysList) {
            int slot = slotList.get(tempIndex);
            slotInfo.put(slot,section);
            inv.setItem(slot,getItemStackFromConfig(info + "." + section,p,plot,amountPlot));
            tempIndex++;
        }

        inventoryData.put(p,slotInfo);
        p.updateInventory();
    }


}
