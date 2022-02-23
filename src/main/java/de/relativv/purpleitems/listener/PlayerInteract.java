package de.relativv.purpleitems.listener;

import de.relativv.purpleitems.main.PurpleItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteract implements Listener {

    private PurpleItems purpleItems;
    public PlayerInteract(PurpleItems purpleItems) {
        this.purpleItems = purpleItems;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if(e.getItem() != null && e.getItem().getItemMeta() != null) {
                if(e.getItem().getType() == Material.TURTLE_HELMET) {
                        if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(this.purpleItems.fileManager.getTranslation("config.turtlehelmet.displayname"))) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                            p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, false, false));
                        }
                    }

            }
        }
    }
}
