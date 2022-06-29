/*
 * Origins-Bukkit - Origins for Bukkit and forks of Bukkit.
 * Copyright (C) 2022 LemonyPancakes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.lemonypancakes.bukkit.origins.util;

import com.google.gson.JsonObject;

import java.util.NoSuchElementException;

public enum Comparison {
    LESS_THAN("<") {

        @Override
        public boolean compare(int toCompare, int compareTo) {
            return toCompare < compareTo;
        }

        @Override
        public boolean compare(double toCompare, double compareTo) {
            return toCompare < compareTo;
        }

        @Override
        public boolean compare(float toCompare, float compareTo) {
            return toCompare < compareTo;
        }

        @Override
        public boolean compare(long toCompare, long compareTo) {
            return toCompare < compareTo;
        }

        @Override
        public boolean compare(int toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    int compareTo = jsonObject.get("compare_to").getAsInt();

                    return toCompare < compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(double toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    double compareTo = jsonObject.get("compare_to").getAsDouble();

                    return toCompare < compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(float toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    float compareTo = jsonObject.get("compare_to").getAsFloat();

                    return toCompare < compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(long toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    long compareTo = jsonObject.get("compare_to").getAsLong();

                    return toCompare < compareTo;
                }
            }
            return false;
        }
    },
    LESS_THAN_OR_EQUAL_TO("<=") {

        @Override
        public boolean compare(int toCompare, int compareTo) {
            return toCompare <= compareTo;
        }

        @Override
        public boolean compare(double toCompare, double compareTo) {
            return toCompare <= compareTo;
        }

        @Override
        public boolean compare(float toCompare, float compareTo) {
            return toCompare <= compareTo;
        }

        @Override
        public boolean compare(long toCompare, long compareTo) {
            return toCompare <= compareTo;
        }

        @Override
        public boolean compare(int toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    int compareTo = jsonObject.get("compare_to").getAsInt();

                    return toCompare <= compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(double toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    double compareTo = jsonObject.get("compare_to").getAsDouble();

                    return toCompare <= compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(float toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    float compareTo = jsonObject.get("compare_to").getAsFloat();

                    return toCompare <= compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(long toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    long compareTo = jsonObject.get("compare_to").getAsLong();

                    return toCompare <= compareTo;
                }
            }
            return false;
        }
    },
    GREATER_THAN(">") {

        @Override
        public boolean compare(int toCompare, int compareTo) {
            return toCompare > compareTo;
        }

        @Override
        public boolean compare(double toCompare, double compareTo) {
            return toCompare > compareTo;
        }

        @Override
        public boolean compare(float toCompare, float compareTo) {
            return toCompare > compareTo;
        }

        @Override
        public boolean compare(long toCompare, long compareTo) {
            return toCompare > compareTo;
        }

        @Override
        public boolean compare(int toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    int compareTo = jsonObject.get("compare_to").getAsInt();

                    return toCompare > compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(double toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    double compareTo = jsonObject.get("compare_to").getAsDouble();

                    return toCompare > compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(float toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    float compareTo = jsonObject.get("compare_to").getAsFloat();

                    return toCompare > compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(long toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    long compareTo = jsonObject.get("compare_to").getAsLong();

                    return toCompare > compareTo;
                }
            }
            return false;
        }
    },
    GREATER_THAN_OR_EQUAL_TO(">=") {

        @Override
        public boolean compare(int toCompare, int compareTo) {
            return toCompare >= compareTo;
        }

        @Override
        public boolean compare(double toCompare, double compareTo) {
            return toCompare >= compareTo;
        }

        @Override
        public boolean compare(float toCompare, float compareTo) {
            return toCompare >= compareTo;
        }

        @Override
        public boolean compare(long toCompare, long compareTo) {
            return toCompare >= compareTo;
        }

        @Override
        public boolean compare(int toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    int compareTo = jsonObject.get("compare_to").getAsInt();

                    return toCompare >= compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(double toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    double compareTo = jsonObject.get("compare_to").getAsDouble();

                    return toCompare >= compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(float toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    float compareTo = jsonObject.get("compare_to").getAsFloat();

                    return toCompare >= compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(long toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    long compareTo = jsonObject.get("compare_to").getAsLong();

                    return toCompare >= compareTo;
                }
            }
            return false;
        }
    },
    EQUAL_TO("==") {

        @Override
        public boolean compare(int toCompare, int compareTo) {
            return toCompare == compareTo;
        }

        @Override
        public boolean compare(double toCompare, double compareTo) {
            return toCompare == compareTo;
        }

        @Override
        public boolean compare(float toCompare, float compareTo) {
            return toCompare == compareTo;
        }

        @Override
        public boolean compare(long toCompare, long compareTo) {
            return toCompare == compareTo;
        }

        @Override
        public boolean compare(int toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    int compareTo = jsonObject.get("compare_to").getAsInt();

                    return toCompare == compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(double toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    double compareTo = jsonObject.get("compare_to").getAsDouble();

                    return toCompare == compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(float toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    float compareTo = jsonObject.get("compare_to").getAsFloat();

                    return toCompare == compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(long toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    long compareTo = jsonObject.get("compare_to").getAsLong();

                    return toCompare == compareTo;
                }
            }
            return false;
        }
    },
    NOT_EQUAL_TO("!=") {

        @Override
        public boolean compare(int toCompare, int compareTo) {
            return toCompare != compareTo;
        }

        @Override
        public boolean compare(double toCompare, double compareTo) {
            return toCompare != compareTo;
        }

        @Override
        public boolean compare(float toCompare, float compareTo) {
            return toCompare != compareTo;
        }

        @Override
        public boolean compare(long toCompare, long compareTo) {
            return toCompare != compareTo;
        }

        @Override
        public boolean compare(int toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    int compareTo = jsonObject.get("compare_to").getAsInt();

                    return toCompare != compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(double toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    double compareTo = jsonObject.get("compare_to").getAsDouble();

                    return toCompare != compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(float toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    float compareTo = jsonObject.get("compare_to").getAsFloat();

                    return toCompare != compareTo;
                }
            }
            return false;
        }

        @Override
        public boolean compare(long toCompare, JsonObject jsonObject) {
            if (jsonObject != null) {
                if (jsonObject.has("compare_to")) {
                    long compareTo = jsonObject.get("compare_to").getAsLong();

                    return toCompare != compareTo;
                }
            }
            return false;
        }
    };

    private final String comparison;

    Comparison(String comparison) {
        this.comparison = comparison;
    }

    public static Comparison parseComparison(String comparison) {
        for (Comparison co : values()) {
            if (co.comparison.equals(comparison)) {
                return co;
            }
        }
        throw new NoSuchElementException(String.format("Unknown comparison [%s]", comparison));
    }

    public static Comparison parseComparison(JsonObject jsonObject) {
        if (jsonObject != null) {
            if (jsonObject.has("comparison")) {
                String comparison = jsonObject.get("comparison").getAsString();

                if (comparison != null) {
                    for (Comparison co : values()) {
                        if (co.comparison.equals(comparison)) {
                            return co;
                        }
                    }
                    throw new NoSuchElementException(String.format("Unknown comparison [%s]", comparison));
                }
            }
        }
        return EQUAL_TO;
    }

    public abstract boolean compare(int toCompare, int compareTo);

    public abstract boolean compare(double toCompare, double compareTo);

    public abstract boolean compare(float toCompare, float compareTo);

    public abstract boolean compare(long toCompare, long compareTo);

    public abstract boolean compare(int toCompare, JsonObject compareTo);

    public abstract boolean compare(double toCompare, JsonObject compareTo);

    public abstract boolean compare(float toCompare, JsonObject compareTo);

    public abstract boolean compare(long toCompare, JsonObject compareTo);
}
