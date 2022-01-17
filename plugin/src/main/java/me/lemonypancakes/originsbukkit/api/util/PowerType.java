package me.lemonypancakes.originsbukkit.api.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lemonypancakes.originsbukkit.api.data.container.ActionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.ConditionContainer;
import me.lemonypancakes.originsbukkit.api.data.container.IdentifierContainer;
import me.lemonypancakes.originsbukkit.api.data.container.power.SinglePowerContainer;
import me.lemonypancakes.originsbukkit.api.data.type.*;
import me.lemonypancakes.originsbukkit.util.Catcher;
import me.lemonypancakes.originsbukkit.util.Storage;
import org.apache.commons.jexl3.*;

import java.util.*;

@SuppressWarnings({"unchecked", "unused"})
public enum PowerType {

    SINGLE("Single") {
        @Override
        public Power newInstance(Identifier identifier, JsonObject powerSection) {
            Power power = new SinglePowerContainer(identifier);

            power.setJsonObject(powerSection);
            if (powerSection != null) {
                if (powerSection.has("action") && !powerSection.has("actions")) {
                    JsonObject action = powerSection.getAsJsonObject("action");

                    if (action != null) {
                        if (action.has("type")) {
                            String actionType = action.get("type").getAsString();

                            if (actionType != null) {
                                Identifier actionTypeIdentifier = new IdentifierContainer(
                                        actionType.split(":")[0],
                                        actionType.split(":")[1]
                                );

                                if (action.has("fields")) {
                                    JsonObject fields = action.getAsJsonObject("fields");

                                    if (fields != null) {
                                        Storage.getActionsData().forEach((key, value) -> {
                                            if (Catcher.catchDuplicate(key, actionTypeIdentifier)) {
                                                Action<?> actionBuff = new ActionContainer<>(
                                                        key,
                                                        fields,
                                                        value.getBiConsumer()
                                                );
                                                power.setActions(new Action<?>[]{actionBuff});
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                } else if (powerSection.has("actions") && !powerSection.has("action")) {
                    JsonObject[] actions
                            = new Gson().fromJson(
                            powerSection.get(
                                    "actions"
                            ),
                            JsonObject[].class
                    );
                    List<Action<?>> actionList = new ArrayList<>();

                    if (actions != null) {
                        for (JsonObject action : actions) {
                            if (action.has("type")) {
                                String actionType = action.get("type").getAsString();

                                if (actionType != null) {
                                    Identifier actionTypeIdentifier = new IdentifierContainer(
                                            actionType.split(":")[0],
                                            actionType.split(":")[1]
                                    );

                                    if (action.has("fields")) {
                                        JsonObject fields = action.getAsJsonObject("fields");

                                        if (fields != null) {
                                            Storage.getActionsData().forEach((key, value) -> {
                                                if (Catcher.catchDuplicate(key, actionTypeIdentifier)) {
                                                    Action<?> actionBuff = new ActionContainer<>(
                                                            key,
                                                            fields,
                                                            value.getBiConsumer()
                                                    );
                                                    actionList.add(actionBuff);
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        }
                        power.setActions(
                                actionList.toArray(
                                        new Action<?>[0]
                                )
                        );
                    }
                }
                if (powerSection.has("condition")) {
                    JsonObject[] condition
                            = new Gson().fromJson(
                            powerSection.get(
                                    "condition"
                            ),
                            JsonObject[].class
                    );
                    Map<Object, Object> buffer = new HashMap<>();
                    StringBuilder condition1 = new StringBuilder();

                    for (JsonObject cond : condition) {
                        String randString = getSaltString();

                        if (cond.has("type")) {
                            String conditionType = cond.get("type").getAsString();

                            if (conditionType != null) {
                                Identifier conditionIdentifier = new IdentifierContainer(
                                        conditionType.split(":")[0],
                                        conditionType.split(":")[1]
                                );

                                if (cond.has("fields")) {
                                    JsonObject fields = cond.getAsJsonObject("fields");

                                    if (fields != null) {
                                        Storage.getConditionsData().forEach((key, value) -> {
                                            if (Catcher.catchDuplicate(key, conditionIdentifier)) {
                                                Condition<?> conditionBuff = new ConditionContainer<>(
                                                        key,
                                                        fields,
                                                        value.getBiPredicate()
                                                );
                                                buffer.put(randString, conditionBuff);
                                                condition1.append(randString).append(" ");
                                            }
                                        });
                                    }
                                }
                            }
                        } else if (cond.has("operator")) {
                            String operator = cond.get("operator").getAsString();

                            if (operator != null) {
                                condition1.append(operator).append(" ");
                            }
                        }
                    }
                    JexlEngine jexlEngine = new JexlBuilder().create();
                    JexlExpression expression = jexlEngine.createExpression(condition1.toString());

                    power.setCondition(
                            new ConditionContainer<Temp>(
                                    null,
                                    null,
                                    (data, temp) -> {
                                        JexlContext context = new MapContext();
                                        buffer.forEach((key, value) -> {
                                            if (value instanceof Condition) {
                                                context.set(
                                                        key.toString(),
                                                        ((Condition<Object>) value).test(temp)
                                                );
                                            }
                                        });

                                        return (boolean) expression.evaluate(context);
                                    }
                            )
                    );
                }
            }
            return power;
        }

        @Override
        public Power newInstance(Identifier identifier) {
            return new SinglePowerContainer(identifier);
        }
    },
    LISTENER("Listener") {
        @Override
        public Power newInstance(Identifier identifier, JsonObject powerSection) {
            Power power = null;

            if (powerSection != null) {
                if (powerSection.has("listener")) {
                    String listenerString = powerSection.get("listener").getAsString();

                    if (listenerString != null) {
                        Identifier listenerIdentifier = new IdentifierContainer(
                                listenerString.split(":")[0],
                                listenerString.split(":")[1]
                        );

                        for (Map.Entry<Identifier, Power> entry : Storage.getPowersData().entrySet()) {
                            if (Catcher.catchDuplicate(entry.getKey(), listenerIdentifier)) {
                                power = ((Listener) entry.getValue()).newInstance(identifier, powerSection);
                                break;
                            }
                        }
                    }
                }
                if (power != null) {

                    power.setJsonObject(powerSection);
                    if (powerSection.has("action") && !powerSection.has("actions")) {
                        JsonObject action = powerSection.getAsJsonObject("action");

                        if (action != null) {
                            if (action.has("type")) {
                                String actionType = action.get("type").getAsString();

                                if (actionType != null) {
                                    Identifier actionTypeIdentifier = new IdentifierContainer(
                                            actionType.split(":")[0],
                                            actionType.split(":")[1]
                                    );

                                    if (action.has("fields")) {
                                        JsonObject fields = action.getAsJsonObject("fields");

                                        if (fields != null) {
                                            for (Map.Entry<Identifier, Action<?>> entry : Storage.getActionsData().entrySet()) {
                                                if (Catcher.catchDuplicate(entry.getKey(), actionTypeIdentifier)) {
                                                    Action<?> actionBuff = new ActionContainer<>(
                                                            entry.getKey(),
                                                            fields,
                                                            entry.getValue().getBiConsumer()
                                                    );
                                                    power.setActions(new Action<?>[]{actionBuff});
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (powerSection.has("actions") && !powerSection.has("action")) {
                        JsonObject[] actions
                                = new Gson().fromJson(
                                powerSection.get(
                                        "actions"
                                ),
                                JsonObject[].class
                        );
                        List<Action<?>> actionList = new ArrayList<>();

                        if (actions != null) {
                            for (JsonObject action : actions) {
                                if (action.has("type")) {
                                    String actionType = action.get("type").getAsString();

                                    if (actionType != null) {
                                        Identifier actionTypeIdentifier = new IdentifierContainer(
                                                actionType.split(":")[0],
                                                actionType.split(":")[1]
                                        );

                                        if (action.has("fields")) {
                                            JsonObject fields = action.getAsJsonObject("fields");

                                            if (fields != null) {
                                                Storage.getActionsData().forEach((key, value) -> {
                                                    if (Catcher.catchDuplicate(key, actionTypeIdentifier)) {
                                                        Action<?> actionBuff = new ActionContainer<>(
                                                                key,
                                                                fields,
                                                                value.getBiConsumer()
                                                        );
                                                        actionList.add(actionBuff);
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                            }
                            power.setActions(
                                    actionList.toArray(
                                            new Action<?>[0]
                                    )
                            );
                        }
                    }
                    if (powerSection.has("condition")) {
                        JsonObject[] condition
                                = new Gson().fromJson(
                                powerSection.get(
                                        "condition"
                                ),
                                JsonObject[].class
                        );
                        Map<Object, Object> buffer = new HashMap<>();
                        StringBuilder condition1 = new StringBuilder();

                        for (JsonObject cond : condition) {
                            String randString = getSaltString();

                            if (cond.has("type")) {
                                String conditionType = cond.get("type").getAsString();

                                if (conditionType != null) {
                                    Identifier conditionIdentifier = new IdentifierContainer(
                                            conditionType.split(":")[0],
                                            conditionType.split(":")[1]
                                    );

                                    if (cond.has("fields")) {
                                        JsonObject fields = cond.getAsJsonObject("fields");

                                        if (fields != null) {
                                            Storage.getConditionsData().forEach((key, value) -> {
                                                if (Catcher.catchDuplicate(key, conditionIdentifier)) {
                                                    Condition<?> conditionBuff = new ConditionContainer<>(
                                                            key,
                                                            fields,
                                                            value.getBiPredicate()
                                                    );
                                                    buffer.put(randString, conditionBuff);
                                                    condition1.append(randString).append(" ");
                                                }
                                            });
                                        }
                                    }
                                }
                            } else if (cond.has("operator")) {
                                String operator = cond.get("operator").getAsString();

                                if (operator != null) {
                                    condition1.append(operator).append(" ");
                                }
                            }
                        }
                        JexlEngine jexlEngine = new JexlBuilder().create();
                        JexlExpression expression = jexlEngine.createExpression(condition1.toString());

                        power.setCondition(
                                new ConditionContainer<Temp>(
                                        null,
                                        null,
                                        (data, temp) -> {
                                            JexlContext context = new MapContext();
                                            buffer.forEach((key, value) -> {
                                                if (value instanceof Condition) {
                                                    context.set(
                                                            key.toString(),
                                                            ((Condition<Object>) value).test(temp)
                                                    );
                                                }
                                            });

                                            return (boolean) expression.evaluate(context);
                                        }
                                )
                        );
                    }
                }
            }
            return power;
        }

        @Override
        public Power newInstance(Identifier identifier) {
            return null;
        }
    };

    private final String powerType;

    PowerType(String powerType) {
        this.powerType = powerType;
    }

    public static PowerType parsePower(PowerType powerType) {
        for (PowerType pt : values()) {
            if (pt == powerType) {
                return pt;
            }
        }
        return null;
    }

    public static PowerType parsePower(String powerType) {
        for (PowerType pt : values()) {
            if (pt.powerType.equals(powerType)) {
                return pt;
            }
        }
        return null;
    }

    public abstract Power newInstance(Identifier identifier,
                                      JsonObject powerSection);

    public abstract Power newInstance(Identifier identifier);

    private static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 7) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
