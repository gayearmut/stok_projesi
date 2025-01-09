package com.stok.stokTakipProjesi.service;


import com.stok.stokTakipProjesi.model.Urun;
import com.stok.stokTakipProjesi.repository.UrunRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrunService {

    private final UrunRepository urunRepository;
    private final StokHareketService stokHareketService;

    public UrunService(UrunRepository urunRepository, StokHareketService stokHareketService) {
        this.urunRepository = urunRepository;
        this.stokHareketService = stokHareketService;
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


    public void deleteById(Long id){

        urunRepository.deleteById(id);
    }

    public void deleteUrunAndStokHareket(Long id) {
        stokHareketService.deleteAllByUrunId(id); // Önce ürüne bağlı stok hareketlerini sil
        urunRepository.deleteById(id); // ürünü sil
    }

}
