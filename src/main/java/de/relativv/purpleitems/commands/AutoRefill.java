package de.relativv.purpleitems.commands;

import de.relativv.purpleitems.main.PurpleItems;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoRefill implements CommandExecutor {

    private PurpleItems purpleItems;
    public AutoRefill(PurpleItems purpleItems) {
        this.purpleItems = purpleItems;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(sender.hasPermission("purpleItems.autorefill")) {

                boolean currentValue = this.purpleItems.fileManager.refillConfiguration.getBoolean(p.getUniqueId().toString() + ".autorefill");
                if(!currentValue) {
                    this.purpleItems.fileManager.refillConfiguration.set(p.getUniqueId().toString() + ".autorefill", true);
                    p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.autoRefillActivated"));
                } else {
                    this.purpleItems.fileManager.refillConfiguration.set(p.getUniqueId().toString() + ".autorefill", false);
                    p.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.autoRefillDeactivated"));
                }

                this.purpleItems.fileManager.saveRefillFile();

            } else {
                sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("noPermission"));
            }
        } else {
            sender.sendMessage(PurpleItems.pr + this.purpleItems.fileManager.getTranslation("messages.senderMustBePlayer"));
        }
        return true;
    }
}
