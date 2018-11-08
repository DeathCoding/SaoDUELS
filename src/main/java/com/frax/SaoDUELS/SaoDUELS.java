package com.frax.SaoDUELS;

import com.frax.SaoDUELS.commands.DuelCommand;
import com.frax.SaoDUELS.listener.BettingListener;
import com.frax.SaoDUELS.listener.DuelListener;
import com.frax.SaoDUELS.sql.MySQL;
import com.frax.SaoDUELS.util.ChatUtil;
import com.frax.SaoDUELS.util.FileSystem;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class SaoDUELS extends JavaPlugin {

    private SaoDUELS plugin;
    private FileSystem fs;
    private DuelCommand dc;
    private DuelListener dl;
    private BettingListener bl;
    private ChatUtil cu;
    private MySQL sql;
    private Economy economy;

    private HashMap<Player, Player> request;
    private HashMap<Player, Player> inFight;

    private String prefix;

    public void onEnable() {
        plugin = this;

        request = new HashMap<Player, Player>();
        inFight = new HashMap<Player, Player>();

        sql = new MySQL();
        sql.openConnection();

        bl = new BettingListener(this);
        dc = new DuelCommand(this, bl);
        dl = new DuelListener(this, sql, bl);
        cu = new ChatUtil(prefix);

        fs = new FileSystem("plugins/SaoDUELS", dc, dl, this);

        fs.createDefaults();
        fs.fetchData();


        getCommand("duel").setExecutor(dc);
        getServer().getPluginManager().registerEvents(dl, this);
        getServer().getPluginManager().registerEvents(bl, this);

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (!(rsp == null)) {
            economy = rsp.getProvider();
        }

    }

    public void onDisable() {
        sql.closeConnection();
    }

    public HashMap<Player, Player> getRequest() {
        return request;
    }

    public HashMap<Player, Player> getInFight() {
        return inFight;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public ChatUtil getCu() {
        return cu;
    }

    public MySQL getSql() {
        return sql;
    }

    public Economy getEconomy() {
        return economy;
    }
}
