package com.stok.stokTakipProjesi.repository;

import com.stok.stokTakipProjesi.model.Depo;
import com.stok.stokTakipProjesi.model.StokHareket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StokHareketRepository extends JpaRepository<StokHareket ,Long> {
    List<StokHareket> findAllByOrderByIdAsc();
    @Query("SELECT sh.urun, SUM(sh.miktar) AS toplamMiktar " +
            "FROM StokHareket sh " +
            "WHERE sh.hareketTipi = 'çıkış' " +
            "GROUP BY sh.urun " +
            "ORDER BY toplamMiktar DESC")
    List<Object[]> findTopSellingProducts();

    List<StokHareket> findAllByUrunId(Long id);
}

