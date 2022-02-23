package de.relativv.purpleitems.listener;

import de.relativv.purpleitems.main.PurpleItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoin implements Listener {

    private PurpleItems purpleItems;
    public PlayerJoin(PurpleItems purpleItems) {
        this.purpleItems = purpleItems;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        this.insertAutoRefill(p);

        if(p.getInventory().getHelmet() != null) {
            if(p.getInventory().getHelmet().getType() == Material.TURTLE_HELMET) {
                if(p.getInventory().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase(this.purpleItems.fileManager.getTranslation("config.turtlehelmet.displayname"))) {
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, false, false));
                } else {
                    e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
                    e.getPlayer().removePotionEffect(PotionEffectType.WATER_BREATHING);
                }
            } else {
                e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
                e.getPlayer().removePotionEffect(PotionEffectType.WATER_BREATHING);
            }
        }
    }

    private void insertAutoRefill(Player p) {
        if(this.purpleItems.fileManager.refillConfiguration.get(p.getUniqueId().toString() + ".autorefill") == null) {
            this.purpleItems.fileManager.refillConfiguration.set(p.getUniqueId().toString() + ".autorefill", false);
            this.purpleItems.fileManager.saveRefillFile();
        }
    }
}
