package it.gruppoaton.PayslipMicroservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/prova")
    public String showHome(){
        return "prova";
    }

}
