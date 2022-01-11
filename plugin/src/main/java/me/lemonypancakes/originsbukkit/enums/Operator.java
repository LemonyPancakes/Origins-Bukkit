package me.lemonypancakes.originsbukkit.enums;

import java.util.NoSuchElementException;

public enum Operator {

    LESS_THAN("<") {
        @Override public boolean apply(double left, double right) {
            return left < right;
        }
    },
    GREATER_THAN(">") {
        @Override public boolean apply(double left, double right) {
            return left > right;
        }
    },
    LESS_THAN_OR_EQUAL_TO("<=") {
        @Override public boolean apply(double left, double right) {
            return left <= right;
        }
    },
    GREATER_THAN_OR_EQUAL_TO(">=") {
        @Override public boolean apply(double left, double right) {
            return left >= right;
        }
    },
    EQUAL_TO("==") {
        @Override public boolean apply(double left, double right) {
            return left == right;
        }
    };

    private final String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    public static Operator parseOperator(String operator) {
        for (Operator op : values()) {
            if (op.operator.equals(operator)) {
                return op;
            }
        }
        throw new NoSuchElementException(String.format("Unknown operator [%s]", operator));
    }

    public abstract boolean apply(double left, double right);
}
