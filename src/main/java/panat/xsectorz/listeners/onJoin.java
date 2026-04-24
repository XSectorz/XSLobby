package panat.xsectorz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import panat.xsectorz.configuration.config;
import panat.xsectorz.core.XSLobby;
import panat.xsectorz.utils.XSUtils;

import java.util.UUID;

public class onJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        // 1. ตรวจสอบโหมดการทำงานจาก Config
        if (config.customConfig.getString("configuration_mode").equalsIgnoreCase("lobby")) {
            XSUtils.loadItemsJoin(p);

            // 2. ระบบ Force Resource Pack (ส่งแบบ SHA-1 Hash เพื่อใช้ Cache)
            if (config.customConfig.getBoolean("force_resource.enable")) {

                // หน่วงเวลา 2 วินาที (40 ticks) เพื่อให้ผู้เล่นโหลดเข้าโลกเสร็จก่อนส่งคำสั่ง
                Bukkit.getScheduler().runTaskLater(XSLobby.getPlugin(), () -> {
                    String url = config.customConfig.getString("force_resource.resource");
                    String hashFromConfig = config.customConfig.getString("force_resource.hash");

                    if (url != null && hashFromConfig != null) {
                        try {
                            // ตัดช่องว่างที่อาจติดมา และแปลงเป็น byte array (20 bytes)
                            byte[] hash = hexStringToByteArray(hashFromConfig.trim().toLowerCase());

                            if (hash.length == 20) {
                                // ส่งคำสั่ง setResourcePack พร้อม Hash เพื่อให้ Client เช็คไฟล์ในเครื่อง
                                p.setResourcePack(url, hash);
                                // p.sendMessage("§a[Siamcraft] กำลังตรวจสอบ Resource Pack..."); // ปิดไว้ถ้าไม่ต้องการ debug
                            } else {
                                Bukkit.getLogger().info("[Siamcraft] Hash ใน Config ไม่ใช่ 20 bytes (ต้องมี 40 ตัวอักษร)");
                            }
                        } catch (Exception ex) {
                            Bukkit.getLogger().info("[Siamcraft] เกิดข้อผิดพลาดในการแปลง Hash: " + ex.getMessage());
                        }
                    }
                }, 40L);
            }

        } else if (config.customConfig.getString("configuration_mode").equalsIgnoreCase("creative")) {
            XSUtils.loadCreativeItems(p);
        }

        // 3. ระบบ Spawn ผู้เล่น (หน่วงเวลา 10 ticks ตามเดิม)
        Bukkit.getScheduler().scheduleSyncDelayedTask(XSLobby.getPlugin(), new Runnable() {
            @Override
            public void run() {
                XSUtils.spawn(p);
            }
        }, 10L);

        e.setJoinMessage(null);
    }

    /**
     * แปลง String Hex (SHA-1) 40 ตัวอักษร ให้เป็น byte array ขนาด 20 bytes
     */
    public byte[] hexStringToByteArray(String s) {
        int len = s.length();
        // ตรวจสอบว่าความยาวต้องเป็นเลขคู่
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Hex string must have an even length");
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}