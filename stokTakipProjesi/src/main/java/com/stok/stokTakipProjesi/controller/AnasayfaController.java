package com.stok.stokTakipProjesi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnasayfaController {

    @GetMapping("/anasayfa")
    public String anasayfa() {
        return "anasayfa";
    }
}

