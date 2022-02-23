package de.relativv.purpleitems.listener;

import de.relativv.purpleitems.main.PurpleItems;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerPickupItems implements Listener {

    private PurpleItems purpleItems;
    public PlayerPickupItems(PurpleItems purpleItems) {
        this.purpleItems = purpleItems;
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        for(ItemStack stacks : e.getPlayer().getInventory()) {
            if(stacks != null) {
                if (stacks.getType() == Material.GRAY_DYE) {
                    if(stacks.getItemMeta() != null) {
                    if (stacks.getItemMeta().getDisplayName().equalsIgnoreCase(this.purpleItems.fileManager.getTranslation("config.cobblevacc.displayname"))) {
                        if (e.getItem().getItemStack().getType() == Material.COBBLESTONE) {
                            e.setCancelled(true);
                        }
                    }
                    }
                } else if (stacks.getType() == Material.LIGHT_GRAY_DYE) {
                    if(stacks.getItemMeta() != null) {
                    if (stacks.getItemMeta().getDisplayName().equalsIgnoreCase(this.purpleItems.fileManager.getTranslation("config.stonevacc.displayname"))) {
                        if (e.getItem().getItemStack().getType() == Material.STONE) {
                            e.setCancelled(true);
                        }
                    }
                    }
                }
            }
        }
    }
}
