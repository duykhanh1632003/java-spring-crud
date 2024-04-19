package com.example.jdk15.models;


public class UserDo {
    private String name;

    private String address;

    private String type;

    private String phoneNumber;

    // Constructors
    public UserDo() {
    }

    public UserDo(String name, String address, String type, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
