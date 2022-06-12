package me.lemonypancakes.originsbukkit.factory.condition;

import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.*;
import me.lemonypancakes.originsbukkit.data.CraftCondition;
import me.lemonypancakes.originsbukkit.util.Identifier;

import java.util.Arrays;
import java.util.function.BiPredicate;

public class MetaConditions {

    public MetaConditions(OriginsBukkitPlugin plugin) {
        plugin.getRegistry().register(new Condition.Factory(new Identifier(OriginsBukkit.KEY, "and"), new Meta.And(plugin, null, null)));
        plugin.getRegistry().register(new Condition.Factory(new Identifier(OriginsBukkit.KEY, "or"), new Meta.Or(plugin, null, null)));
    }

    public static class Meta {

        public static class And extends CraftCondition {

            private Condition[] conditions;

            public And(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, Temp> biPredicate) {
                super(plugin, jsonObject, biPredicate);
                if (jsonObject != null) {
                    this.conditions = OriginsBukkit.getLoader().loadConditions(jsonObject.getAsJsonArray("condition"));
                    setBiPredicate(((jsonObject1, temp) -> Arrays.stream(conditions).allMatch(condition -> condition.test(temp))));
                }
            }
        }

        public static class Or extends CraftCondition {

            private Condition[] conditions;

            public Or(OriginsBukkitPlugin plugin, JsonObject jsonObject, BiPredicate<JsonObject, Temp> biPredicate) {
                super(plugin, jsonObject, biPredicate);
                if (jsonObject != null) {
                    this.conditions = OriginsBukkit.getLoader().loadConditions(jsonObject.getAsJsonArray("condition"));
                    setBiPredicate(((jsonObject1, temp) -> Arrays.stream(conditions).anyMatch(condition -> condition.test(temp))));
                }
            }
        }
    }
}
