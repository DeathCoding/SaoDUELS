package com.frax.SaoDUELS.listener;

import com.frax.SaoDUELS.SaoDUELS;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BettingListener implements Listener {

    private HashMap<Player, Player> bettingPlayers;
    private HashMap<Player, List<ItemStack>> bettingItems;
    private HashMap<Player, Integer> bettingMoney;
    private SaoDUELS plugin;
    private Thread t;
    private List<Thread> running;

    public BettingListener(SaoDUELS plugin) {
        bettingPlayers = new HashMap<Player, Player>();
        bettingItems = new HashMap<Player, List<ItemStack>>();
        bettingMoney = new HashMap<Player, Integer>();
        this.plugin = plugin;
        running = new ArrayList<Thread>();
    }

    public void addPlayersToBetting(Player p1, Player p2) {
        bettingPlayers.put(p1, p2);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            e.setCancelled(false);
        } else {
                Player p = (Player) e.getWhoClicked();

                //TODO UPDATE MONEY

                if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6100 §7cor")) {
                    if (bettingMoney.containsKey(p)) {
                        int p1 = bettingMoney.get(p) + 100;
                        bettingMoney.remove(p);
                        bettingMoney.put(p, p1);
                        if (e.getInventory().getItem(47).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(47);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6" + p1));
                            i.setItemMeta(im);
                        } else if (e.getInventory().getItem(51).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(51);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6" + p1));
                            i.setItemMeta(im);
                        }
                    } else {
                        bettingMoney.put(p, 100);

                        if (e.getInventory().getItem(47).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(47);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6100"));
                            i.setItemMeta(im);
                        } else if (e.getInventory().getItem(51).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(51);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6100"));
                            i.setItemMeta(im);
                        }
                    }
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6500 §7cor")) {
                    if (bettingMoney.containsKey(p)) {
                        int p1 = bettingMoney.get(p) + 500;
                        bettingMoney.remove(p);
                        bettingMoney.put(p, p1);

                        if (e.getInventory().getItem(47).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(51);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6" + p1));
                            i.setItemMeta(im);
                        } else if (e.getInventory().getItem(51).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(51);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6" + p1));
                            i.setItemMeta(im);
                        }
                    } else {
                        bettingMoney.put(p, 500);

                        if (e.getInventory().getItem(47).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(47);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6500"));
                            i.setItemMeta(im);
                        } else if (e.getInventory().getItem(51).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(51);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6500"));
                            i.setItemMeta(im);
                        }
                    }
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§61000 §7cor")) {
                    if (bettingMoney.containsKey(p)) {
                        int p1 = bettingMoney.get(p) + 1000;
                        bettingMoney.remove(p);
                        bettingMoney.put(p, p1);

                        if (e.getInventory().getItem(47).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(47);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6" + p1));
                            i.setItemMeta(im);
                        } else if (e.getInventory().getItem(51).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(51);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6" + p1));
                            i.setItemMeta(im);
                        }
                    } else {
                        bettingMoney.put(p, 1000);

                        if (e.getInventory().getItem(47).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(47);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §61000"));
                            i.setItemMeta(im);
                        } else if (e.getInventory().getItem(51).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(51);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §61000"));
                            i.setItemMeta(im);
                        }
                    }
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§610000 §7cor")) {
                    if (bettingMoney.containsKey(p)) {
                        int p1 = bettingMoney.get(p) + 10000;
                        bettingMoney.remove(p);
                        bettingMoney.put(p, p1);

                        if (e.getInventory().getItem(47).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(47);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6" + p1));
                            i.setItemMeta(im);
                        } else if (e.getInventory().getItem(51).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(51);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §6" + p1));
                            i.setItemMeta(im);
                        }
                    } else {
                        bettingMoney.put(p, 10000);

                        if (e.getInventory().getItem(47).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(47);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §610000"));
                            i.setItemMeta(im);
                        } else if (e.getInventory().getItem(51).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            ItemStack i = e.getInventory().getItem(51);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §610000"));
                            i.setItemMeta(im);
                        }
                    }
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                    e.setCancelled(true);
                    if (bettingMoney.containsKey(p)) {
                        bettingMoney.remove(p);

                        if (e.getInventory().getItem(47).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            e.setCancelled(true);
                            ItemStack i = e.getInventory().getItem(47);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §60"));
                            i.setItemMeta(im);
                        } else if (e.getInventory().getItem(51).getItemMeta().getDisplayName().equals("§a" + p.getName())) {
                            e.setCancelled(true);
                            ItemStack i = e.getInventory().getItem(51);
                            ItemMeta im = i.getItemMeta();
                            im.setLore(Arrays.asList("§7Cor: §60"));
                            i.setItemMeta(im);
                        }
                    }
                }

                if (bettingPlayers.containsKey(p)) { //player 1
                    if (e.getSlot() <= 3 || (e.getSlot() <= 12 && e.getSlot() >= 9) || (e.getSlot() <= 21 && e.getSlot() >= 18) || (e.getSlot() <= 30 && e.getSlot() >= 27) || (e.getSlot() <= 39 && e.getSlot() >= 36) || (e.getSlot() <= 48 && e.getSlot() >= 45)) {
                        e.setCancelled(false);

                        if (e.getClick().isShiftClick()) {
                            e.setCancelled(true);
                            return;
                        }

                        if (!(e.getCurrentItem().getType() == null) || !(e.getCurrentItem() == null) && !(e.getSlot() == 45)) {
                            running.remove(t);
                        }

                        if (e.getSlot() == 45) {
                            e.setCancelled(true);
                            accept(p, e.getCurrentItem(), e.getInventory());
                        } else if (e.getSlot() == 46) {
                            e.setCancelled(true);
                            for (int i = 0; i < e.getInventory().getViewers().size(); i++) {
                                Bukkit.getPlayer(e.getInventory().getViewers().get(i).getName()).closeInventory();
                            }

                            bettingPlayers.remove(p);
                            plugin.getRequest().remove(p);
                        }
                    } else {
                        e.setCancelled(true);
                    }
                } else { //player 2

                    if ((e.getSlot() >= 5 && e.getSlot() <= 8) || (e.getSlot() <= 17 && e.getSlot() >= 14) || (e.getSlot() <= 26 && e.getSlot() >= 23) || (e.getSlot() <= 35 && e.getSlot() >= 32) || (e.getSlot() <= 44 && e.getSlot() >= 41) || e.getSlot() >= 50) {
                        e.setCancelled(false);

                        if (e.getClick().isShiftClick()) {
                            e.setCancelled(true);
                            return;
                        }

                        if (!(e.getCurrentItem().getType() == null) || !(e.getCurrentItem() == null) && !(e.getSlot() == 52)) {
                            running.remove(t);
                        }

                        if (e.getSlot() == 52) {
                            e.setCancelled(true);
                            accept(p, e.getCurrentItem(), e.getInventory());
                        } else if (e.getSlot() == 53) {
                            e.setCancelled(true);
                            for (int i = 0; i < e.getInventory().getViewers().size(); i++) {
                                Bukkit.getPlayer(e.getInventory().getViewers().get(i).getName()).closeInventory();
                            }

                            bettingPlayers.remove(p);
                            plugin.getRequest().remove(p);
                        }
                    } else {
                        e.setCancelled(true);
                    }
                }
            }
        }

    public void accept(Player p, ItemStack item, Inventory inv) {
        Thread t = new Thread();
        if (item.getType().equals(Material.POISONOUS_POTATO)) {
            item.setType(Material.EMERALD_BLOCK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Accepted");
            item.setItemMeta(meta);

            if (inv.getItem(45).getType() == Material.EMERALD_BLOCK && inv.getItem(52).getType() == Material.EMERALD_BLOCK) {
                finishBet(inv);
            }
        } else if (item.getType().equals(Material.EMERALD_BLOCK)) {
            item.setType(Material.POISONOUS_POTATO);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Accept");
            item.setItemMeta(meta);
        }
    }

    public void finishBet(final Inventory inv) {
        List<HumanEntity> viewers = inv.getViewers();
        final Player p1;
        final Player p2;
        if (bettingPlayers.containsKey((Player) viewers.get(0))) {
            p1 = (Player) viewers.get(0);
            p2 = (Player) viewers.get(1);
        } else {
            p1 = (Player) viewers.get(1);
            p2 = (Player) viewers.get(0);
        }

        t = new Thread(new Runnable() {
            public void run() {
                while (running.contains(t)) {
                    for (int j = 5; j >= 0; j--) {

                        ItemStack item = inv.getItem(45);
                        item.setAmount(j);

                        ItemStack item2 = inv.getItem(52);
                        item2.setAmount(j);

                        if (!running.contains(t)) {
                            running.remove(t);
                            try {
                                ItemStack i = inv.getItem(47);
                                i.setType(Material.POISONOUS_POTATO);
                                i.setAmount(1);
                                ItemMeta im = i.getItemMeta();
                                im.setDisplayName("Accept");
                                i.setItemMeta(im);

                                ItemStack i2 = inv.getItem(51);
                                i2.setType(Material.POISONOUS_POTATO);
                                i2.setAmount(1);
                                ItemMeta im2 = i2.getItemMeta();
                                im2.setDisplayName("Accept");
                                i2.setItemMeta(im);

                                t.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        if (j == 0) {
                            running.remove(t);
                            p1.closeInventory();
                            p2.closeInventory();

                            List<ItemStack> p1Items = new ArrayList<ItemStack>();
                            List<ItemStack> p2Items = new ArrayList<ItemStack>();

                            for (int i = 0; i < 51; i++) {
                                if (i <= 3 || (i <= 12 && i >= 9) || (i <= 21 && i >= 18) || (i <= 30 && i >= 27) || (i <= 39 && i >= 36) || (i <= 48 && i >= 47)) {
                                    if (!(inv.getItem(i) == null)) {
                                        p1Items.add(inv.getItem(i));
                                    }
                                } else if ((i >= 5 && i <= 8) || (i <= 17 && i >= 14) || (i <= 26 && i >= 23) || (i <= 35 && i >= 32) || (i <= 44 && i >= 41) || (i >= 50 && i <= 51)) {
                                    if (!(inv.getItem(i) == null)) {
                                        p2Items.add(inv.getItem(i));
                                    }
                                }
                            }

                            if (!(p1Items.isEmpty())) {
                                bettingItems.put(p1, p1Items);
                            }

                            if (!(p2Items.isEmpty())) {
                                bettingItems.put(p2, p2Items);
                            }

                            bettingPlayers.remove(p1);
                            Inventory invEn = Bukkit.createInventory(null, 9, "options");

                            invEn.setItem(2, getItemStack(Material.FISHING_ROD, "first hit"));
                            invEn.setItem(4, getItemStack(Material.ITEM_FRAME, "half life"));
                            invEn.setItem(6, getDeath());

                            p1.openInventory(invEn);
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, p1.getName());



        t.start();
    }

    private ItemStack getItemStack(Material mat, String name) {
        ItemStack i = new ItemStack(mat);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);

        return i;
    }

    private ItemStack getDeath() {
        ItemStack i = new ItemStack(Material.SKULL_ITEM);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName("death");
        im.setLore(Arrays.asList("§4Warning:", "§cEXP §cCor Penalty will", "§coccur on death!"));
        i.setItemMeta(im);

        return i;
    }

    public HashMap<Player, Player> getBettingPlayers() {
        return bettingPlayers;
    }

    public HashMap<Player, List<ItemStack>> getBettingItems() {
        return bettingItems;
    }
}
