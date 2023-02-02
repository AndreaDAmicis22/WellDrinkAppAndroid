package com.example.welldrink;

public class Favorite {
    private final boolean drink;
    private final String name;

    public Favorite(boolean drink, String name) {
        this.drink = drink;
        this.name = name;
    }

    public boolean isDrink() {
        return drink;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "drink=" + drink +
                ", name='" + name + '\'' +
                '}';
    }
}
