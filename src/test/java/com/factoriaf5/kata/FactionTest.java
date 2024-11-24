package com.factoriaf5.kata;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactionTest {

    @Test
    public void testFactionCreation() {

        Faction faction = new Faction("Red");

        assertEquals("Red", faction.getName());
    }

    @Test
    public void testEqualsAndHashCode() {

        Faction faction1 = new Faction("Red");
        Faction faction2 = new Faction("Red");

        assertEquals(faction1, faction2);

        assertEquals(faction1.hashCode(), faction2.hashCode());

        Faction faction3 = new Faction("Blue");

        assertNotEquals(faction1, faction3);
    }

    @Test
    public void testFactionWithNull() {

        Faction faction = new Faction("Red");

        assertNotEquals(faction, null);
    }

    @Test
    public void testFactionWithDifferentClass() {

        Faction faction = new Faction("Red");

        assertNotEquals(faction, new Object());
    }

    @Test
    public void testSameFactionInstance() {

        Faction faction1 = new Faction("Red");

        assertEquals(faction1, faction1);
    }
}
