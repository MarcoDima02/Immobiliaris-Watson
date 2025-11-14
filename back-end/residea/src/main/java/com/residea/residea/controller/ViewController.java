package com.residea.residea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/utenti")  // URL che apri nel browser
    public String utentiPage() {
        return "utenti";     // Thymeleaf cercherà templates/utenti.html
    }
}