package com.example.carpartsservice.model;

import javax.persistence.*;

@Entity
public class Part {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String name;
    private  String description;

    @Column(unique=true)
    private  String eanNumber;

    private double price;

    @ManyToOne
    private  Category Category;

    public Part() {

    }

    public Part(String name, String description, String eanNumber, double price, Category category) {
        this.name = name;
        this.description = description;
        this.eanNumber = eanNumber;
        this.price = price;
        Category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEanNumber() {
        return eanNumber;
    }

    public void setEanNumber(String eanNumber) {
        this.eanNumber = eanNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return Category;
    }

    public void setCategory(Category category) {
        Category = category;
    }
}
