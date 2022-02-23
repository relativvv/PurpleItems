package de.relativv.purpleitems.utils;

import de.relativv.purpleitems.main.PurpleItems;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    public File config;
    public FileConfiguration cfg;

    public File refillFile;
    public FileConfiguration refillConfiguration;

    //Create instance of the FileManager
    public FileManager(PurpleItems purpleItems) {
        this.config = new File("plugins/PurpleItems/config.yml");
        this.cfg = YamlConfiguration.loadConfiguration(this.config);

        this.refillFile = new File("plugins/PurpleItems/autorefill.yml");
        this.refillConfiguration = YamlConfiguration.loadConfiguration(this.refillFile);

        this.setConfigDefaults();
    }

    // Set defaults to config to customize message
    public void setConfigDefaults() {
        this.cfg.options().copyDefaults(true);
        this.cfg.addDefault("prefix", "&8▌ &5PurpleItems &7»");
        this.cfg.addDefault("noPermission", "&cYou don't have the correct permission to perform this action!");
        this.cfg.addDefault("messages.senderMustBePlayer", "&cYou must be a player to use this command");
        this.cfg.addDefault("messages.giveItemHelp", "&c/purpleitems giveitem <Player> <TurtleHelmet | LazyAxe | LazyPickAxe | CobbleVacc | StoneVacc>");
        this.cfg.addDefault("messages.targetNotOnline", "&cThe target Player '%player%' is not online.");
        this.cfg.addDefault("messages.invalidItem", "&cThat is not a valid Item.");
        this.cfg.addDefault("messages.notEnoughMoney", "&cYou have not enough money for that action.");
        this.cfg.addDefault("messages.onCooldown", "&cThis action is on cooldown, please wait %cooldown% seconds.");
        this.cfg.addDefault("messages.worldNotAllowed", "&cYou can't use the item in your current world.");
        this.cfg.addDefault("messages.noChargesLeft", "&cYou have no charges left on that tool.");
        this.cfg.addDefault("messages.configReloaded", "&aThe config has been reloaded");
        this.cfg.addDefault("messages.itemNotRefillable", "&cYou can't refill this item");
        this.cfg.addDefault("messages.notEnoughMoney", "&cYou don't have enough money.");
        this.cfg.addDefault("messages.itemRefilled", "&aItem successfully refilled, you paid %money% money.");
        this.cfg.addDefault("messages.autoRefillActivated", "&aItem autorefill has been activated, you can keep using your special items, even if they have 0 charges. They will refill automatically.");
        this.cfg.addDefault("messages.autoRefillDeactivated", "&4Item autorefill has been deactivated.");
        this.cfg.addDefault("messages.autoRefillRefilled", "&bDue to autorefill, your charges have been restored automatically.");

        ArrayList<String> allowedWorlds = new ArrayList<String>();
        allowedWorlds.add("world");
        this.cfg.addDefault("config.toolsAllowedInWorlds", allowedWorlds);

        this.cfg.addDefault("config.turtlehelmet.displayname", "&9Turtle Hermit Helmet");
        this.cfg.addDefault("config.turtlehelmet.loreLine1", "&7This magical helmet gives you infinite underwater");
        this.cfg.addDefault("config.turtlehelmet.loreLine2", "&7breathing if you wear it. Also you can see clearly");
        this.cfg.addDefault("config.turtlehelmet.loreLine3", "§7in the dark..");

        this.cfg.addDefault("config.cobblevacc.displayname", "&7Cobblestone Vaccine");
        this.cfg.addDefault("config.cobblevacc.loreLine1", "&7If you have this item in your inventory");
        this.cfg.addDefault("config.cobblevacc.loreLine2", "&7you can't pickup cobblestone anymore.");

        this.cfg.addDefault("config.stonevacc.displayname", "&8Stone Vaccine");
        this.cfg.addDefault("config.stonevacc.loreLine1", "&7If you have this item in your inventory");
        this.cfg.addDefault("config.stonevacc.loreLine2", "&7you can't pickup stone anymore.");

        this.cfg.addDefault("config.lazyaxe.displayname", "&bLazyaxe");
        this.cfg.addDefault("config.lazyaxe.loreLine1", "&7Every 2 seconds > by right-clicking a tree, the tree instantly chops down.");
        this.cfg.addDefault("config.lazyaxe.loreLine2", "&7This can be done 10 times, after that refill for 100 money with /refill");
        this.cfg.addDefault("config.lazyaxe.loreLine3", "&7while holding this item, or activate autorefill /autorefill while holding this item");
        this.cfg.addDefault("config.lazyaxe.cooldown", 2);
        this.cfg.addDefault("config.lazyaxe.refillCost", 100);

        this.cfg.addDefault("config.lazypickaxe.displayname", "&6Lazy Pickaxe");
        this.cfg.addDefault("config.lazypickaxe.loreLine1", "&7Every 2 seconds > it will mine instantly to bedrock and only a glass block will stay.");
        this.cfg.addDefault("config.lazypickaxe.loreLine2", "&7This can be done 10 times, after that refill for 100 money with /refill");
        this.cfg.addDefault("config.lazypickaxe.loreLine3", "&7while holding this item, or activate autorefill /autorefill while holding this item");
        this.cfg.addDefault("config.lazypickaxe.cooldown", 2);
        this.cfg.addDefault("config.lazypickaxe.refillCost", 100);
        this.saveConfig();
    }

    public String getTranslation(String translationKey) {
        if(this.cfg.getString(translationKey) != null) {
            return ChatColor.translateAlternateColorCodes('&', this.cfg.getString(translationKey));
        }
        return "Translation Error";
    }

    //Save config file
    public void saveConfig() {
        try {
            this.cfg.save(this.config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRefillFile() {
        try {
            this.refillConfiguration.save(this.refillFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
