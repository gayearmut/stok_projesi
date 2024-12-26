package com.stok.stokTakipProjesi.repository;

import com.stok.stokTakipProjesi.model.Urun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
/*
public interface UrunRepository  extends CrudRepository<Urun,Long> {
}
*/

public interface UrunRepository extends JpaRepository<Urun, Long> {
    List<Urun> findAllByOrderByIdAsc();
}