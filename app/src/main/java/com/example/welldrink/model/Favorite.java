package com.example.welldrink.model;

import androidx.annotation.NonNull;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Favorite)) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(getName(), favorite.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @NonNull
    @Override
    public String toString() {
        return "Favorite{" +
                "drink=" + drink +
                ", name='" + name + '\'' +
                '}';
    }
}
