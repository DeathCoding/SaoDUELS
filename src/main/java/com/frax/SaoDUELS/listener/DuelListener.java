package com.frax.SaoDUELS.listener;

import com.frax.SaoDUELS.SaoDUELS;
import com.frax.SaoDUELS.sql.MySQL;
import com.frax.SaoDUELS.sql.StatsManager;
import com.frax.SaoDUELS.util.HologramUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class DuelListener implements Listener {

    private SaoDUELS plugin;
    private BettingListener bl;

    private HashMap<Player, String> option;
    private int countdown;
    private Thread t;

    private String hologramMatchStart;
    private String hologramWin;
    private String hologramLose;

    private HologramUtil hu;

    private MySQL sql;
    private StatsManager sm;

    public DuelListener(SaoDUELS plugin, MySQL sql, BettingListener bl) {
        this.plugin = plugin;
        this.bl = bl;
        option = new HashMap<Player, String>();

        hu = new HologramUtil();
        this.sql = sql;
    }

    @EventHandler
    public void onInteractInventory(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Inventory time;
        final Player p = (Player) e.getWhoClicked();

        if (inv.getTitle().equals("options")) {
            e.setCancelled(true);

            ItemStack plus = getItemStack(Material.WOOD_BUTTON, "+1");
            ItemStack minus = getItemStack(Material.WOOD_BUTTON, "-1");
            ItemStack clock = getItemStack(Material.WATCH, "10s", 10);
            ItemStack accept = getItemStack(Material.WOOL, "Accept");

            if (e.getCurrentItem().getType() == Material.FISHING_ROD) {
                option.put(p, "first");
                p.closeInventory();

                time = Bukkit.createInventory(null, 27,"time");

                time.setItem(4, plus);
                time.setItem(13, clock);
                time.setItem(22, minus);
                time.setItem(26, accept);
                p.openInventory(time);
            } else if (e.getCurrentItem().getType() == Material.ITEM_FRAME) {
                option.put(p, "half");
                p.closeInventory();

                time = Bukkit.createInventory(null, 27,"time");

                time.setItem(4, plus);
                time.setItem(13, clock);
                time.setItem(22, minus);
                time.setItem(26, accept);
                p.openInventory(time);
            } else if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
                option.put(p, "death");
                p.closeInventory();

                time = Bukkit.createInventory(null, 27,"time");

                time.setItem(4, plus);
                time.setItem(13, clock);
                time.setItem(22, minus);
                time.setItem(26, accept);
                p.openInventory(time);
            }
        }

        if (inv.getTitle().equals("time")) {
            e.setCancelled(true);

            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("+1")) {
                int i = inv.getItem(13).getAmount();

                if (i == 60) return;

                i++;
                inv.remove(Material.WATCH);

                inv.setItem(13, getItemStack(Material.WATCH, i + "s", i));
                p.updateInventory();
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("-1")) {
                int i = inv.getItem(13).getAmount();

                if (i == 10) return;

                i--;
                inv.remove(Material.WATCH);

                inv.setItem(13, getItemStack(Material.WATCH, i + "s", i));
                p.updateInventory();
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("Accept")) {
                countdown = inv.getItem(13).getAmount();
                p.closeInventory();

                Location hologram = new Location(p.getWorld(), (p.getLocation().getX() + plugin.getRequest().get(p).getLocation().getX()) / 2, p.getLocation().getY() + 2.5, (p.getLocation().getZ() + plugin.getRequest().get(p).getLocation().getZ()) / 2);
                hu.setLoc(hologram);
                hu.spawn();

                final Player target = plugin.getRequest().get(p);
                System.out.println(target.getName());
                plugin.getRequest().remove(p);

                t = new Thread(new Runnable() {
                    boolean running = true;

                    public void run() {
                        while (running) {
                            for (int i = countdown; i >= 0; i--) {

                                hu.changeName(hologramMatchStart.replace("%count%", "" + i));

                                if (i == 0) {
                                    hu.remove();

                                    plugin.getInFight().put(p, target);
                                    running = false;
                                    try {
                                        t.join();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                });

                t.start();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) e.setCancelled(false);

        Player damager = null;
        Player target = null;

        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            damager = (Player) e.getDamager();
            target = (Player) e.getEntity();

            sm = new StatsManager(damager, sql);
        }

        String options = null;

        if (option.containsKey(damager)) {
            options = option.get(damager);
        } else {
            options = option.get(target);
        }

        if (plugin.getInFight().containsKey(damager) || plugin.getInFight().containsValue(damager)) {
            if (plugin.getInFight().containsKey(target) || plugin.getInFight().containsValue(target)) {
                e.setCancelled(false);

                if (options.equals("first")) {
                    if (target.getHealth() <= target.getMaxHealth()) {
                        if (plugin.getInFight().containsKey(damager)) {
                            plugin.getInFight().remove(damager);
                        } else if (plugin.getInFight().containsKey(target)) {
                            plugin.getInFight().remove(target);
                        }

                        showGameHolograms(damager, target);

                        if (bl.getBettingItems().containsKey(damager)) {
                            for (int i = 0; i < bl.getBettingItems().get(damager).size(); i++) {
                                damager.getInventory().addItem(bl.getBettingItems().get(damager).get(i));
                            }
                        }

                        if (!bl.getBettingItems().containsKey(target)) {
                            for (int i = 0; i < bl.getBettingItems().get(target).size(); i++) {
                                damager.getInventory().addItem(bl.getBettingItems().get(target).get(i));
                            }
                        }

                        bl.getBettingItems().remove(damager);
                        bl.getBettingItems().remove(target);

                        //sm.addWin();
                        //sm.setP(target);
                        //sm.addLose();
                    }
                } else if (options.equals("half")) {
                    if (target.getHealth() <= target.getMaxHealth() / 2) {
                        if (plugin.getInFight().containsKey(damager)) {
                            plugin.getInFight().remove(damager);
                        } else if (plugin.getInFight().containsKey(target)) {
                            plugin.getInFight().remove(target);
                        }

                        showGameHolograms(damager, target);

                        bl.getBettingItems().remove(damager);
                        bl.getBettingItems().remove(target);

                        //sm.addWin();
                        //sm.setP(target);
                        //sm.addLose();
                    }
                } else if (options.equals("death")) {
                    if (( target.getHealth() - e.getDamage()) <= 0) {
                        if (plugin.getInFight().containsKey(damager)) {
                            plugin.getInFight().remove(damager);
                        } else if (plugin.getInFight().containsKey(target)) {
                            plugin.getInFight().remove(target);
                        }

                        showGameHolograms(damager, target);

                        bl.getBettingItems().remove(damager);
                        bl.getBettingItems().remove(target);

                        //sm.addWin();
                        //sm.setP(target);
                        //sm.addLose();
                    }
                }
            } else {
                e.setCancelled(true);
            }
        } else {
            e.setCancelled(true);
        }
    }

    private ItemStack getItemStack(Material mat, String name) {
        ItemStack i = new ItemStack(mat);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);

        return i;
    }

    private ItemStack getItemStack(Material mat, String name, int amount) {
        ItemStack i = new ItemStack(mat);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setAmount(amount);
        i.setItemMeta(im);

        return i;
    }

    private void showGameHolograms(Player p, Player target) {
        final HologramUtil hu = new HologramUtil();
        hu.setName(hologramWin);

        final HologramUtil hu2 = new HologramUtil();
        hu2.setName(hologramLose);

        double rotation = (p.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }

        if (0 <= rotation && rotation < 67.5) {
            hu.setLoc(new Location(p.getWorld(), p.getLocation().getX() - 1, p.getLocation().getY() + 2, p.getLocation().getZ() - 1));
            hu2.setLoc(new Location(target.getWorld(), target.getLocation().getX(), target.getLocation().getY() + 2, target.getLocation().getZ() + 2));
        }  else if (67.5 <= rotation && rotation < 157.5) {
            hu.setLoc(new Location(p.getWorld(), p.getLocation().getX() + 2, p.getLocation().getY() + 2, p.getLocation().getZ()));
            hu2.setLoc(new Location(target.getWorld(), target.getLocation().getX() + 2, target.getLocation().getY() + 2, target.getLocation().getZ()));
        }  else if (157.5 <= rotation && rotation < 247.5) {
            hu.setLoc(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 2, p.getLocation().getZ() + 2));
            hu2.setLoc(new Location(target.getWorld(), target.getLocation().getX(), target.getLocation().getY() + 2, target.getLocation().getZ() - 2));
        }  else if (247.5 <= rotation && rotation < 337.5) {
            hu.setLoc(new Location(p.getWorld(), p.getLocation().getX() - 2, p.getLocation().getY() + 2, p.getLocation().getZ()));
            hu2.setLoc(new Location(target.getWorld(), target.getLocation().getX() - 2, target.getLocation().getY() + 2, target.getLocation().getZ()));
        }  else if (337.5 <= rotation && rotation < 360.0) {
            hu.setLoc(new Location(p.getWorld(), p.getLocation().getX() - 1, p.getLocation().getY() + 2, p.getLocation().getZ() - 1));
            hu2.setLoc(new Location(target.getWorld(), target.getLocation().getX(), target.getLocation().getY() + 2, target.getLocation().getZ()));
        }

        hu.spawn();
        hu2.spawn();

        t = new Thread(new Runnable() {
            boolean running = true;
            public void run() {
                while (running) {
                    for (int i = 5; i >= 0; i--) {
                        if (i == 0) {
                            running = false;
                            hu.remove();
                            hu2.remove();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        t.start();
    }

    public void setHologramMatchStart(String hologramMatchStart) {
        this.hologramMatchStart = hologramMatchStart;
    }

    public void setHologramWin(String hologramWin) {
        this.hologramWin = hologramWin;
    }

    public void setHologramLose(String hologramLose) {
        this.hologramLose = hologramLose;
    }
}
