package com.example.welldrink;

import java.util.ArrayList;

public class Drink {
    private final int id;
    private final String title;
    private final String description;
    private final String glass;
    private final String instruction;
    private final String instructionES;
    private final String instructionDE;
    private final String instructionFR;
    private final String instructionIT;
    private final String instructionZH;
    private final String img;
    private final ArrayList<String> ingredients;
    private final ArrayList<String> measures;

    public Drink(int id, String title, String description, String glass, String instruction,
                 String instructionES, String instructionDE, String instructionFR, String instructionIT,
                 String instructionZH, String img, ArrayList<String> ingredients, ArrayList<String> measures) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.glass = glass;
        this.instruction = instruction;
        this.instructionES = instructionES;
        this.instructionDE = instructionDE;
        this.instructionFR = instructionFR;
        this.instructionIT = instructionIT;
        this.instructionZH = instructionZH;
        this.img = img;
        this.ingredients = ingredients;
        this.measures = measures;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGlass() {
        return glass;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getInstructionES() {
        return instructionES;
    }

    public String getInstructionDE() {
        return instructionDE;
    }

    public String getInstructionFR() {
        return instructionFR;
    }

    public String getInstructionIT() {
        return instructionIT;
    }

    public String getInstructionZH() {
        return instructionZH;
    }

    public String getImg() {
        return img;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getMeasures() {
        return measures;
    }
}
