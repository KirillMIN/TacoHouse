package com.example.tacohouse.tacos.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//создание компонента контролера
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
}
