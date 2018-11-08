package com.frax.SaoDUELS.util;

import com.frax.SaoDUELS.SaoDUELS;
import com.frax.SaoDUELS.commands.DuelCommand;
import com.frax.SaoDUELS.listener.DuelListener;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileSystem {

    private File f;
    private FileConfiguration cfg;
    private String path;

    private DuelCommand command;
    private DuelListener listener;
    private SaoDUELS saoDUELS;

    public FileSystem(String path, DuelCommand command, DuelListener listener, SaoDUELS saoDUELS) {
        this.path = path;
        this.command = command;
        this.listener = listener;
        this.saoDUELS = saoDUELS;

        f = new File(path, "SaoDUELSconfig.yml");
        cfg = YamlConfiguration.loadConfiguration(f);
    }

    public void createDefaults() {
        cfg.options().copyDefaults(true);

        cfg.addDefault("prefix", "[SAO]");
        cfg.addDefault("noChallengeAccept", "You can't accept this challenge, when you havn't got challenged");
        cfg.addDefault("noChallengeReject", "You can't reject this challenge, when you havn't got challenged");

        cfg.addDefault("challengeRejectPlayer", "You have rejected his challenge!");
        cfg.addDefault("challengeRejectTarget", "Your challenge got rejected!");

        cfg.addDefault("notInRange", "You can't duel players, who are not in your distance!");

        cfg.addDefault("challengeMessage", "The user %player% just challenged you for a duel!");
        cfg.addDefault("challengeAcceptMessage", "For accepting this request type /duel accept %player%");
        cfg.addDefault("challengeRejectMessage", "For rejecting this request type /duel reject %player%");
        cfg.addDefault("wrongArguments", "You have to use /duel <targetName>");

        cfg.addDefault("alreadyInFight", "");
        cfg.addDefault("fightNotAccept", "");

        cfg.addDefault("hologramMatchStart", "Match starts in %count%s");
        cfg.addDefault("hologramWin", "You've won this match!");
        cfg.addDefault("hologramLose", "You've lost this match!");

        cfg.addDefault("usernameMySQL", "");
        cfg.addDefault("passwordMySQL", "");
        cfg.addDefault("databaseMySQL", "");
        cfg.addDefault("hostMySQL", "");

        try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fetchData() {
        command.setNoChallengeAccept(ChatColor.translateAlternateColorCodes('&', cfg.getString("noChallengeAccept")));
        command.setNoChallengeReject(ChatColor.translateAlternateColorCodes('&', cfg.getString("noChallengeReject")));
        command.setChallengeRejectPlayer(ChatColor.translateAlternateColorCodes('&', cfg.getString("challengeRejectPlayer")));
        command.setChallengeRejectTarget(ChatColor.translateAlternateColorCodes('&', cfg.getString("challengeRejectTarget")));
        command.setNotInRange(ChatColor.translateAlternateColorCodes('&', cfg.getString("notInRange")));
        command.setChallengeMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("challengeMessage")));
        command.setChallengeAcceptMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("challengeAcceptMessage")));
        command.setChallengeRejectMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("challengeRejectMessage")));
        command.setWrongArguments(ChatColor.translateAlternateColorCodes('&', cfg.getString("wrongArguments")));
        command.setAlreadyInFight(ChatColor.translateAlternateColorCodes('&',  cfg.getString("")));
        command.setFightNotAccept(ChatColor.translateAlternateColorCodes('&',  cfg.getString("")));

        listener.setHologramMatchStart(ChatColor.translateAlternateColorCodes('&', cfg.getString("hologramMatchStart")));
        listener.setHologramWin(ChatColor.translateAlternateColorCodes('&', cfg.getString("hologramWin")));
        listener.setHologramLose(ChatColor.translateAlternateColorCodes('&', cfg.getString("hologramLose")));

        saoDUELS.setPrefix(ChatColor.translateAlternateColorCodes('&', cfg.getString("prefix")));

        saoDUELS.getSql().setUsername(cfg.getString("usernameMySQL"));
        saoDUELS.getSql().setUsername(cfg.getString("passwordMySQL"));
        saoDUELS.getSql().setUsername(cfg.getString("databaseMySQL"));
        saoDUELS.getSql().setUsername(cfg.getString("hostMySQL"));
    }

    public File getF() {
        return f;
    }

    public void setF(File f) {
        this.f = f;
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public void setCfg(FileConfiguration cfg) {
        this.cfg = cfg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
