package com.stok.stokTakipProjesi.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Siparis")
@Table(name = "siparis_tablosu")
public class Siparis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "urun_id", referencedColumnName = "id")
    private Urun urun;

    @Column(name = "kullanici_adi")
    private String kullaniciAdi;

    @Column(name = "kullanici_soyadi")
    private String kullaniciSoyadi;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "siparis_durumu")
    private SiparisDurumu siparisDurumu;

    @Column(name = "siparis_tarihi")
    private LocalDate siparisTarihi = LocalDate.now();

    @Column(name = "miktar")
    private int miktar;

    @PrePersist
    public void prePersist() {
        if (siparisDurumu == null) {
            this.siparisDurumu = SiparisDurumu.OLUSTURULDU;
        }
    }

    // Getters and Setters
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

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getKullaniciSoyadi() {
        return kullaniciSoyadi;
    }

    public void setKullaniciSoyadi(String kullaniciSoyadi) {
        this.kullaniciSoyadi = kullaniciSoyadi;
    }

    public SiparisDurumu getSiparisDurumu() {
        return siparisDurumu;
    }

    public void setSiparisDurumu(SiparisDurumu siparisDurumu) {
        this.siparisDurumu = siparisDurumu;
    }

    public LocalDate getSiparisTarihi() {
        return siparisTarihi;
    }

    public void setSiparisTarihi(LocalDate siparisTarihi) {
        this.siparisTarihi = siparisTarihi;
    }
    public int getMiktar() {
        return miktar;
    }
    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }
}

