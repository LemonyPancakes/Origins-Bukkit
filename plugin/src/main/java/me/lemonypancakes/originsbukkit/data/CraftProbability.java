package me.lemonypancakes.originsbukkit.data;

import me.lemonypancakes.originsbukkit.Probability;

import java.util.Objects;
import java.util.Random;

public class CraftProbability implements Probability {

    private int min;
    private int max;

    private Integer previousValue;

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public Integer getPreviousValue() {
        return previousValue;
    }

    @Override
    public boolean generateProbability() {
        Random random = new Random();
        int next = random.nextInt(max) + 1;
        this.previousValue = next;
        return next <= min + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CraftProbability)) return false;
        CraftProbability that = (CraftProbability) o;
        return getMin() == that.getMin() && getMax() == that.getMax() && Objects.equals(getPreviousValue(), that.getPreviousValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMin(), getMax(), getPreviousValue());
    }

    @Override
    public String toString() {
        return "ProbabilityContainer{" +
                "min=" + min +
                ", max=" + max +
                ", previousValue=" + previousValue +
                '}';
    }
}
