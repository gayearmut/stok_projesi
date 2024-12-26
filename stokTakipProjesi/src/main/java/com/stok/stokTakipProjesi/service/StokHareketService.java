package com.stok.stokTakipProjesi.service;

import com.stok.stokTakipProjesi.model.Depo;
import com.stok.stokTakipProjesi.model.StokHareket;
import com.stok.stokTakipProjesi.repository.StokHareketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StokHareketService {
    private final StokHareketRepository stokHareketRepository;

    public StokHareketService(StokHareketRepository stokHareketRepository) {
        this.stokHareketRepository = stokHareketRepository;
    }

    public List<StokHareket> getAllStokHareket() {
        return (List<StokHareket>) stokHareketRepository.findAllByOrderByIdAsc();
    }

    public StokHareket save(StokHareket stokHareket ) {
        return stokHareketRepository.save(stokHareket);
    }

    public Optional<StokHareket> findById(Long id){
        return stokHareketRepository.findById(id);
    }
    public void deleteById(Long id) {
        stokHareketRepository.deleteById(id);
    }

    public List<Object[]> getTopSellingProducts() {
        return stokHareketRepository.findTopSellingProducts();
    }
}
