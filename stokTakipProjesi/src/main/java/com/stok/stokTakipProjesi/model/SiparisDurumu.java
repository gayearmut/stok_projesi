
/*
public enum SiparisDurumu {
    OLUSTURULDU(), // Sipariş oluşturuldu, stok hareketi yapılmaz
    ONAYLANDI,   // Sipariş onaylandı, stoktan çıkış yapılır
    IPTAL_EDILDI  // Sipariş iptal edildi, stoktan düşen ürün geri alınır
}*/

package com.stok.stokTakipProjesi.model;

public enum SiparisDurumu {
    OLUSTURULDU(0),
    ONAYLANDI(1),
    REDDEDILDI(2),
    IPTAL_EDILDI(3);

    private final int value;

    SiparisDurumu(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SiparisDurumu fromValue(int value) {
        for (SiparisDurumu status : SiparisDurumu.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Geçersiz Sipariş Durumu: " + value);
    }
}


