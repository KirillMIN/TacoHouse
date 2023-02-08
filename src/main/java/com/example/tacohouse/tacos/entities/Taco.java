package com.example.tacohouse.tacos.entities;

import com.example.tacohouse.tacos.entities.Ingredient;
import lombok.Data;

import java.util.List;

@Data
public class Taco {
    private String name;
    private List<Ingredient> ingredients;
}
