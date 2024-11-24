package com.factoriaf5.kata;

import java.util.Objects;

public class Faction {
    private String name;

    // Constructor
    public Faction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Faction faction = (Faction) obj;
        return Objects.equals(name, faction.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
