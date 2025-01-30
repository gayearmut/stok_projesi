package com.stok.stokTakipProjesi.service;


import com.stok.stokTakipProjesi.model.Siparis;
import com.stok.stokTakipProjesi.model.SiparisDurumu;
import com.stok.stokTakipProjesi.model.Urun;
import com.stok.stokTakipProjesi.repository.SiparisRepository;
import com.stok.stokTakipProjesi.repository.UrunRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrunService {

    private final UrunRepository urunRepository;
    private final StokHareketService stokHareketService;
    private final SiparisRepository siparisRepository;


    public UrunService(UrunRepository urunRepository, StokHareketService stokHareketService, SiparisRepository siparisRepository) {
        this.urunRepository = urunRepository;
        this.stokHareketService = stokHareketService;
        this.siparisRepository = siparisRepository;
    }




    public List<Urun> getAllUrun(){
         return (List<Urun>) urunRepository.findAllByOrderByIdAsc(); //ürünleri listele
    }
    public Optional<Urun> findById(Long id){
        return urunRepository.findById(id);//ürünleri getir
    }

    public Urun save(Urun urun)   {
        return urunRepository.save(urun);
    }
    public Urun getUrunById(Long id) {
        return urunRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        urunRepository.deleteById(id);
    }

    public void deleteUrunAndStokHareket(Long id) {
        stokHareketService.deleteAllByUrunId(id); // Önce ürüne bağlı stok hareketlerini sil
        urunRepository.deleteById(id); // ürünü sil
    }


    // Ürün silme işlemi
    public void deleteUrunAndCheckSiparis(Long id) throws Exception {
        Urun urun = urunRepository.findById(id)
                .orElseThrow(() -> new Exception("Ürün bulunamadı!"));

        // Bu ürünle ilişkili siparişleri kontrol et
        List<Siparis> siparisler = siparisRepository.findByUrun(urun);

        // Eğer aktif siparişler varsa, ürün silinemez
        boolean aktifSiparisVarMi = siparisler.stream()
                .anyMatch(siparis -> siparis.getSiparisDurumu() == SiparisDurumu.OLUSTURULDU ||
                        siparis.getSiparisDurumu() == SiparisDurumu.ONAYLANDI);

        if (aktifSiparisVarMi) {
            throw new Exception("Bu ürünle ilişkili aktif siparişler bulunuyor. Ürün silinemez!");
        }

        // Eğer iptal edilmiş veya reddedilmiş siparişler varsa, önce bu siparişleri sil
        List<Siparis> iptalEdilmisSiparisler = siparisler.stream()
                .filter(siparis -> siparis.getSiparisDurumu() == SiparisDurumu.IPTAL_EDILDI ||
                        siparis.getSiparisDurumu() == SiparisDurumu.REDDEDILDI)
                .toList();

        if (!iptalEdilmisSiparisler.isEmpty()) {
            siparisRepository.deleteAll(iptalEdilmisSiparisler);
        }

        urunRepository.delete(urun);
    }
}
