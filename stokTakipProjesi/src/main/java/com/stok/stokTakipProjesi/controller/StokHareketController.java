package com.stok.stokTakipProjesi.controller;

import com.stok.stokTakipProjesi.model.StokHareket;
import com.stok.stokTakipProjesi.model.Urun;
import com.stok.stokTakipProjesi.service.StokHareketService;
import com.stok.stokTakipProjesi.service.UrunService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/stokHareket")
public class StokHareketController {
    private final StokHareketService stokHareketService;
    private final UrunService urunService;

    public StokHareketController(StokHareketService stokHareketService, UrunService urunService) {
        this.stokHareketService = stokHareketService;
        this.urunService = urunService;
    }

    @PostMapping
    public String stokHareket(@RequestParam Long urunId,
                              @RequestParam int miktar,
                              @RequestParam String hareketTipi,
                              RedirectAttributes redirectAttributes) {
        Urun urun = urunService.findById(urunId).orElse(null);
        if (urun == null) {
            redirectAttributes.addFlashAttribute("error", "Ürün bulunamadı!");
            return "redirect:/";
        }
         if (miktar <= 0) {
            redirectAttributes.addFlashAttribute("error", "İşlem başarısız! Miktar sıfırdan büyük olmalıdır.Lütfen geçerli bir miktar giriniz.");
            return "redirect:/urun/" + urunId;
        }
        try {
            if ("giriş".equals(hareketTipi)) {
                urun.setAdet(urun.getAdet() + miktar);
            } else if ("çıkış".equals(hareketTipi)) {
                if (urun.getAdet() >= miktar) {
                    urun.setAdet(urun.getAdet() - miktar);
                } else {
                    redirectAttributes.addFlashAttribute("error", "Yeterli stok yok!");
                    return "redirect:/urun/" + urunId;
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Geçersiz hareket tipi!");
                return "redirect:/urun/" + urunId;
            }

            urunService.save(urun);

            StokHareket stokHareket = new StokHareket();
            stokHareket.setUrun(urun);
            stokHareket.setMiktar(miktar);
            stokHareket.setHareketTipi(hareketTipi);
            stokHareketService.save(stokHareket);

            redirectAttributes.addFlashAttribute("success", "Hareket başarıyla kaydedildi!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "İşlem sırasında bir hata oluştu: " + e.getMessage());
        }
        return "redirect:/urun/" + urunId;
    }

    @GetMapping("/cokSatanlar")
    public String getTopSellingProducts(Model model) {
        List<Object[]> topSellingProducts = stokHareketService.getTopSellingProducts();
        model.addAttribute("topSellingProducts", topSellingProducts);
        return "enCokSatanListesi";
    }
}
