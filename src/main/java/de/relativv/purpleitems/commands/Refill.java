package de.relativv.purpleitems.commands;

import de.relativv.purpleitems.main.PurpleItems;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Refill implements CommandExecutor {

    private PurpleItems purpleItems;
    public Refill(PurpleItems purpleItems) {
        this.purpleItems = purpleItems;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(sender.hasPermission("purpleItems.refillItem")) {

                ItemStack stack = p.getInventory().getItemInMainHand();
                if(stack.getItemMeta().getDisplayName().contains(this.purpleItems.fileManager.getTranslation("config.lazyaxe.displayname"))
                    || stack.getItemMeta().getDisplayName().contains(this.purpleItems.fileManager.getTranslation("config.lazypickaxe.displayname"))) {

                    if(stack.getType() == Material.WOODEN_AXE) {
                        if(PurpleItems.econ.getBalance(p) >= this.purpleItems.fileManager.cfg.getInt("config.lazyaxe.refillCost")) {
                            ItemMeta meta = stack.getItemMeta();
                            meta.setDisplayName(this.purpleItems.fileManager.getTranslation("config.lazyaxe.displayname") + " §8(§a10§7/§a10§8)");
                            p.getInventory().getItemInMainHand().setItemMeta(meta);
                            p.getInventory().getItemInMainHand().setDurability((short) 0);
                            PurpleItems.econ.withdrawPlayer(p, this.purpleItems.fileManager.cfg.getInt("config.lazyaxe.refillCost"));
                            p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.itemRefilled").replaceAll("%money%", this.purpleItems.fileManager.cfg.getInt("config.lazyaxe.refillCost") + ""));
                        } else {
                            p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.notEnoughMoney"));
                            return true;
                        }
                    } else if(stack.getType() == Material.WOODEN_PICKAXE) {
                        if(PurpleItems.econ.getBalance(p) >= this.purpleItems.fileManager.cfg.getInt("config.lazyaxe.refillCost")) {
                            ItemMeta meta = stack.getItemMeta();
                            meta.setDisplayName(this.purpleItems.fileManager.getTranslation("config.lazypickaxe.displayname") + " §8(§a10§7/§a10§8)");
                            p.getInventory().getItemInMainHand().setItemMeta(meta);
                            p.getInventory().getItemInMainHand().setDurability((short) 0);
                            PurpleItems.econ.withdrawPlayer(p, this.purpleItems.fileManager.cfg.getInt("config.lazypickaxe.refillCost"));
                            p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.itemRefilled").replaceAll("%money%", this.purpleItems.fileManager.cfg.getInt("config.lazypickaxe.refillCost") + ""));
                        } else {
                            p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.notEnoughMoney"));
                            return true;
                        }
                    } else {
                        p.sendMessage(PurpleItems.pr + "§cAn error ocurred");
                        return true;
                    }


                } else {
                    sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.itemNotRefillable"));
                }

            } else {
                sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("noPermission"));
            }
        } else {
            sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.senderMustBePlayer"));
        }
        return true;
    }
}
