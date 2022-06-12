package me.lemonypancakes.originsbukkit.util;

import java.util.Objects;

public class Pair<T1, T2> {

    private T1 left;
    private T2 right;

    public Pair(T1 left, T2 right) {
        this.left = left;
        this.right = right;
    }

    public T1 getLeft() {
        return left;
    }

    public void setLeft(T1 t1) {
        this.left = t1;
    }

    public T2 getRight() {
        return right;
    }

    public void setRight(T2 t2) {
        this.right = t2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(getLeft(), pair.getLeft()) && Objects.equals(getRight(), pair.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeft(), getRight());
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
