package com.ievgenrudenko.strategy;

public abstract class AbstractMatchingStrategy
        implements MatchingStrategy, Comparable<AbstractMatchingStrategy> {

    final private int priority;

    protected AbstractMatchingStrategy(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(AbstractMatchingStrategy other) {
        return Integer.compare(priority, other.priority);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "priority=" + priority + '}';
    }

}
