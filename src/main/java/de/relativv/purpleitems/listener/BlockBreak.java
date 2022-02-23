package de.relativv.purpleitems.listener;

import de.relativv.purpleitems.main.PurpleItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BlockBreak implements Listener {

    private PurpleItems purpleItems;
    public BlockBreak(PurpleItems purpleItems) {
        this.purpleItems = purpleItems;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(e.isCancelled()) {
            return;
        }

        List<String> allowedWorlds = this.purpleItems.fileManager.cfg.getStringList("config.toolsAllowedInWorlds");
        if(p.getInventory().getItemInMainHand().getType() == Material.WOODEN_PICKAXE) {
            if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(this.purpleItems.fileManager.getTranslation("config.lazypickaxe.displayname"))) {
                if (allowedWorlds.contains(p.getWorld().getName())) {
                    if (!this.purpleItems.cooldown2.contains(p)) {
                        if (this.getChargingsAmountPickaxe(p.getInventory().getItemInMainHand()) > 0 || this.purpleItems.fileManager.refillConfiguration.getBoolean(p.getUniqueId().toString() + ".autorefill")) {
                            if(!e.isCancelled()) {
                                e.setCancelled(true);
                                int newValue = this.getChargingsAmountPickaxe(p.getInventory().getItemInMainHand()) - 1;
                                short maxDurab = p.getInventory().getItemInMainHand().getType().getMaxDurability();
                                ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();

                                if (newValue <= 0) {
                                    if (this.purpleItems.fileManager.refillConfiguration.getBoolean(p.getUniqueId().toString() + ".autorefill")) {
                                        meta.setDisplayName(this.purpleItems.fileManager.getTranslation("config.lazypickaxe.displayname") + " §8(§a10§7/§a10§8)");
                                        p.getInventory().getItemInMainHand().setItemMeta(meta);
                                        p.getInventory().getItemInMainHand().setDurability((short) 0);
                                        p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.autoRefillRefilled"));
                                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
                                    } else {
                                        meta.setDisplayName(this.purpleItems.fileManager.getTranslation("config.lazypickaxe.displayname") + " §8(§a" + newValue + "§7/§a10§8)");
                                        p.getInventory().getItemInMainHand().setItemMeta(meta);
                                        p.getInventory().getItemInMainHand().setDurability((short) (maxDurab - 1));
                                        e.setCancelled(true);
                                    }
                                } else {
                                    meta.setDisplayName(this.purpleItems.fileManager.getTranslation("config.lazypickaxe.displayname") + " §8(§a" + newValue + "§7/§a10§8)");
                                    p.getInventory().getItemInMainHand().setItemMeta(meta);

                                    double percentage = newValue / 10.0;
                                    short newDurability = (short) Math.round(maxDurab - maxDurab * percentage);
                                    p.getInventory().getItemInMainHand().setDurability(newDurability);
                                }

                                List<Block> blocks = new ArrayList<>();
                                for (int i = e.getBlock().getLocation().getBlockY(); i > -64; i--) {
                                    Location l = new Location(e.getBlock().getWorld(), e.getBlock().getLocation().getBlockX(), i, e.getBlock().getLocation().getBlockZ());
                                    if (l.getBlock().getType() != Material.BEDROCK) {
                                        blocks.add(l.getBlock());
                                    }
                                }

                                for (Block locs : blocks) {
                                    if (locs.getType() != Material.AIR
                                            && locs.getType() != Material.BEDROCK
                                            && locs.getLocation() != e.getBlock().getLocation()) {

                                        if(locs.getType() != Material.LAVA && locs.getType() != Material.WATER) {
                                            e.getBlock().getWorld().dropItem(e.getBlock().getLocation().add(0, 1, 0), new ItemStack(locs.getType()));
                                        }
                                        locs.setType(Material.AIR);
                                    }
                                }

                                e.getBlock().getLocation().getBlock().setType(Material.GLASS);
                                this.purpleItems.cooldown2.add(p);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        PurpleItems.getInstance().cooldown2.remove(p);
                                    }
                                }.runTaskLater(PurpleItems.getInstance(), this.purpleItems.fileManager.cfg.getInt("config.lazypickaxe.cooldown") * 20L);

                            }
                        } else {
                            p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.noChargesLeft"));
                            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 2, 2);
                            e.setCancelled(true);
                            p.updateInventory();
                        }
                    } else {
                        p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.onCooldown").replaceAll("%cooldown%", this.purpleItems.fileManager.cfg.getInt("config.lazypickaxe.cooldown") + ""));
                        e.setCancelled(true);
                        p.updateInventory();
                   }
                } else {
                    p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.worldNotAllowed"));
                }
            }












            } else if(p.getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE) {
            if(e.getBlock().getType() == Material.ACACIA_LOG
                    || e.getBlock().getType() == Material.BIRCH_LOG
                    || e.getBlock().getType() == Material.DARK_OAK_LOG
                    || e.getBlock().getType() == Material.JUNGLE_LOG
                    || e.getBlock().getType() == Material.SPRUCE_LOG
                    || e.getBlock().getType() == Material.OAK_LOG) {
                        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(this.purpleItems.fileManager.getTranslation("config.lazyaxe.displayname"))) {
                            e.setCancelled(true);
                            if (allowedWorlds.contains(p.getWorld().getName())) {
                                    ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
                                    if (this.getChargingsAmountAxe(p.getInventory().getItemInMainHand()) > 0 || this.purpleItems.fileManager.refillConfiguration.getBoolean(p.getUniqueId().toString() + ".autorefill")) {
                                        if (!this.purpleItems.cooldown.contains(p)) {
                                            this.purpleItems.cooldown.add(p);

                                            int newValue = this.getChargingsAmountAxe(p.getInventory().getItemInMainHand()) - 1;
                                            short maxDurab = p.getInventory().getItemInMainHand().getType().getMaxDurability();

                                            if (newValue <= 0) {
                                                if (this.purpleItems.fileManager.refillConfiguration.getBoolean(p.getUniqueId().toString() + ".autorefill")) {
                                                    meta.setDisplayName(this.purpleItems.fileManager.getTranslation("config.lazyaxe.displayname") + " §8(§a10§7/§a10§8)");
                                                    p.getInventory().getItemInMainHand().setItemMeta(meta);
                                                    p.getInventory().getItemInMainHand().setDurability((short) 0);
                                                    p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.autoRefillRefilled"));
                                                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
                                                } else {
                                                    meta.setDisplayName(this.purpleItems.fileManager.getTranslation("config.lazyaxe.displayname") + " §8(§a" + newValue + "§7/§a10§8)");
                                                    p.getInventory().getItemInMainHand().setItemMeta(meta);
                                                    p.getInventory().getItemInMainHand().setDurability((short) (maxDurab - 1));
                                                    e.setCancelled(true);
                                                }
                                            } else {
                                                meta.setDisplayName(this.purpleItems.fileManager.getTranslation("config.lazyaxe.displayname") + " §8(§a" + newValue + "§7/§a10§8)");
                                                p.getInventory().getItemInMainHand().setItemMeta(meta);

                                                double percentage = newValue / 10.0;
                                                short newDurability = (short) Math.round(maxDurab - maxDurab * percentage);
                                                p.getInventory().getItemInMainHand().setDurability(newDurability);
                                            }

                                            List<Location> blocks = new ArrayList<>();
                                            for (int y = 0; y <= e.getBlock().getWorld().getHighestBlockYAt(e.getBlock().getLocation().getBlockX(), e.getBlock().getLocation().getBlockZ()); y++) {
                                                Location nLoc = new Location(e.getBlock().getWorld(), e.getBlock().getLocation().getBlockX(), y, e.getBlock().getLocation().getBlockZ());
                                                blocks.add(nLoc);
                                            }

                                            Material mat = e.getBlock().getType();
                                            for (Location block : blocks) {
                                                if (block.getBlock().getType() == mat) {
                                                    block.getBlock().breakNaturally();
                                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                                        all.playSound(block, Sound.BLOCK_WOOD_BREAK, 1, 1);
                                                    }
                                                }
                                            }

                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    PurpleItems.getInstance().cooldown.remove(p);
                                                }
                                            }.runTaskLater(PurpleItems.getInstance(), this.purpleItems.fileManager.cfg.getInt("config.lazyaxe.cooldown") * 20L);
                                        } else {
                                            p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.onCooldown").replaceAll("%cooldown%", this.purpleItems.fileManager.cfg.getInt("config.lazyaxe.cooldown") + ""));
                                            p.updateInventory();
                                            e.setCancelled(true);
                                        }
                                    } else {
                                        p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.noChargesLeft"));
                                        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 2, 2);
                                        e.setCancelled(true);
                                        p.updateInventory();
                                    }

                            } else {
                                p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.worldNotAllowed"));
                            }
                        }



            }
            }
        }


    private int getChargingsAmountPickaxe(ItemStack stack) {
        String toReturn = "";
        String chargings = stack.getItemMeta().getDisplayName();
        toReturn = chargings.replaceAll(this.purpleItems.fileManager.getTranslation("config.lazypickaxe.displayname"), "");
        toReturn = toReturn.replaceAll("§8§a", "");
        toReturn = toReturn.replaceAll("§7/§a10§8", "");
        toReturn = toReturn.replace("§8(§a", "").replace(")", " ").trim();
        return Integer.parseInt(toReturn);
    }

    private int getChargingsAmountAxe(ItemStack stack) {
        String toReturn = "";
        String chargings = stack.getItemMeta().getDisplayName();
        toReturn = chargings.replaceAll(this.purpleItems.fileManager.getTranslation("config.lazyaxe.displayname"), "");
        toReturn = toReturn.replaceAll("§8§a", "");
        toReturn = toReturn.replaceAll("§7/§a10§8", "");
        toReturn = toReturn.replace("§8(§a", "").replace(")", " ").trim();
        return Integer.parseInt(toReturn);
    }
}
