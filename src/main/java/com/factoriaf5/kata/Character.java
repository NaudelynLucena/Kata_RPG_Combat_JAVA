package com.factoriaf5.kata;

import java.util.HashSet;
import java.util.Set;

public class Character {
    private int health;
    int level;
    private boolean alive;
    private int attackRange;
    private Set<Faction> factions;

    public Character() {
        this.health = 1000;
        this.level = 1;
        this.alive = true;
        this.attackRange = 2;
        this.factions = new HashSet<>();
    }

    public int getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public Set<Faction> getFactions() {
        return factions;
    }
    public void receiveDamage(int amount) {
        if (amount < 0) return;
        this.health = Math.max(0, this.health - amount);
        if (this.health == 0) {
            this.alive = false;
        }
    }

    public void heal(Character target) {
        if (this.alive && target.alive) {
            target.health = Math.min(1000, target.health + 100);
        }
    }

    public void inflictDamage(Character target, int amount) {
        if (this.alive && target != this && !this.isAlly(target)) {
            int damage = calculateDamage(target, amount);
            target.receiveDamage(damage);
        }
    }

    int calculateDamage(Character target, int amount) {
        int levelDifference = this.level - target.getLevel();
        double damageFactor = 1;

        if (levelDifference >= 5) {
            damageFactor = 1.5;
        } else if (levelDifference <= -5) {
            damageFactor = 0.5;
        }
        return (int) (amount * damageFactor);
    }

    public boolean isAlly(Character target) {
        for (Faction faction : this.factions) {
            if (target.getFactions().contains(faction)) {
                return true;
            }
        }
        return false;
    }

    public void joinFaction(Faction faction) {
        this.factions.add(faction);
    }

    public void leaveFaction(Faction faction) {
        this.factions.remove(faction);
    }

    public void setAttackRange(int range) {
        this.attackRange = range;
    }
}
