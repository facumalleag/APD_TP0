package com.example.myapplication.model;

public class Paso {

    private int stepOrder;
    private String description;

    public Paso (int orden, String description) {
        this.stepOrder = orden;
        this.description = description;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
