package com.stok.stokTakipProjesi.service;

import com.stok.stokTakipProjesi.model.Depo;
import com.stok.stokTakipProjesi.model.Siparis;
import com.stok.stokTakipProjesi.model.SiparisDurumu;
import com.stok.stokTakipProjesi.model.Urun;
import com.stok.stokTakipProjesi.repository.SiparisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiparisService {

    private final SiparisRepository siparisRepository;

    @Autowired
    public SiparisService(SiparisRepository siparisRepository) {
        this.siparisRepository = siparisRepository;
    }

    public Siparis save(Siparis siparis) {
        return siparisRepository.save(siparis);
    }
    public List<Siparis> getAllSiparis() {
        return siparisRepository.findAllByOrderByIdAsc();
    }
    public Optional<Siparis> getSiparisById(Long id) {
        return siparisRepository.findById(id);
    }

    public List<Siparis> getSiparisByDurum(SiparisDurumu siparisDurumu) {
        return siparisRepository.findBySiparisDurumu(siparisDurumu);
    }

    public Siparis updateSiparis(Siparis siparis) {
        return siparisRepository.save(siparis);
    }

    // Siparişi silme
    public void deleteSiparis(Long id) {
        siparisRepository.deleteById(id);
    }


    public List<Siparis> findBySiparisDurumu(SiparisDurumu durum) {
        return siparisRepository.findBySiparisDurumu(durum);
    }

    public List<Object[]> getTopSellingProducts() {
        return siparisRepository.findTopSellingProducts(SiparisDurumu.ONAYLANDI);
    }

}

