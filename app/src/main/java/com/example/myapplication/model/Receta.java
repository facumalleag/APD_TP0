package com.example.myapplication.model;

import java.util.Date;
import java.util.List;

public class Receta {

    private int id;
    private int idUser;
    private String name;
    private String description;
    private int serving;
    private int servingPerPerson;
    private int time;
    private int idStatus;
    private int idDifficulty;
    private int idCategory;
    private String isVegan;
    private int totalSteps;
    private String totalRating;
    private Date createdAt;
    private Date updatedAt;
    private List<Ingrediente> ingredients;
    private List<Paso> steps;

    public Receta(int id, int idUser, String name, String description, int serving, int servingPerPerson, int time,
                  int idStatus, int idDifficulty, int idCategory, String isVegan, int totalSteps, String totalRating,
                  Date createdAt, Date updatedAt, List<Ingrediente> ingredients, List<Paso> steps) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.description = description;
        this.serving = serving;
        this.servingPerPerson = servingPerPerson;
        this.time = time;
        this.idStatus = idStatus;
        this.idDifficulty = idDifficulty;
        this.idCategory = idCategory;
        this.isVegan = isVegan;
        this.totalSteps = totalSteps;
        this.totalRating = totalRating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }

    public int getServingPerPerson() {
        return servingPerPerson;
    }

    public void setServingPerPerson(int servingPerPerson) {
        this.servingPerPerson = servingPerPerson;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public int getIdDifficulty() {
        return idDifficulty;
    }

    public void setIdDifficulty(int idDifficulty) {
        this.idDifficulty = idDifficulty;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getIsVegan() {
        return isVegan;
    }

    public void setIsVegan(String isVegan) {
        this.isVegan = isVegan;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Ingrediente> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingrediente> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Paso> getSteps() {
        return steps;
    }

    public void setSteps(List<Paso> steps) {
        this.steps = steps;
    }

}
