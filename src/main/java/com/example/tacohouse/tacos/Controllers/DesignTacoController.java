package com.example.tacohouse.tacos.Controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import com.example.tacohouse.tacos.Repositories.JdbcTemplate.IngredientRepository;
import com.example.tacohouse.tacos.entities.Ingredient;
import com.example.tacohouse.tacos.entities.Taco;
import com.example.tacohouse.tacos.entities.TacoOrder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
//Обработка запросов с путем /design
@RequestMapping("/design")
//Создание объекта поддерживается на впротяжении всей сессии
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private final IngredientRepository ingredientRepo;
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository){
        this.ingredientRepo = ingredientRepository;
    }
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType((List<Ingredient>) ingredients, type));
        }
    }
    //Добавление новых объектов в модель
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }


    @PostMapping
    public String processTaco(Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        if(errors.hasErrors()){
            System.out.println(errors);
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco:" + taco.getId() + " "  + taco.getName() + " " + taco.getIngredients().isEmpty());
        return "redirect:/orders/current";
    }


}
