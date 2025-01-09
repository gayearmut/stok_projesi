package com.stok.stokTakipProjesi.controller;

import com.stok.stokTakipProjesi.model.Depo;
import com.stok.stokTakipProjesi.model.Urun;
import com.stok.stokTakipProjesi.service.DepoService;
import com.stok.stokTakipProjesi.service.UrunService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {


    private final UrunService urunService;
    private final DepoService depoService;

    public HomeController(UrunService urunService, DepoService depoService) {
        this.urunService = urunService;
        this.depoService = depoService;
    }


    @GetMapping("/")
    public String home(@RequestParam(value = "name",defaultValue = "")String name,
                        Model model){
        List<Urun> urunList = urunService.getAllUrun();//ürünleri getirir
        model.addAttribute("urunList",urunList);
        return "home";
    }

    @GetMapping("/Ekle")
    public String Ekle(Model model){
        Urun urun = new Urun();
        List<Depo> depoList = depoService.getAllDepo(); //depoları getirir
        model.addAttribute("depolar",depoList);
        model.addAttribute("urun",urun);
        return "Ekle";
    }
    //yeni ürün eklemek veya ürünü güncellemek
    @PostMapping("/save")//modelAttribute: formdan gelen veriyi nesneye dönüştürür,nesneyi model ile bağlar
    public String save(@Valid @ModelAttribute("urun") Urun urun,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model){
        if (bindingResult.hasErrors()){
            return "/Ekle";
        }

        urunService.save(urun);
        redirectAttributes.addFlashAttribute(
                "message",
                "Ürün başarıyla eklendi!"
        );
        return "redirect:/";
    }

    @GetMapping("urun/{id}/edit")//id bazlı güncelleme işlemi
    public String edit(@PathVariable long id, Model model){
        Urun urun = urunService.findById(id).orElse(null);
        List<Depo> depoList = depoService.getAllDepo();
        model.addAttribute("depolar",depoList);
        model.addAttribute("urun",urun);
        return "/Guncelle";
    }

    @GetMapping("urun/{id}")
    public String show(@PathVariable long id, Model model){
        urunService.findById(id)
                .ifPresent(urun -> model.addAttribute("urun",urun));
        return "show";
    }

    @GetMapping("urun/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes){
        urunService.deleteById(id);
        redirectAttributes.addFlashAttribute("message","Ürün başarıyla silindi!");
        return "redirect:/";
    }
}
