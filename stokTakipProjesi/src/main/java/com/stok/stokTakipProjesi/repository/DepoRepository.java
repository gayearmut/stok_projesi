package com.stok.stokTakipProjesi.repository;

import com.stok.stokTakipProjesi.model.Depo;
import com.stok.stokTakipProjesi.model.Urun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepoRepository extends JpaRepository<Depo, Long> {
    List<Depo> findAllByOrderByIdAsc();
}


