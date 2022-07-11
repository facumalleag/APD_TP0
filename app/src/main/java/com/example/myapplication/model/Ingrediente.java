package com.example.myapplication.model;

public class Ingrediente {

    private int id;
    private String description;
    private Integer cantidad;
    private String medida;
    private int idMeasurement;

    public Ingrediente(int id, String description, Integer cantidad, String medida, int idMeasurement) {
        this.id = id;
        this.description = description;
        this.cantidad = cantidad;
        this.medida = medida;
        this.idMeasurement = idMeasurement;
    }

    public String getNombre() {
        return description;
    }

    public void setNombre(String nombre) {
        this.description = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdMeasurement() {
        return idMeasurement;
    }

    public void setIdMeasurement(int idMeasurement) {
        this.idMeasurement = idMeasurement;
    }
}
