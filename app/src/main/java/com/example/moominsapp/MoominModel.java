package com.example.moominsapp;

public class MoominModel {

    private String name;
    private String desc;
    private int image;
    private int id;

    // Constructor
    public MoominModel(String name, String desc, int image, int id) {
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.id = id;
    }

    // Getters
    public String getMoominName() {
        return name;
    }

    public String getMoominDesc() {
        return desc;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    // Setters
    public void setMoominName(String name) {
        this.name = name;
    }

    public void setMoominDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Optional: Override toString for debugging
    @Override
    public String toString() {
        return "MoominModel{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", image=" + image +
                ", id=" + id +
                '}';
    }
}
