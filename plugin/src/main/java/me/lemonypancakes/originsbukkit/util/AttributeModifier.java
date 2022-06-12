package me.lemonypancakes.originsbukkit.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.NoSuchElementException;

public enum AttributeModifier {

    LESS_THAN("addition") {
        @Override public double apply(double baseValue, double currentValue, double modifierValue) {
            return baseValue + modifierValue;
        }
    },
    GREATER_THAN("multiply_base") {
        @Override public double apply(double baseValue, double currentValue, double modifierValue) {
            return currentValue + (baseValue * modifierValue);
        }
    },
    LESS_THAN_OR_EQUAL_TO("multiply_total") {
        @Override public double apply(double baseValue, double currentValue, double modifierValue) {
            return currentValue * (1 + modifierValue);
        }
    };

    private final String operation;

    AttributeModifier(String operation) {
        this.operation = operation;
    }

    public static AttributeModifier parse(String string) {
        for (AttributeModifier attributeModifier : values()) {
            if (attributeModifier.operation.equals(string)) {
                return attributeModifier;
            }
        }
        throw new NoSuchElementException(String.format("Unknown operator [%s]", string));
    }

    public static double calculate(JsonObject jsonObject, double baseValue) {
        JsonObject modifier = jsonObject.getAsJsonObject("modifier");
        JsonObject[] modifiers = new Gson().fromJson(jsonObject.getAsJsonObject("modifiers"), JsonObject[].class);

        if (modifier != null && modifiers == null) {
            String operation = modifier.get("operation").getAsString();

            if (operation != null) {
                double value = modifier.get("value").getAsDouble();

                return AttributeModifier.parse(operation).apply(baseValue, 0, value);
            }
        } else if (modifiers != null && modifier == null) {

        } else {
            ChatUtils.sendConsoleMessage("&c[Origins-Bukkit] Cannot have 2 fields of modifiers at the same time.");
        }
        return 0;
    }

    public abstract double apply(double baseValue, double currentValue, double modifierValue);
}
