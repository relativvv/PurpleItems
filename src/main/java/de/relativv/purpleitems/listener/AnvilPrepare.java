package de.relativv.purpleitems.listener;

import de.relativv.purpleitems.main.PurpleItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class AnvilPrepare implements Listener {

    private PurpleItems purpleItems;
    public AnvilPrepare(PurpleItems purpleItems) {
        this.purpleItems = purpleItems;
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent e) {

        if((e.getInventory().getItem(0) != null && this.purpleItems.itemDisplayNames.contains(e.getInventory().getItem(0).getItemMeta().getDisplayName())) || e.getInventory().getItem(1) != null && this.purpleItems.itemDisplayNames.contains(e.getInventory().getItem(1).getItemMeta().getDisplayName())) {
            e.setResult(null);
        }
    }

}
