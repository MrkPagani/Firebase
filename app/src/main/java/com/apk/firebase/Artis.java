package com.apk.firebase;

public class Artis {

    String artisId;
    String artisNama;
    String artisGenre;

    public Artis(){

    }

    public Artis(String artisId, String artisNama, String artisGenre) {
        this.artisId = artisId;
        this.artisNama = artisNama;
        this.artisGenre = artisGenre;
    }

    public String getArtisId() {
        return artisId;
    }

    public String getArtisNama() {
        return artisNama;
    }

    public String getArtisGenre() {
        return artisGenre;
    }
}
