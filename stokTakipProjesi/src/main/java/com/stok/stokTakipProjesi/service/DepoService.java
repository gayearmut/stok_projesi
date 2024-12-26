package com.stok.stokTakipProjesi.service;

import com.stok.stokTakipProjesi.model.Depo;
import com.stok.stokTakipProjesi.model.Urun;
import com.stok.stokTakipProjesi.repository.DepoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepoService {

    private final DepoRepository depoRepository;

    public DepoService(DepoRepository depoRepository) {
        this.depoRepository = depoRepository;
    }

    public List<Depo> getAllDepo() {
        return (List<Depo>) depoRepository.findAllByOrderByIdAsc();
    }

    public Depo save(Depo depo) {
        return depoRepository.save(depo);
    }
    public Optional<Depo> findById(Long id){
        return depoRepository.findById(id);
    }
    public void deleteById(Long id) {
        depoRepository.deleteById(id);
    }
}
