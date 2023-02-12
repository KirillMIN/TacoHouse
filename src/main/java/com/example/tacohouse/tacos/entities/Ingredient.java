package com.example.tacohouse.tacos.entities;
import lombok.*;


import java.util.List;




public class Ingredient  {

    public Ingredient(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }


    private  Long id;


    private  String name;


    private  Type type;


   // @ManyToMany
   // private List<Taco> tacos;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }




}