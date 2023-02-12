package com.example.tacohouse.tacos.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Taco {

    private Long id;

    private Date createdAt;

    //@NotNull
    //@Size(min=5, message="Name must be at least 5 characters long")
    private String name;

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    private List<Ingredient> ingredients = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setIngredients(List<Ingredient> ingredients) {

        this.ingredients = ingredients;
    }

    private  TacoOrder tacoOrder;



}