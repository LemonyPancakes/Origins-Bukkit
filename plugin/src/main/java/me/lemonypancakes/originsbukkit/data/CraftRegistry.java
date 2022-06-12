package me.lemonypancakes.originsbukkit.data;

import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.storage.Misc;
import me.lemonypancakes.originsbukkit.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class CraftRegistry implements Registry {

    private final OriginsBukkitPlugin plugin;
    private final Map<Identifier, Action.Factory> actionFactoryMap = new HashMap<>();
    private final Map<Identifier, Condition.Factory> conditionFactoryMap = new HashMap<>();
    private final Map<Identifier, Origin> originMap = new HashMap<>();
    private final Map<Identifier, Power> powerMap = new HashMap<>();
    private final Map<Identifier, Power.Factory> powerFactoryMap = new HashMap<>();
    private final Map<Identifier, OriginItem> originItemMap = new HashMap<>();
    private final List<Origin> origins = new ArrayList<>();

    public CraftRegistry(OriginsBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void register(Action.Factory actionFactory) {
        Identifier identifier = actionFactory.getIdentifier();

        if (!actionFactoryMap.containsKey(identifier)) {
            actionFactoryMap.put(identifier, actionFactory);
        }
    }

    @Override
    public void register(Condition.Factory condition) {
        Identifier identifier = condition.getIdentifier();

        if (!conditionFactoryMap.containsKey(identifier)) {
            conditionFactoryMap.put(identifier, condition);
        }
    }

    @Override
    public void register(Origin origin) {
        Identifier identifier = origin.getIdentifier();

        if (!originMap.containsKey(identifier)) {
            originMap.put(identifier, origin);
            origins.add(origin);
            origins.sort(Comparator.comparing(Origin::getImpact));
            List<Inventory> inventoryList = new ArrayList<>();

            for (Origin origin1 : origins) {
                inventoryList.add(origin1.getInventoryGUI());
            }
            Misc.GUIS.clear();
            Misc.GUIS.addAll(inventoryList);
            if (!origin.getIdentifier().toString().equals("origins-bukkit:dummy_origin")) {
                Misc.ORIGINS_AS_STRING.add(origin.getIdentifier().toString());
            }
        }
    }

    @Override
    public void register(Power power) {
        Identifier identifier = power.getIdentifier();

        if (!powerMap.containsKey(identifier)) {
            powerMap.put(identifier, power);
        }
    }

    @Override
    public void register(Power.Factory powerFactory) {
        Identifier identifier = powerFactory.getIdentifier();

        if (!powerFactoryMap.containsKey(identifier)) {
            powerFactoryMap.put(identifier, powerFactory);
        }
    }

    @Override
    public void register(OriginItem originItem) {
        Identifier identifier = originItem.getIdentifier();

        if (!originItemMap.containsKey(identifier)) {
            if (originItem.getRecipe() != null) {
                Bukkit.addRecipe(originItem.getRecipe());
            }
            originItemMap.put(identifier, originItem);
        }
    }

    @Override
    public void unregisterActionFactory(Identifier identifier) {
        actionFactoryMap.remove(identifier);
    }

    @Override
    public void unregisterConditionFactory(Identifier identifier) {
        conditionFactoryMap.remove(identifier);
    }

    @Override
    public void unregisterOrigin(Identifier identifier) {
        originMap.remove(identifier);
    }

    @Override
    public void unregisterPower(Identifier identifier) {
        powerMap.remove(identifier);
    }

    @Override
    public void unregisterPowerFactory(Identifier identifier) {
        powerFactoryMap.remove(identifier);
    }

    @Override
    public void unregisterOriginItem(Identifier identifier) {
        if (originItemMap.containsKey(identifier)) {
            OriginItem originItem = originItemMap.get(identifier);

            if (originItem != null) {
                if (originItem.getRecipe() != null) {
                    Bukkit.removeRecipe(identifier.toNameSpacedKey());
                }
                originItemMap.remove(identifier);
            }
        }
    }

    @Override
    public boolean hasActionFactory(Identifier identifier) {
        return actionFactoryMap.containsKey(identifier);
    }

    @Override
    public boolean hasConditionFactory(Identifier identifier) {
        return conditionFactoryMap.containsKey(identifier);
    }

    @Override
    public boolean hasOrigin(Identifier identifier) {
        return originMap.containsKey(identifier);
    }

    @Override
    public boolean hasPower(Identifier identifier) {
        return powerMap.containsKey(identifier);
    }

    @Override
    public boolean hasPowerFactory(Identifier identifier) {
        return powerFactoryMap.containsKey(identifier);
    }

    @Override
    public boolean hasOriginItem(Identifier identifier) {
        return originItemMap.containsKey(identifier);
    }

    @Override
    public Action.Factory getActionFactory(Identifier identifier) {
        return actionFactoryMap.get(identifier);
    }

    @Override
    public Condition.Factory getConditionFactory(Identifier identifier) {
        return conditionFactoryMap.get(identifier);
    }

    @Override
    public Origin getOrigin(Identifier identifier) {
        return originMap.get(identifier);
    }

    @Override
    public Power getPower(Identifier identifier) {
        return powerMap.get(identifier);
    }

    @Override
    public Power.Factory getPowerFactory(Identifier identifier) {
        return powerFactoryMap.get(identifier);
    }

    @Override
    public OriginItem getOriginItem(Identifier identifier) {
        return originItemMap.get(identifier);
    }

    @Override
    public Set<Identifier> getActionFactoriesKeySet() {
        return actionFactoryMap.keySet();
    }

    @Override
    public Set<Identifier> getConditionFactoriesKeySet() {
        return conditionFactoryMap.keySet();
    }

    @Override
    public Set<Identifier> getOriginsKeySet() {
        return originMap.keySet();
    }

    @Override
    public Set<Identifier> getPowersKeySet() {
        return powerMap.keySet();
    }

    @Override
    public Set<Identifier> getPowerFactoriesKeySet() {
        return powerFactoryMap.keySet();
    }

    @Override
    public Set<Identifier> getOriginItemsKeySet() {
        return originItemMap.keySet();
    }

    @Override
    public Collection<Action.Factory> getActionFactories() {
        return actionFactoryMap.values();
    }

    @Override
    public Collection<Condition.Factory> getConditionFactories() {
        return conditionFactoryMap.values();
    }

    @Override
    public Collection<Origin> getOrigins() {
        return originMap.values();
    }

    @Override
    public Collection<Power> getPowers() {
        return powerMap.values();
    }

    @Override
    public Collection<Power.Factory> getPowerFactories() {
        return powerFactoryMap.values();
    }

    @Override
    public Collection<OriginItem> getOriginItems() {
        return originItemMap.values();
    }

    @Override
    public boolean isActionFactoriesEmpty() {
        return actionFactoryMap.isEmpty();
    }

    @Override
    public boolean isConditionFactoriesEmpty() {
        return conditionFactoryMap.isEmpty();
    }

    @Override
    public boolean isOriginsEmpty() {
        return originMap.isEmpty();
    }

    @Override
    public boolean isPowersEmpty() {
        return powerMap.isEmpty();
    }

    @Override
    public boolean isPowerFactoriesEmpty() {
        return powerFactoryMap.isEmpty();
    }

    @Override
    public boolean isOriginItemsEmpty() {
        return originItemMap.isEmpty();
    }

    @Override
    public OriginsBukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void setPlugin(OriginsBukkitPlugin plugin) {}
}
