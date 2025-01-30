package com.stok.stokTakipProjesi.repository;

import com.stok.stokTakipProjesi.model.Depo;
import com.stok.stokTakipProjesi.model.Siparis;
import com.stok.stokTakipProjesi.model.SiparisDurumu;
import com.stok.stokTakipProjesi.model.Urun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface SiparisRepository extends JpaRepository<Siparis, Long> {
    List<Siparis> findAllByOrderByIdAsc();
    List<Siparis> findBySiparisDurumu(SiparisDurumu siparisDurumu);
    List<Siparis> findByUrun(Urun urun);

    @Query("SELECT s.urun, SUM(s.miktar) as toplamSatis " +
            "FROM Siparis s " +
            "WHERE s.siparisDurumu = :durum " +
            "GROUP BY s.urun " +
            "ORDER BY toplamSatis DESC")
    List<Object[]> findTopSellingProducts(@Param("durum") SiparisDurumu durum);


}
