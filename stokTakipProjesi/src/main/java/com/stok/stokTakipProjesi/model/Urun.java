package com.stok.stokTakipProjesi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

@Entity(name ="Urun")
@Table(name ="urun_tablosu",
        indexes = {
        @Index(name = "idx_depo_id", columnList = "depo_id", unique = true)
})
//@Table(name = "urun_tablosu")
public class Urun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Ürün adı girilmesi zorunludur!")
    @Column(name = "ad")
    private String ad;

    @NotEmpty(message = "Ürün markası girilmesi zorunludur!")
    @Column(name = "marka")
    private String marka;

    @Column(name = "adet")
    private int adet;

    @Column(name = "tarih")
    private LocalDate tarih = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depo_id", referencedColumnName = "id")
     private Depo depo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public Depo getDepo() {
        return depo;
    }

    public void setDepo(Depo depo) {
        this.depo = depo;
    }


}
