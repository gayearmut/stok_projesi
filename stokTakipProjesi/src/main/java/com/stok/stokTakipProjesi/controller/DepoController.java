package com.stok.stokTakipProjesi.controller;

import com.stok.stokTakipProjesi.model.Depo;
import com.stok.stokTakipProjesi.service.DepoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/depo") //sınıf veya metoda url eşlemesi yapar
public class DepoController {

    private final DepoService depoService;

    public DepoController(DepoService depoService) {
        this.depoService = depoService;
    }

    @GetMapping("")
    public String depoHome(Model model) {
        List<Depo> depoList = depoService.getAllDepo();
        model.addAttribute("depoList", depoList);
        return "depo";
    }

    @GetMapping("/depoEkle")
    public String depoEkle(Model model) {
        Depo depo = new Depo();
        model.addAttribute("depo", depo);
        return "depoEkle";
    }

    @PostMapping("/depo/save")
    public String save(@Valid @ModelAttribute("depo") Depo depo,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "depoEkle";
        }

        depoService.save(depo);
        redirectAttributes.addFlashAttribute("message", "Depo başarıyla eklendi!");
        return "redirect:/depo";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        Depo depo = depoService.findById(id).orElse(null);
        model.addAttribute("depo", depo);
        return "editDepo";
    }
/*
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        depoService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Depo başarıyla silindi!");
        return "redirect:/depo";
    }*/


    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        if (depoService.hasBagliUrun(id)) {
            redirectAttributes.addFlashAttribute("error", "Bu depoyu silemezsiniz. Bu depoya bağlı ürünler bulunmaktadır.");
            return "redirect:/depo";
        }
        depoService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Depo başarıyla silindi!");
        return "redirect:/depo";
    }


}


