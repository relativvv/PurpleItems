package de.relativv.purpleitems.commands;

import de.relativv.purpleitems.main.PurpleItems;
import de.relativv.purpleitems.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItem implements CommandExecutor {

    private PurpleItems purpleItems;
    public GiveItem(PurpleItems purpleItems) {
        this.purpleItems = purpleItems;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("purpleItems.giveitem")) {
            if(args.length != 3 && args.length != 1) {
                sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.giveItemHelp"));
                sender.sendMessage(PurpleItems.pr + "§cReload the config with /purpleitems reloadconfig");
                return true;
            }

            if(args.length == 3) {
                if(args[0].equalsIgnoreCase("giveitem")) {

                    String name = args[1];
                    Player target = Bukkit.getPlayer(name);
                    if(target != null && target.isOnline()) {
                        if(args[2].equalsIgnoreCase("turtlehelmet")
                                || args[2].equalsIgnoreCase("LazyAxe")
                                || args[2].equalsIgnoreCase("LazyPickAxe")
                                || args[2].equalsIgnoreCase("CobbleVacc")
                                || args[2].equalsIgnoreCase("StoneVacc")
                        ) {
                            String itemDisplayname = this.purpleItems.fileManager.getTranslation("config." + args[2].toLowerCase() + ".displayname");
                            String itemLore1 = this.purpleItems.fileManager.getTranslation("config." + args[2].toLowerCase() + ".loreLine1");
                            String itemLore2 = this.purpleItems.fileManager.getTranslation("config." + args[2].toLowerCase() + ".loreLine2");
                            String itemLore3 = this.purpleItems.fileManager.getTranslation("config." + args[2].toLowerCase() + ".loreLine3") != null ? this.purpleItems.fileManager.getTranslation("config." + args[2].toLowerCase() + ".loreLine3") : "";

                            switch (args[2].toLowerCase()) {
                                case "turtlehelmet":
                                    ItemStack helmet = new ItemBuilder(Material.TURTLE_HELMET, 1)
                                            .setDisPlayname(itemDisplayname)
                                            .setLore(itemLore1, itemLore2, itemLore3)
                                            .build();

                                    target.getInventory().addItem(helmet);
                                    break;

                                case "lazyaxe":
                                    ItemStack lazyAxe = new ItemBuilder(Material.WOODEN_AXE, 1)
                                            .setDisPlayname(itemDisplayname + " &8(§a10§7/§a10&8)")
                                            .setLore(itemLore1, itemLore2, itemLore3)
                                            .build();
                                    target.getInventory().addItem(lazyAxe);
                                    break;

                                case "lazypickaxe":
                                    ItemStack lazyPickaxe = new ItemBuilder(Material.WOODEN_PICKAXE, 1)
                                            .setDisPlayname(itemDisplayname + " &8(§a10§7/§a10&8)")
                                            .setLore(itemLore1, itemLore2, itemLore3)
                                            .build();
                                    target.getInventory().addItem(lazyPickaxe);
                                    break;

                                case "cobblevacc":
                                    ItemStack cobbleVacc = new ItemBuilder(Material.GRAY_DYE, 1)
                                            .setDisPlayname(itemDisplayname)
                                            .setLore(itemLore1, itemLore2)
                                            .build();
                                    target.getInventory().addItem(cobbleVacc);
                                    break;

                                case "stonevacc":
                                    ItemStack stoneVacc = new ItemBuilder(Material.LIGHT_GRAY_DYE, 1)
                                            .setDisPlayname(itemDisplayname)
                                            .setLore(itemLore1, itemLore2)
                                            .build();
                                    target.getInventory().addItem(stoneVacc);
                                    break;
                                default:
                                    sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.invalidItem"));
                                    break;
                            }
                        } else {
                            sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.giveItemHelp"));
                        }

                    } else {
                        sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.targetNotOnline").replaceAll("%player%", args[1]));
                    }

                } else {
                    sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.giveItemHelp"));
                }

            } else {
                if(args[0].equalsIgnoreCase("reloadconfig")) {
                    this.purpleItems.fileManager.cfg = YamlConfiguration.loadConfiguration(this.purpleItems.fileManager.config);
                    this.purpleItems.fileManager.refillConfiguration = YamlConfiguration.loadConfiguration(this.purpleItems.fileManager.refillFile);

                    PurpleItems.pr = ChatColor.translateAlternateColorCodes('&', this.purpleItems.fileManager.cfg.getString("prefix") + " §r");
                    PurpleItems.noPerm = ChatColor.translateAlternateColorCodes('&', this.purpleItems.fileManager.cfg.getString("noPermission"));

                    this.purpleItems.itemDisplayNames.clear();

                    String turtleHelmet = this.purpleItems.fileManager.getTranslation("config.turtlehelmet.displayname");
                    String lazyAxe = this.purpleItems.fileManager.getTranslation("config.lazyaxe.displayname");
                    String lazypickaxe = this.purpleItems.fileManager.getTranslation("config.lazypickaxe.displayname");
                    String cobblevacc = this.purpleItems.fileManager.getTranslation("config.cobblevacc.displayname");
                    String stonevacc = this.purpleItems.fileManager.getTranslation("config.stonevacc.displayname");

                    this.purpleItems.itemDisplayNames.add(turtleHelmet);
                    this.purpleItems.itemDisplayNames.add(lazypickaxe);
                    this.purpleItems.itemDisplayNames.add(cobblevacc);
                    this.purpleItems.itemDisplayNames.add(stonevacc);
                    this.purpleItems.itemDisplayNames.add(lazyAxe);

                    sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.configReloaded"));
                } else {
                    sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.giveItemHelp"));
                }
            }
        } else {
            sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("noPermission"));
        }
        return true;
    }
}
