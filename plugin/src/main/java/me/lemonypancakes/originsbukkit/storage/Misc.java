package me.lemonypancakes.originsbukkit.storage;

import org.bukkit.inventory.Inventory;

import java.util.*;

public class Misc {

    public static final List<Inventory> GUIS = new ArrayList<>();

    public static final Map<UUID, Integer> VIEWERS = new HashMap<>();

    public static final List<String> ORIGINS_AS_STRING = new ArrayList<>();

    public static final Map<String, Inventory> ORIGINS_INFO_GUI = new HashMap<>();

    public static final Map<UUID, String> TEMPORARY_DATA = new HashMap<>();
}
