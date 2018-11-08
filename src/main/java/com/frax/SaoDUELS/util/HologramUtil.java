package com.frax.SaoDUELS.util;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class HologramUtil {

    private String name;
    private Location loc;
    private double height;

    private ArmorStand hologram = null;

    public HologramUtil(Location loc, String name, double height) {
        this.name = name;
        this.loc = loc;
        this.height = height;
    }

    public HologramUtil() { }

    public void spawn() {
        loc.setY((loc.getY() + height) - 1.25);

        hologram = (ArmorStand)this.loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        hologram.setCustomName(name);
        hologram.setCustomNameVisible(true);
        hologram.setGravity(false);
        hologram.setVisible(false);
    }

    public void remove() {
        if (existHologram()) {
            hologram.remove();
        }
    }

    public void changeName(String name) {
        if (existHologram()) {
            hologram.setCustomName(name);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    private boolean existHologram() {
        if (hologram != null) {
            return true;
        } else {
            return false;
        }
    }

}
