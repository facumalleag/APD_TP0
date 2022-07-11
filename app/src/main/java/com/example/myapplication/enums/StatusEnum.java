package com.example.myapplication.enums;

public enum StatusEnum {

    IN_PROGRESS(1,"In Progress"),
    APPROVED(2, "Approved"),
    REJECTED(3, "Rejected"),
    CANCELLED(4, "Cancelled");

     int id;
    String description;


    StatusEnum(int id, String description) {
        this.id = id;
        this.description = description;
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
}
