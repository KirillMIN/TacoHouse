package com.example.tacohouse.tacos.Converters;


import com.example.tacohouse.tacos.Repositories.JdbcTemplate.IngredientRepository;
import com.example.tacohouse.tacos.entities.Ingredient;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepo;

    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }
    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(Long.valueOf(id));
    }
}
