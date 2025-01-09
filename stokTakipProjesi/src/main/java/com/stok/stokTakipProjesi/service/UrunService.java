package com.stok.stokTakipProjesi.service;


import com.stok.stokTakipProjesi.model.Urun;
import com.stok.stokTakipProjesi.repository.UrunRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrunService {

    private final UrunRepository urunRepository;

    public UrunService(UrunRepository urunRepository) {
        this.urunRepository = urunRepository;
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
}
