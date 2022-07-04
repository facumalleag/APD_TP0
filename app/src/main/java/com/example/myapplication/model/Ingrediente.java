package com.example.myapplication.model;

public class Ingrediente {

    private int id;
    private String nombre;
    private Integer cantidad;
    private String medida;

    public Ingrediente(int id, String nombre, Integer cantidad, String medida) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.medida = medida;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
