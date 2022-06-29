package me.lemonypancakes.bukkit.origins;

import me.lemonypancakes.bukkit.origins.util.Identifier;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Registry extends OriginsBukkitPluginHolder {

    void register(Action.Factory<?> actionFactory);

    void register(Condition.Factory<?> conditionFactory);

    void register(Origin origin);

    void register(Power power);

    void register(Power.Factory powerFactory);

    void register(OriginItem originItem);

    <T> void unregisterActionFactory(DataType<T> dataType, Identifier identifier);

    <T> void unregisterConditionFactory(DataType<T> dataType, Identifier identifier);

    void unregisterOrigin(Identifier identifier);

    void unregisterPower(Identifier identifier);

    void unregisterPowerFactory(Identifier identifier);

    void unregisterOriginItem(Identifier identifier);

    <T> boolean hasActionFactory(DataType<T> dataType, Identifier identifier);

    <T> boolean hasConditionFactory(DataType<T> dataType, Identifier identifier);

    boolean hasOrigin(Identifier identifier);

    boolean hasPower(Identifier identifier);

    boolean hasPowerFactory(Identifier identifier);

    boolean hasOriginItem(Identifier identifier);

    <T> Action.Factory<T> getActionFactory(DataType<T> dataType, Identifier identifier);

    <T> Condition.Factory<T> getConditionFactory(DataType<T> dataType, Identifier identifier);

    Origin getOrigin(Identifier identifier);

    Power getPower(Identifier identifier);

    Power.Factory getPowerFactory(Identifier identifier);

    OriginItem getOriginItem(Identifier identifier);

    Set<DataType<?>> getActionFactoriesKeySet();

    Set<DataType<?>> getConditionFactoriesKeySet();

    Set<Identifier> getOriginsKeySet();

    Set<Identifier> getPowersKeySet();

    Set<Identifier> getPowerFactoriesKeySet();

    Set<Identifier> getOriginItemsKeySet();

    Collection<Map<Identifier, Action.Factory<?>>> getActionFactories();

    Collection<Map<Identifier, Condition.Factory<?>>> getConditionFactories();

    Collection<Origin> getOrigins();

    Collection<Power> getPowers();

    Collection<Power.Factory> getPowerFactories();

    Collection<OriginItem> getOriginItems();

    boolean isActionFactoriesEmpty();

    boolean isConditionFactoriesEmpty();

    boolean isOriginsEmpty();

    boolean isPowersEmpty();

    boolean isPowerFactoriesEmpty();

    boolean isOriginItemsEmpty();
}
