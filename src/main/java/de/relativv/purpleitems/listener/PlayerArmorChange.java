package de.relativv.purpleitems.listener;

import de.relativv.purpleitems.main.PurpleItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerArmorChange implements Listener {

    private PurpleItems purpleItems;

    public PlayerArmorChange(PurpleItems purpleItems) {
        this.purpleItems = purpleItems;
    }

    @EventHandler
    public void onArmorChange(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() != null) {
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getItemMeta() != null) {
                    if (e.getClickedInventory() == p.getInventory()) {
                            if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getItemMeta() != null && p.getInventory().getHelmet().getType() == Material.TURTLE_HELMET) {
                                if (p.getInventory().getHelmet().getItemMeta().getDisplayName().equalsIgnoreCase(this.purpleItems.fileManager.getTranslation("config.turtlehelmet.displayname"))) {
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, false, false));
                                }
                            } else {
                                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                                p.removePotionEffect(PotionEffectType.WATER_BREATHING);
                            }
                        }
                    }
            }
        }
    }
}
