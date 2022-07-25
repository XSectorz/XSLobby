package panat.xsectorz.utils;

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

import java.util.ArrayList;
import java.util.Collections;

public class XSUtils {

    public static void spawn(Player p) {

        String worldName = config.customConfig.getString("spawn.world");

        if(Bukkit.getServer().getWorld(worldName) == null) {
            p.sendMessage(transColor(messages.customConfig.getString("world__null")));
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

    public static void loadItemsJoin(Player p) {

        p.getInventory().clear();

        for (String item : config.customConfig.getConfigurationSection("item_join").getKeys(false)) {
            Material mat = Material.getMaterial(config.customConfig.getString("item_join." + item + ".material"));
            int slot = config.customConfig.getInt("item_join." + item + ".slot");
            int amount = config.customConfig.getInt("item_join." + item + ".amount");
            String displayName = config.customConfig.getString("item_join." + item + ".displayName");
            ArrayList<String> lore = (ArrayList<String>) config.customConfig.getStringList("item_join." + item + ".lore");

            p.getInventory().setItem(slot,createItemStack(mat,amount,displayName,lore));
        }
        p.updateInventory();
    }

    public static void loadItemsPvp(Player p) {

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
        itm.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        return it;
    }

    public static String transColor(String string) {
        return string.replace('&','§');
    }

}
