package de.relativv.purpleitems.main;

import de.relativv.purpleitems.commands.AutoRefill;
import de.relativv.purpleitems.commands.GiveItem;
import de.relativv.purpleitems.commands.Refill;
import de.relativv.purpleitems.listener.*;
import de.relativv.purpleitems.utils.FileManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class PurpleItems extends JavaPlugin {

    private PluginManager pm;
    private ConsoleCommandSender cs;

    public ArrayList<String> itemDisplayNames;

    public ArrayList<Player> cooldown;
    public ArrayList<Player> cooldown2;
    public FileManager fileManager;

    public static String pr;
    public static String noPerm;
    public static PurpleItems instance;

    public static Economy econ;

    @Override
    public void onEnable() {
        instance = this;

        this.fileManager = new FileManager(this);
        this.cooldown = new ArrayList<Player>();
        this.cooldown2 = new ArrayList<Player>();
        this.itemDisplayNames = new ArrayList<String>();
        this.register();

        sendConsoleMessage("§e============== §5PurpleItems §e=============");
        sendConsoleMessage(" ");
        sendConsoleMessage("§3Author§8: §a" + this.getDescription().getAuthors());
        sendConsoleMessage("§3Version§8: §a" + this.getDescription().getVersion());
        sendConsoleMessage(" ");
        sendConsoleMessage("§a§lLOADED");
        sendConsoleMessage(" ");
        sendConsoleMessage("§e============== §5PurpleItems §e=============");
    }

    @Override
    public void onDisable() {
        sendConsoleMessage("§e============== §5PurpleItems §e=============");
        sendConsoleMessage(" ");
        sendConsoleMessage("§3Author§8: §a" + this.getDescription().getAuthors());
        sendConsoleMessage("§3Version§8: §a" + this.getDescription().getVersion());
        sendConsoleMessage(" ");
        sendConsoleMessage("§4§lUNLOADED");
        sendConsoleMessage(" ");
        sendConsoleMessage("§e============== §5PurpleItems §e=============");
    }

    private void register() {
        this.pm = Bukkit.getPluginManager();
        this.cs = Bukkit.getConsoleSender();

        if (!setupEconomy() ) {
            sendConsoleMessage("§5Purpleitems §8> §bLoading vault dependency..");
        }

        this.getCommand("purpleitems").setExecutor(new GiveItem(this));
        this.getCommand("refill").setExecutor(new Refill(this));
        this.getCommand("autorefill").setExecutor(new AutoRefill(this));

        this.pm.registerEvents(new PlayerJoin(this), this);
        this.pm.registerEvents(new PlayerPickupItems(this), this);
        this.pm.registerEvents(new PlayerArmorChange(this), this);
        this.pm.registerEvents(new BlockBreak(this), this);
        this.pm.registerEvents(new AnvilPrepare(this), this);
        this.pm.registerEvents(new PlayerInteract(this), this);

        String turtleHelmet = this.fileManager.getTranslation("config.turtlehelmet.displayname");
        String lazyAxe = this.fileManager.getTranslation("config.lazyaxe.displayname");
        String lazypickaxe = this.fileManager.getTranslation("config.lazypickaxe.displayname");
        String cobblevacc = this.fileManager.getTranslation("config.cobblevacc.displayname");
        String stonevacc = this.fileManager.getTranslation("config.stonevacc.displayname");

        itemDisplayNames.add(turtleHelmet);
        itemDisplayNames.add(lazypickaxe);
        itemDisplayNames.add(cobblevacc);
        itemDisplayNames.add(stonevacc);
        itemDisplayNames.add(lazyAxe);

        pr = ChatColor.translateAlternateColorCodes('&', this.fileManager.cfg.getString("prefix") + " §r");
        noPerm = ChatColor.translateAlternateColorCodes('&', this.fileManager.cfg.getString("noPermission"));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void sendConsoleMessage(String msg) {
        this.cs.sendMessage(msg);
    }

    public static PurpleItems getInstance() {
        return instance;
    }
}
