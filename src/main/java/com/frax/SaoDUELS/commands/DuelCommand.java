package com.frax.SaoDUELS.commands;

import com.frax.SaoDUELS.SaoDUELS;
import com.frax.SaoDUELS.listener.BettingListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DuelCommand implements CommandExecutor {

    private Inventory inv;
    private SaoDUELS plugin;
    private BettingListener listener;

    private String noChallengeAccept;
    private String noChallengeReject;
    private String challengeRejectPlayer;
    private String challengeRejectTarget;
    private String notInRange;
    private String challengeMessage;
    private String challengeAcceptMessage;
    private String challengeRejectMessage;
    private String wrongArguments;
    private String alreadyInFight;
    private String fightNotAccept;

    public DuelCommand(SaoDUELS plugin, BettingListener listener) {
        this.plugin = plugin;
        this.listener = listener;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;

        Player p = (Player) commandSender;

        if (strings.length == 0) {

            if (command.getName().equalsIgnoreCase("duel")) {
                plugin.getCu().sendMessage(p, wrongArguments.replace("%player%", p.getName()));
            }

        } else if (strings.length == 1) {
            Player target = Bukkit.getPlayer(strings[0]);

            if (target == null) {
                return true;
            }

            if (p.getLocation().distance(target.getLocation()) <= 10) {
                if (!plugin.getInFight().containsKey(target)) {
                    plugin.getRequest().put(target, p);
                    plugin.getCu().sendMessage(target, challengeMessage.replace("%player%", p.getName()));
                    plugin.getCu().sendMessage(target, challengeAcceptMessage.replace("%player%", p.getName()));
                    plugin.getCu().sendMessage(target, challengeRejectMessage.replace("%player%", p.getName()));
                } else {
                    plugin.getCu().sendMessage(target, alreadyInFight.replace("%player%", p.getName()));
                }
            } else {
                plugin.getCu().sendMessage(p, notInRange);
            }

        } else if (strings.length == 2) {

            if (strings[0].equalsIgnoreCase("accept")) {
                Player target = Bukkit.getPlayer(strings[1]);

                if (plugin.getInFight().containsKey(p)) {
                    plugin.getCu().sendMessage(p, fightNotAccept);
                    return true;
                }

                if (plugin.getRequest().containsKey(p)) {
                    inv = Bukkit.createInventory(null, 54, "Betting");

                    inv.setItem(13, getItemStack(Material.PAPER, "§6100 §7cor"));
                    inv.setItem(22, getItemStack(Material.PAPER, "§6500 §7cor"));
                    inv.setItem(31, getItemStack(Material.PAPER, "§61000 §7cor"));
                    inv.setItem(40, getItemStack(Material.PAPER, "§610000 §7cor"));
                    inv.setItem(45, getItemStack(Material.POISONOUS_POTATO, "Accept"));
                    inv.setItem(46, getItemStack(Material.BARRIER, "Decline"));
                    inv.setItem(47, getItemStack(Material.PAPER, "§a" + p.getName()));
                    inv.setItem(51, getItemStack(Material.PAPER, "§a" + target.getName()));
                    inv.setItem(52, getItemStack(Material.POISONOUS_POTATO, "Accept"));
                    inv.setItem(53, getItemStack(Material.BARRIER, "Decline"));

                    p.openInventory(inv);
                    target.openInventory(inv);

                    listener.addPlayersToBetting(p, target);
                } else {
                    plugin.getCu().sendMessage(p, noChallengeAccept.replace("%player%", p.getName()));
                }

            } else if (strings[0].equalsIgnoreCase("reject")) {
                Player target = Bukkit.getPlayer(strings[1]);

                if (plugin.getRequest().containsKey(p)) {
                    plugin.getRequest().remove(p);

                    plugin.getCu().sendMessage(p, challengeRejectPlayer.replace("%player%", p.getName()));
                    plugin.getCu().sendMessage(target, challengeRejectTarget.replace("%player%", p.getName()));
                } else {
                    plugin.getCu().sendMessage(p, noChallengeReject.replace("%player%", p.getName()));
                }
            }
        }
        return true;
    }


    private ItemStack getItemStack(Material mat, String name) {
        ItemStack i = new ItemStack(mat);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);

        return i;
    }

    private ItemStack getDeath() {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
        ItemMeta im = skull.getItemMeta();
        im.setDisplayName("death");
        skull.setItemMeta(im);

        return skull;
    }

    public void setNoChallengeAccept(String noChallengeAccept) {
        this.noChallengeAccept = noChallengeAccept;
    }

    public void setNoChallengeReject(String noChallengeReject) {
        this.noChallengeReject = noChallengeReject;
    }

    public void setChallengeRejectPlayer(String challengeRejectPlayer) {
        this.challengeRejectPlayer = challengeRejectPlayer;
    }

    public void setChallengeRejectTarget(String challengeRejectTarget) {
        this.challengeRejectTarget = challengeRejectTarget;
    }

    public void setNotInRange(String notInRange) {
        this.notInRange = notInRange;
    }

    public void setChallengeMessage(String challengeMessage) {
        this.challengeMessage = challengeMessage;
    }

    public void setChallengeAcceptMessage(String challengeAcceptMessage) {
        this.challengeAcceptMessage = challengeAcceptMessage;
    }

    public void setChallengeRejectMessage(String challengeRejectMessage) {
        this.challengeRejectMessage = challengeRejectMessage;
    }

    public void setWrongArguments(String wrongArguments) {
        this.wrongArguments = wrongArguments;
    }

    public void setAlreadyInFight(String alreadyInFight) {
        this.alreadyInFight = alreadyInFight;
    }

    public void setFightNotAccept(String fightNotAccept) {
        this.fightNotAccept = fightNotAccept;
    }
}
