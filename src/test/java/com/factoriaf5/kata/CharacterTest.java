package com.factoriaf5.kata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CharacterTest {

    private Character character;
    private Character target;
    private Faction faction;

    @BeforeEach
    public void setUp() {
        character = new Character();
        target = new Character();
        faction = new Faction(null);
    }

    @Test
    public void testInitialHealth() {
        assertThat(character.getHealth(), is(1000));
    }

    @Test
    public void testInitialLevel() {
        assertThat(character.getLevel(), is(1));
    }

    @Test
    public void testInitialAliveStatus() {
        assertThat(character.isAlive(), is(true));
    }

    @Test
    public void testInitialAttackRange() {
        assertThat(character.getAttackRange(), is(2));
    }

    @Test
    public void testInitialFactions() {
        assertThat(character.getFactions(), is(empty()));
    }

    @Test
    public void testReceiveDamage() {
        character.receiveDamage(500);
        assertThat(character.getHealth(), is(500));
        assertThat(character.isAlive(), is(true));

        character.receiveDamage(600);
        assertThat(character.getHealth(), is(0));
        assertThat(character.isAlive(), is(false));
    }

    @Test
    public void testReceiveNegativeDamage() {
        character.receiveDamage(-10);
        assertThat(character.getHealth(), is(1000));
        assertThat(character.isAlive(), is(true));
    }

    @Test
    public void testHealWhenBothAreAlive() {
        Character healer = new Character();
        Character target = new Character();
        target.receiveDamage(800);

        healer.heal(target);

        assertEquals(300, target.getHealth(), "El target debe ser curado correctamente.");
    }

    @Test
    public void testHealWhenHealerIsDead() {
        Character healer = new Character();
        Character target = new Character();
        healer.receiveDamage(1000);

        healer.heal(target);

        assertEquals(1000, target.getHealth(), "El target no debe ser curado si el healer está muerto.");
    }

    @Test
    public void testHealWhenTargetIsDead() {
        Character healer = new Character();
        Character target = new Character();
        target.receiveDamage(1000);

        healer.heal(target);

        assertEquals(0, target.getHealth(), "El target no debe ser curado si está muerto.");
    }

    @Test
    public void testHealDoesNotExceedMaxHealth() {
        Character healer = new Character();
        Character target = new Character();
        target.receiveDamage(900);

        healer.heal(target);

        assertEquals(200, target.getHealth(), "La salud del target no debe superar 1000.");
    }

    @Test
    public void testHealWhenTargetHasMaxHealth() {
        Character healer = new Character();
        Character target = new Character();
        target.receiveDamage(0);

        healer.heal(target);

        assertEquals(1000, target.getHealth(), "La salud del target no debe cambiar si ya está a 1000.");
    }

    @Test
    public void testInflictDamageWhenAttackerIsAliveAndNotAlly() {
        character.level = 10;
        target.level = 1;

        int initialHealth = target.getHealth();
        character.inflictDamage(target, 200);

        assertThat(target.getHealth(), is(initialHealth - 300));
    }

    @Test
    public void testInflictDamageWhenAttackerIsDead() {
        character.receiveDamage(1000);
        int initialHealth = target.getHealth();

        character.inflictDamage(target, 200);

        assertThat(target.getHealth(), is(initialHealth));
    }

    @Test
    public void testInflictDamageWhenTargetIsTheSameAsAttacker() {
        int initialHealth = character.getHealth();

        character.inflictDamage(character, 200);

        assertThat(character.getHealth(), is(initialHealth));
    }

    @Test
    public void testInflictDamageWhenAttackerAndTargetAreAllies() {
        character.joinFaction(faction);
        target.joinFaction(faction);

        int initialHealth = target.getHealth();
        character.inflictDamage(target, 200);

        assertThat(target.getHealth(), is(initialHealth));
    }

    @Test
    public void testCalculateDamageWhenLevelDifferenceIsGreaterThanFive() {
        character.level = 10;
        target.level = 1;

        int damage = character.calculateDamage(target, 200);

        assertThat(damage, is(300));
    }

    @Test
    public void testCalculateDamageWhenLevelDifferenceIsLessThanMinusFive() {
        character.level = 1;
        target.level = 10;

        int damage = character.calculateDamage(target, 200);

        assertThat(damage, is(100));
    }

    @Test
    public void testCalculateDamageWhenLevelDifferenceIsBetweenMinusFiveAndFive() {
        character.level = 5;
        target.level = 3;

        int damage = character.calculateDamage(target, 200);

        assertThat(damage, is(200));
    }

    @Test
    public void testCalculateDamageWhenLevelDifferenceIsExactlyFive() {
        character.level = 10;
        target.level = 5;

        int damage = character.calculateDamage(target, 200);

        assertThat(damage, is(300));
    }

    @Test
    public void testCalculateDamageWhenLevelDifferenceIsExactlyMinusFive() {
        character.level = 5;
        target.level = 10;

        int damage = character.calculateDamage(target, 200);

        assertThat(damage, is(100));
    }

    @Test
    public void testJoinFaction() {
        character.joinFaction(faction);
        assertThat(character.getFactions(), contains(faction));
    }

    @Test
    public void testLeaveFaction() {
        character.joinFaction(faction);
        character.leaveFaction(faction);
        assertThat(character.getFactions(), is(empty()));
    }

    @Test
public void testIsAllyWhenBothHaveNoFactions() {
    Character character1 = new Character();
    Character character2 = new Character();

    assertFalse(character1.isAlly(character2), "Dos personajes sin facciones no deben ser aliados.");
}

@Test
public void testIsAllyWhenCharacterHasFactionsButTargetHasNone() {
    Character character1 = new Character();
    Character character2 = new Character();
    Faction faction = new Faction("Faction1");
    character1.joinFaction(faction);

    assertFalse(character1.isAlly(character2), "El personaje con facciones no debe ser aliado de un personaje sin facciones.");
}

@Test
public void testIsAllyWhenCharacterHasNoFactionsButTargetHasFactions() {
    Character character1 = new Character();
    Character character2 = new Character();
    Faction faction = new Faction("Faction1");
    character2.joinFaction(faction);

    assertFalse(character1.isAlly(character2), "El personaje sin facciones no debe ser aliado de un personaje con facciones.");
}

@Test
public void testIsAllyWhenNeitherShareFactions() {
    Character character1 = new Character();
    Character character2 = new Character();
    Faction faction1 = new Faction("Faction1");
    Faction faction2 = new Faction("Faction2");
    character1.joinFaction(faction1);
    character2.joinFaction(faction2);

    assertFalse(character1.isAlly(character2), "Dos personajes con facciones distintas no deben ser aliados.");
}

@Test
public void testIsAllyWhenBothShareAFaction() {
    Character character1 = new Character();
    Character character2 = new Character();
    Faction faction = new Faction("Faction1");
    character1.joinFaction(faction);
    character2.joinFaction(faction);

    assertTrue(character1.isAlly(character2), "Dos personajes en la misma facción deben ser aliados.");
}

@Test
public void testIsAllyWhenOneCharacterHasMultipleFactions() {
    Character character1 = new Character();
    Character character2 = new Character();
    Faction faction1 = new Faction("Faction1");
    Faction faction2 = new Faction("Faction2");
    character1.joinFaction(faction1);
    character1.joinFaction(faction2);
    character2.joinFaction(faction1);

    assertTrue(character1.isAlly(character2), "El personaje con múltiples facciones debe ser aliado si comparte al menos una facción.");
}

    @Test
    public void testSetAttackRange() {
        character.setAttackRange(20);
        assertThat(character.getAttackRange(), is(20));
    }
}
