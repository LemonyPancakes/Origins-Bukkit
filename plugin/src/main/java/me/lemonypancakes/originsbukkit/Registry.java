package me.lemonypancakes.originsbukkit;

import me.lemonypancakes.originsbukkit.util.Identifier;

import java.util.Collection;
import java.util.Set;

public interface Registry extends OriginsBukkitPluginHolder {

    void register(Action.Factory actionFactory);

    void register(Condition.Factory conditionFactory);

    void register(Origin origin);

    void register(Power power);

    void register(Power.Factory powerFactory);

    void register(OriginItem originItem);

    void unregisterActionFactory(Identifier identifier);

    void unregisterConditionFactory(Identifier identifier);

    void unregisterOrigin(Identifier identifier);

    void unregisterPower(Identifier identifier);

    void unregisterPowerFactory(Identifier identifier);

    void unregisterOriginItem(Identifier identifier);

    boolean hasActionFactory(Identifier identifier);

    boolean hasConditionFactory(Identifier identifier);

    boolean hasOrigin(Identifier identifier);

    boolean hasPower(Identifier identifier);

    boolean hasPowerFactory(Identifier identifier);

    boolean hasOriginItem(Identifier identifier);

    Action.Factory getActionFactory(Identifier identifier);

    Condition.Factory getConditionFactory(Identifier identifier);

    Origin getOrigin(Identifier identifier);

    Power getPower(Identifier identifier);

    Power.Factory getPowerFactory(Identifier identifier);

    OriginItem getOriginItem(Identifier identifier);

    Set<Identifier> getActionFactoriesKeySet();

    Set<Identifier> getConditionFactoriesKeySet();

    Set<Identifier> getOriginsKeySet();

    Set<Identifier> getPowersKeySet();

    Set<Identifier> getPowerFactoriesKeySet();

    Set<Identifier> getOriginItemsKeySet();

    Collection<Action.Factory> getActionFactories();

    Collection<Condition.Factory> getConditionFactories();

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
