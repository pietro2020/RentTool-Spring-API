package br.com.rent.tools.api.model;

public enum Condition {

    NEW,
    EXCELLENT,
    GOOD,
    FAIR,
    POOR,
    DAMAGED;

    public boolean isUsable() {
        return this == EXCELLENT || this == GOOD || this == FAIR;
    }

}
