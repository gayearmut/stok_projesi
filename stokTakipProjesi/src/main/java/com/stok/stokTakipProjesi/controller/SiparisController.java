package com.stok.stokTakipProjesi.controller;

import com.stok.stokTakipProjesi.model.Siparis;
import com.stok.stokTakipProjesi.model.SiparisDurumu;
import com.stok.stokTakipProjesi.model.Urun;
import com.stok.stokTakipProjesi.service.SiparisService;
import com.stok.stokTakipProjesi.service.UrunService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/siparis")
public class SiparisController {

    private final SiparisService siparisService;
    private final UrunService urunService;

    @Autowired
    public SiparisController(SiparisService siparisService, UrunService urunService) {
        this.siparisService = siparisService;
        this.urunService = urunService;
    }

    @GetMapping("")
    public String siparis(Model model) {
        List<Siparis> siparisList = siparisService.getAllSiparis();
        model.addAttribute("siparisList", siparisList);
        return "siparis";//Siparişleri listelediğimiz sayfa
    }

    @GetMapping("/siparisOlustur")
    public String siparisOlustur(Model model) {
        Siparis siparis = new Siparis();
        List<Urun> urunList = urunService.getAllUrun(); //urunleri getirir
        model.addAttribute("urunler",urunList);
        model.addAttribute("siparis", siparis);
        return "siparisOlustur";
    }

    @PostMapping("/siparis/save")
    public String save(@Valid @ModelAttribute("siparis") Siparis siparis,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       Model model) {
        // Eğer validasyon hatası varsa form tekrar gösterilir
        if (bindingResult.hasErrors()) {
            List<Urun> urunList = urunService.getAllUrun();
            model.addAttribute("urunler", urunList);
            return "siparisOlustur";
        }
        Urun urun = urunService.getUrunById(siparis.getUrun().getId());
        if (urun == null) {
            redirectAttributes.addFlashAttribute("error", "Seçilen ürün bulunamadı!");
            return "redirect:/siparis/siparisOlustur";
        }

        int mevcutAdet = urun.getAdet();
        int siparisMiktari = siparis.getMiktar();

        //negatif miktar kontrol
        if (siparisMiktari <= 0) {
            redirectAttributes.addFlashAttribute("error", "Sipariş miktarı sıfırdan büyük olmalıdır!");
            return "redirect:/siparis/siparisOlustur";
        }
        // stok yeter mi
        if (siparisMiktari > mevcutAdet) {
            redirectAttributes.addFlashAttribute("error", "Yeterli stok yok!");
            return "redirect:/siparis/siparisOlustur";
        }

        siparisService.save(siparis);
        redirectAttributes.addFlashAttribute("message", "Sipariş başarıyla oluşturuldu!");
        return "redirect:/siparis";
    }

    @GetMapping("/onayla/{id}")
    public String onaylaSiparis(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Siparis> siparisOpt = siparisService.getSiparisById(id);
        if (siparisOpt.isPresent()) {
            Siparis siparis = siparisOpt.get();
            Urun urun = siparis.getUrun();

            // Stok kontrolü
            if (urun.getAdet() >= siparis.getMiktar()) {
                // Sipariş durumunu güncelle
                siparis.setSiparisDurumu(SiparisDurumu.ONAYLANDI);
                siparisService.updateSiparis(siparis);

                // Stoktan düşür
                urun.setAdet(urun.getAdet() - siparis.getMiktar());
                urunService.save(urun);

                redirectAttributes.addFlashAttribute("message", "Sipariş onaylandı ve stok güncellendi!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Yeterli stok yok, sipariş onaylanamadı!");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Sipariş bulunamadı!");
        }
        return "redirect:/siparis";
    }

    @GetMapping("/reddet/{id}")
    public String reddetSiparis(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Siparis> siparisOpt = siparisService.getSiparisById(id);
        if (siparisOpt.isPresent()) {
            Siparis siparis = siparisOpt.get();

            // Sipariş durumunu güncelle
            siparis.setSiparisDurumu(SiparisDurumu.REDDEDILDI);
            siparisService.updateSiparis(siparis);

            redirectAttributes.addFlashAttribute("message", "Sipariş reddedildi!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Sipariş bulunamadı!");
        }
        return "redirect:/siparis";
    }

    @GetMapping("/iptal/{id}")
    public String iptalSiparis(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Siparis> siparisOpt = siparisService.getSiparisById(id);
        if (siparisOpt.isPresent()) {
            Siparis siparis = siparisOpt.get();
            Urun urun = siparis.getUrun();

            // Sipariş durumunu güncelle
            siparis.setSiparisDurumu(SiparisDurumu.IPTAL_EDILDI);
            siparisService.updateSiparis(siparis);

            // Stoktan düşülen miktarı geri ekle
            urun.setAdet(urun.getAdet() + siparis.getMiktar());
            urunService.save(urun);

            redirectAttributes.addFlashAttribute("message", "Sipariş iptal edildi ve stok güncellendi!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Sipariş bulunamadı!");
        }
        return "redirect:/siparis";
    }

    // Filtreleme işlemi için yeni metod
    @GetMapping("/filtrele")
    public String filtreleSiparis(@RequestParam(name = "durum", required = false) SiparisDurumu durum, Model model) {
        List<Siparis> siparisList;
        if (durum != null) {
            siparisList = siparisService.findBySiparisDurumu(durum);
        } else {
            siparisList = siparisService.getAllSiparis();
        }
        model.addAttribute("siparisList", siparisList);
        return "siparis";
    }
    @GetMapping("/cokSatanlar")
    public String getTopSellingProducts(Model model) {
        List<Object[]> topSellingProducts = siparisService.getTopSellingProducts();
        model.addAttribute("topSellingProducts", topSellingProducts);
        return "enCokSatanListesi";
    }
}
