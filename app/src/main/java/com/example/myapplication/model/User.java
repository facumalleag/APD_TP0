package com.example.myapplication.model;

public class User {
    private String email;
    private String alias;
    private String dni;
    private String name;
    private String password;
    private Integer isActive;

    public User(String email, String alias, String dni, String name, String password){
        super();
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
