package panat.xsectorz.utils;

import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.implementations.*;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import panat.xsectorz.configuration.config;
import panat.xsectorz.configuration.messages;
import panat.xsectorz.menu.XSMenuHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class XSUtils {

    public static String SELECTED_COLOR = messages.customConfig.getString("settings.selected");
    public static String NONE_SELECTED_COLOR = messages.customConfig.getString("settings.not_selected");

    public static void spawn(Player p) {

        String worldName = config.customConfig.getString("spawn.world");

        if(Bukkit.getServer().getWorld(worldName) == null) {
            p.sendMessage(transColor(messages.customConfig.getString("world_null")));
            return;
        }
        World world = Bukkit.getServer().getWorld(worldName);
        double x = config.customConfig.getDouble("spawn.x");
        double y = config.customConfig.getDouble("spawn.y");
        double z = config.customConfig.getDouble("spawn.z");
        double yaw = config.customConfig.getDouble("spawn.yaw");
        double pitch = config.customConfig.getDouble("spawn.pitch");

        Location loc = new Location(world,x,y,z);
        loc.setYaw((float) yaw);
        loc.setPitch((float) pitch);

        p.setHealth(p.getMaxHealth());
        p.getInventory().setHeldItemSlot(0);
        p.teleport(loc);

    }

    public static void clearContents(Player p) {
        p.getInventory().clear();
    }

    public static void loadItemFromConfig(String section,Player p) {
        for (String item : config.customConfig.getConfigurationSection(section).getKeys(false)) {
            Material mat = Material.getMaterial(Objects.requireNonNull(config.customConfig.getString(section + "." + item + ".material")));
            int slot = config.customConfig.getInt(section+"." + item + ".slot");
            int amount = config.customConfig.getInt(section+"." + item + ".amount");
            String displayName = config.customConfig.getString(section+"." + item + ".displayName");
            ArrayList<String> lore = (ArrayList<String>) config.customConfig.getStringList(section+"." + item + ".lore");

            p.getInventory().setItem(slot,createItemStack(mat,amount,displayName,lore));
        }
        p.updateInventory();
    }

    public static void loadCreativeItems(Player p) {
        loadItemFromConfig("creative_item",p);
    }

    public static void loadItemsJoin(Player p) {

        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getInventory().setBoots(new ItemStack(Material.AIR));
        clearContents(p);
        loadItemFromConfig("item_join",p);
    }

    public static ArrayList<String> decodePlaceholder(ArrayList<String> lores, Player p, Plot plot,int plotCount,boolean isSameBiome) {

        ArrayList<String> loreNew = new ArrayList<>();

        boolean isOwnerOrAdmin = plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString()) || p.hasPermission("xsapi.creative.admin");

        for(String lore : lores) {

            if(lore.equalsIgnoreCase("%weather_setting%")) {

                if(plot != null && isOwnerOrAdmin
                        && p.hasPermission("xsapi.creative.titan")) {
                    if(plot.getFlag(WeatherFlag.class).toString().equalsIgnoreCase("CLEAR")) {
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.weather.clear")));
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.weather.rainy")));
                        continue;
                    } else if(plot.getFlag(WeatherFlag.class).toString().equalsIgnoreCase("RAIN")) {
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.weather.clear")));
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.weather.rainy")));
                        continue;
                    }
                }

                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.weather.clear")));
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.weather.rainy")));

            } else if(lore.equalsIgnoreCase("%time_setting%")) {

                ArrayList<String> loreTime = new ArrayList<>();
                if(plot != null && plot.getOwner() != null && isOwnerOrAdmin
                        && p.hasPermission("xsapi.creative.hero")) {

                    int index = 0;
                    for(String section : messages.customConfig.getConfigurationSection("settings.time").getKeys(false)) {
                        String prefix = NONE_SELECTED_COLOR;
                        if(XSMenuHandler.getTimeList().get(index).equalsIgnoreCase(plot.getFlag(TimeFlag.class).toString())) {
                            prefix = SELECTED_COLOR;
                        }
                        loreTime.add(XSUtils.replaceColor(prefix + messages.customConfig.getString("settings.time."+section)));
                        index++;
                    }

                } else {
                    for(String section : messages.customConfig.getConfigurationSection("settings.time").getKeys(false)) {
                        String prefix = NONE_SELECTED_COLOR;
                        loreTime.add(XSUtils.replaceColor(prefix + messages.customConfig.getString("settings.time."+section)));
                    }
                }

                loreNew.addAll(loreTime);

            } else if(lore.equalsIgnoreCase("%pvp_setting%")) {

                if(plot != null && plot.getOwner() != null && isOwnerOrAdmin
                 && p.hasPermission("xsapi.creative.hero")) {
                    if(plot.getFlag(PvpFlag.class).toString().equalsIgnoreCase("true")) {
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.pvp.enable")));
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.pvp.disable")));
                        continue;
                    } else if(plot.getFlag(PvpFlag.class).toString().equalsIgnoreCase("false")) {
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.pvp.enable")));
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.pvp.disable")));
                        continue;
                    }
                }
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.pvp.enable")));
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.pvp.disable")));
            } else if(lore.equalsIgnoreCase("%invincible_setting%")) {

                if(plot != null && plot.getOwner() != null && isOwnerOrAdmin
                        && p.hasPermission("xsapi.creative.hero")) {
                    if(plot.getFlag(InvincibleFlag.class).toString().equalsIgnoreCase("true")) {
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.invincible.enable")));
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.invincible.disable")));
                        continue;
                    } else if(plot.getFlag(InvincibleFlag.class).toString().equalsIgnoreCase("false")) {
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.invincible.enable")));
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.invincible.disable")));
                        continue;
                    }

                }
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.invincible.enable")));
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.invincible.disable")));
            } else if(lore.equalsIgnoreCase("%waterflow_setting%")) {

                if(plot != null && plot.getOwner() != null && isOwnerOrAdmin
                        && p.hasPermission("xsapi.creative.titan")) {

                    if(plot.getFlag(LiquidFlowFlag.class).toString().equalsIgnoreCase("enabled") || plot.getFlag(LiquidFlowFlag.class).toString().equalsIgnoreCase("default")) {
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.liquid_flow.enable")));
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.liquid_flow.disable")));
                        continue;
                    } else if(plot.getFlag(LiquidFlowFlag.class).toString().equalsIgnoreCase("disabled")) {
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.liquid_flow.enable")));
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.liquid_flow.disable")));
                        continue;
                    }

                }
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.liquid_flow.enable")));
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.liquid_flow.disable")));
            } else if(lore.equalsIgnoreCase("%ice_melt_setting%")) {

                if(plot != null && plot.getOwner() != null && isOwnerOrAdmin
                        && p.hasPermission("xsapi.creative.titan")) {

                    if(plot.getFlag(IceMeltFlag.class).toString().equalsIgnoreCase("true") || plot.getFlag(IceMeltFlag.class).toString().equalsIgnoreCase("default")) {
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.ice_melt.enable")));
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.ice_melt.disable")));
                        continue;
                    } else if(plot.getFlag(IceMeltFlag.class).toString().equalsIgnoreCase("false")) {
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.ice_melt.enable")));
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.ice_melt.disable")));
                        continue;
                    }

                }
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.ice_melt.enable")));
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.ice_melt.disable")));
            } else if(lore.equalsIgnoreCase("%coral_dry_setting%")) {

                if(plot != null && plot.getOwner() != null && isOwnerOrAdmin
                        && p.hasPermission("xsapi.creative.titan")) {

                    if(plot.getFlag(CoralDryFlag.class).toString().equalsIgnoreCase("true") || plot.getFlag(CoralDryFlag.class).toString().equalsIgnoreCase("default")) {
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.coral_dry.enable")));
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.coral_dry.disable")));
                        continue;
                    } else if(plot.getFlag(CoralDryFlag.class).toString().equalsIgnoreCase("false")) {
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.coral_dry.enable")));
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.coral_dry.disable")));
                        continue;
                    }

                }
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.coral_dry.enable")));
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.coral_dry.disable")));
            } else if(lore.equalsIgnoreCase("%copper_oxide_setting%")) {

                if(plot != null && plot.getOwner() != null && isOwnerOrAdmin
                        && p.hasPermission("xsapi.creative.titan")) {

                    if(plot.getFlag(CopperOxideFlag.class).toString().equalsIgnoreCase("true") || plot.getFlag(CopperOxideFlag.class).toString().equalsIgnoreCase("default")) {
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.copper_oxide.enable")));
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.copper_oxide.disable")));
                        continue;
                    } else if(plot.getFlag(CopperOxideFlag.class).toString().equalsIgnoreCase("false")) {
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.copper_oxide.enable")));
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.copper_oxide.disable")));
                        continue;
                    }

                }
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.copper_oxide.enable")));
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.copper_oxide.disable")));
            } else if(lore.equalsIgnoreCase("%leaf_decay_setting%")) {

                if(plot != null && plot.getOwner() != null && isOwnerOrAdmin
                        && p.hasPermission("xsapi.creative.titan")) {

                    if(plot.getFlag(LeafDecayFlag.class).toString().equalsIgnoreCase("true") || plot.getFlag(LeafDecayFlag.class).toString().equalsIgnoreCase("default")) {
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.leaf_decay.enable")));
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.leaf_decay.disable")));
                        continue;
                    } else if(plot.getFlag(LeafDecayFlag.class).toString().equalsIgnoreCase("false")) {
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.leaf_decay.enable")));
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.leaf_decay.disable")));
                        continue;
                    }

                }
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.leaf_decay.enable")));
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.leaf_decay.disable")));
            } else if(lore.equalsIgnoreCase("%snow_melt_setting%")) {

                if(plot != null && plot.getOwner() != null && isOwnerOrAdmin
                        && p.hasPermission("xsapi.creative.titan")) {

                    if(plot.getFlag(SnowMeltFlag.class).toString().equalsIgnoreCase("true") || plot.getFlag(SnowMeltFlag.class).toString().equalsIgnoreCase("default")) {
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.snow_melt.enable")));
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.snow_melt.disable")));
                        continue;
                    } else if(plot.getFlag(SnowMeltFlag.class).toString().equalsIgnoreCase("false")) {
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.snow_melt.enable")));
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.snow_melt.disable")));
                        continue;
                    }

                }
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.snow_melt.enable")));
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.snow_melt.disable")));
            } else if(lore.equalsIgnoreCase("%snow_form_setting%")) {

                if(plot != null && plot.getOwner() != null && isOwnerOrAdmin
                        && p.hasPermission("xsapi.creative.titan")) {

                    if(plot.getFlag(SnowFormFlag.class).toString().equalsIgnoreCase("true") || plot.getFlag(SnowFormFlag.class).toString().equalsIgnoreCase("default")) {
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.snow_form.enable")));
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.snow_form.disable")));
                        continue;
                    } else if(plot.getFlag(SnowFormFlag.class).toString().equalsIgnoreCase("false")) {
                        loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.snow_form.enable")));
                        loreNew.add(XSUtils.replaceColor(SELECTED_COLOR+messages.customConfig.getString("settings.snow_form.disable")));
                        continue;
                    }

                }
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.snow_form.enable")));
                loreNew.add(XSUtils.replaceColor(NONE_SELECTED_COLOR+messages.customConfig.getString("settings.snow_form.disable")));
            } else if(lore.equalsIgnoreCase("%gamemode_setting%")) {
                if(plot != null && plot.getOwner() != null && isOwnerOrAdmin
                        && p.hasPermission("xsapi.creative.hero")) {
                    String gameMode = plot.getFlagContainer().getFlagErased(GuestGamemodeFlag.class).toString();
                    for(String section : messages.customConfig.getConfigurationSection("settings.gamemode").getKeys(false)) {
                        String prefix = NONE_SELECTED_COLOR;
                        if(section.equalsIgnoreCase(gameMode)) {
                            prefix = SELECTED_COLOR;
                        }
                        loreNew.add(XSUtils.replaceColor(prefix+messages.customConfig.getString("settings.gamemode." + section)));
                    }
                    continue;
                }
                for(String section : messages.customConfig.getConfigurationSection("settings.gamemode").getKeys(false)) {
                    String prefix = NONE_SELECTED_COLOR;
                    loreNew.add(XSUtils.replaceColor(prefix+messages.customConfig.getString("settings.gamemode." + section)));
                }
            } else if(lore.equalsIgnoreCase("%click_option%")) {
                if(plot == null || plot.getOwner() == null || (!plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString()) && !p.hasPermission("xsapi.creative.admin"))) {
                    loreNew.add(XSUtils.replaceColor(messages.customConfig.getString("settings.click_option.not_your_own")));
                } else {
                    if(lores.contains("%weather_setting%") && !p.hasPermission("xsapi.creative.titan")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"titan")));
                        continue;
                    } else if(lores.contains("%time_setting%") && !p.hasPermission("xsapi.creative.hero")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"hero")));
                        continue;
                    } else if(lores.contains("%gamemode_setting%") && !p.hasPermission("xsapi.creative.hero")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"hero")));
                        continue;
                    } else if(lores.contains("%pvp_setting%") && !p.hasPermission("xsapi.creative.hero")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"hero")));
                        continue;
                    } else if(lores.contains("%invincible_setting%") && !p.hasPermission("xsapi.creative.hero")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"hero")));
                        continue;
                    } else if(lores.contains("%waterflow_setting%") && !p.hasPermission("xsapi.creative.titan")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"titan")));
                        continue;
                    } else if(lores.contains("%coral_dry_setting%") && !p.hasPermission("xsapi.creative.titan")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"titan")));
                        continue;
                    } else if(lores.contains("%ice_melt_setting%") && !p.hasPermission("xsapi.creative.titan")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"titan")));
                        continue;
                    } else if(lores.contains("%copper_oxide_setting%") && !p.hasPermission("xsapi.creative.titan")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"titan")));
                        continue;
                    } else if(lores.contains("%leaf_decay_setting%") && !p.hasPermission("xsapi.creative.titan")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"titan")));
                        continue;
                    } else if(lores.contains("%snow_melt_setting%") && !p.hasPermission("xsapi.creative.titan")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"titan")));
                        continue;
                    } else if(lores.contains("%snow_form_setting%") && !p.hasPermission("xsapi.creative.titan")) {
                        loreNew.add(XSUtils.replaceColor(replaceWithRank(messages.customConfig.getString("settings.click_option.not_have_permission"),"titan")));
                        continue;
                    }

                    if(isSameBiome) {
                        loreNew.add(XSUtils.replaceColor(messages.customConfig.getString("settings.click_option.already_choosen")));
                        continue;
                    }

                    loreNew.add(XSUtils.replaceColor(messages.customConfig.getString("settings.click_option.click_to_toggle")));
                }
            } else {
                lore = lore.replace("%plot_have%",String.valueOf(plotCount));
                lore = lore.replace("%plot_available%",PlaceholderAPI.setPlaceholders(p,"%plotsquared_allowed_plot_count%").replace("&7",""));
                loreNew.add(XSUtils.replaceColor(lore));
            }

        }
        return loreNew;

    }

    public static String replaceWithRank(String str,String rank) {
        return XSUtils.replaceColor(str.replace("%rank%",messages.customConfig.getString("ranks."+rank)));
    }


    public static String replaceColor(String str) {
        return str.replace("&","§");
    }

    public static void loadItemsPvp(Player p) {
        clearContents(p);

        for (String item : config.customConfig.getConfigurationSection("item_pvp").getKeys(false)) {
            Material mat = Material.getMaterial(config.customConfig.getString("item_pvp." + item + ".material"));

            int slot = 0;
            String slot_str = "";
            slot = config.customConfig.getInt("item_pvp." + item + ".slot");
            slot_str = config.customConfig.getString("item_pvp." + item + ".slot");

            int amount = config.customConfig.getInt("item_pvp." + item + ".amount");
            String displayName = config.customConfig.getString("item_pvp." + item + ".displayName");
            ArrayList<String> lore = (ArrayList<String>) config.customConfig.getStringList("item_pvp." + item + ".lore");
            ArrayList<String> enchant = (ArrayList<String>) config.customConfig.getStringList("item_pvp." + item + ".enchantments");

            if(slot_str.equalsIgnoreCase("HELMET")) {
                p.getInventory().setHelmet(createItemStack(mat,amount,displayName,lore,enchant));
            } else if(slot_str.equalsIgnoreCase("CHESTPLATE")) {
                p.getInventory().setChestplate(createItemStack(mat,amount,displayName,lore,enchant));
            } else if(slot_str.equalsIgnoreCase("LEGGINGS")) {
                p.getInventory().setLeggings(createItemStack(mat,amount,displayName,lore,enchant));
            } else if(slot_str.equalsIgnoreCase("BOOTS")) {
                p.getInventory().setBoots(createItemStack(mat,amount,displayName,lore,enchant));
            } else {
                p.getInventory().setItem(slot,createItemStack(mat,amount,displayName,lore,enchant));
            }
        }
        p.updateInventory();
    }

    public static ItemStack createItemStack(Material mat, int amount, String name, ArrayList<String> lore) {

        ItemStack it = new ItemStack(mat,amount);

        if(!name.isEmpty()) {
            ItemMeta itm = it.getItemMeta();
            itm.setDisplayName(transColor(name));
            it.setItemMeta(itm);
        }

        if(!lore.isEmpty()) {
            ItemMeta itm = it.getItemMeta();
            Collections.replaceAll(lore,"&","§");
            itm.setLore(lore);
            it.setItemMeta(itm);
        }

        if(it.hasItemMeta()) {
            ItemMeta itemMeta = it.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            it.setItemMeta(itemMeta);
        }

        return it;
    }

    public static ItemStack createItemStack(Material mat, int amount, String name, ArrayList<String> lore,ArrayList<String> enchant) {

        ItemStack it = new ItemStack(mat,amount);

        if(!name.isEmpty()) {
            ItemMeta itm = it.getItemMeta();
            itm.setDisplayName(transColor(name));
            it.setItemMeta(itm);
        }

        if(!lore.isEmpty()) {
            ItemMeta itm = it.getItemMeta();
            Collections.replaceAll(lore,"&","§");
            itm.setLore(lore);
            it.setItemMeta(itm);
        }

        if(!enchant.isEmpty()) {
            ItemMeta itm = it.getItemMeta();

            for(String enchants : enchant) {
                String enchant_name = enchants.split(":")[0].toUpperCase();
                int enchant_lvl = Integer.parseInt(enchants.split(":")[1]);

                itm.addEnchant(Enchantment.getByName(enchant_name),enchant_lvl,true);
            }
            it.setItemMeta(itm);
        }

        ItemMeta itm = it.getItemMeta();
        itm.setUnbreakable(true);
        itm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itm.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        it.setItemMeta(itm);

        return it;
    }

    public static String transColor(String string) {
        return string.replace('&','§');
    }

}
