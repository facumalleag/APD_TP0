package com.example.myapplication.model;

public class user {
    private String email;
    private String alias;
    private String dni;
    private String name;
    private String password;
    private Integer isActive;

    user(String email, String alias, String dni, String name, String password){
        this.email = email;
        this.alias = alias;
        this.dni = dni;
        this.name = name;
        this.password = password;
    }

    public String get_mail(){
        return email;
    }
    public String getDni(){
        return dni;
    }


}
