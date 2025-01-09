package com.stok.stokTakipProjesi.model;

        import jakarta.persistence.*;
        import jakarta.validation.constraints.NotEmpty;

@Entity(name = "Depo")
@Table(name = "depo_tablosu")
public class Depo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Depo adı girilmesi zorunludur!")
    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "konum")
    private String konum;

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

    public String getKonum() {
        return konum;
    }

    public void setKonum(String konum) {
        this.konum = konum;
    }
}
