package com.stok.stokTakipProjesi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "StokHareket")
@Table(name = "stok_hareket_tablosu")
public class StokHareket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "urun_id", referencedColumnName = "id")
    private Urun urun;

    @Column(name = "miktar")
    private int miktar;

    @Column(name = "hareket_tipi")
    private String hareketTipi; //giriş/çıkış

    @Column(name = "islem_tarihi")
    private LocalDateTime islemTarihi = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Urun getUrun() {
        return urun;
    }

    public void setUrun(Urun urun) {
        this.urun = urun;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public String getHareketTipi() {
        return hareketTipi;
    }

    public void setHareketTipi(String hareketTipi) {
        this.hareketTipi = hareketTipi;
    }

    public LocalDateTime getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(LocalDateTime islemTarihi) {
        this.islemTarihi = islemTarihi;
    }
}
