package com.vzurauskas.duelscripts2;

public class Weapon {
    private final String name;
    private final int damage;

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public int damage() {
        return damage;
    }
}
