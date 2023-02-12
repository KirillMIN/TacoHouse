package com.example.tacohouse.tacos.Repositories.JdbcTemplate;

import com.example.tacohouse.tacos.entities.Ingredient;

import java.util.Optional;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findById(Long id);

    Ingredient save(Ingredient ingredient);


}
