package me.lemonypancakes.originsbukkit.util;

import net.objecthunter.exp4j.ExpressionBuilder;

public class MathUtils {

    public static double evaluate(String mathExpression) {
        return new ExpressionBuilder(mathExpression).build().evaluate();
    }
}
