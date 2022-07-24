package panat.xsectorz.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
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

    public static String transColor(String string) {
        return string.replace('&','§');
    }

}
