package com.stok.stokTakipProjesi.repository;

import com.stok.stokTakipProjesi.model.Depo;
import com.stok.stokTakipProjesi.model.StokHareket;
import com.stok.stokTakipProjesi.model.Urun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
/*
public interface UrunRepository  extends CrudRepository<Urun,Long> {
}
*/

public interface UrunRepository extends JpaRepository<Urun, Long> {
    List<Urun> findAllByOrderByIdAsc();
    boolean existsByDepoId(Long id); // Depoya bağlı ürün var mı kontrol eder.

}