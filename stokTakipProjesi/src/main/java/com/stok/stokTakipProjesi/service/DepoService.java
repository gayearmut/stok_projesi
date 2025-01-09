package com.stok.stokTakipProjesi.service;

import com.stok.stokTakipProjesi.model.Depo;
import com.stok.stokTakipProjesi.repository.DepoRepository;
import com.stok.stokTakipProjesi.repository.UrunRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepoService {

    private final DepoRepository depoRepository;
    private final UrunRepository urunRepository;

    public DepoService(DepoRepository depoRepository, UrunRepository urunRepository) {
        this.depoRepository = depoRepository;
        this.urunRepository = urunRepository;
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

    public boolean hasBagliUrun(Long id){
        return urunRepository.existsByDepoId(id);
    }



    }

