package me.lemonypancakes.originsbukkit;

public interface Probability {

    int getMin();

    void setMin(int min);

    int getMax();

    void setMax(int max);

    Integer getPreviousValue();

    boolean generateProbability();
}
