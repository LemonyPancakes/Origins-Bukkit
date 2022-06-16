package me.lemonypancakes.originsbukkit.util;

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
