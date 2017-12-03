package com.example.eldi.sellfish;

/**
 * Created by eldi on 17/11/2017.
 */

import java.util.ArrayList;

public class Jualan {
    private String id, namaIkan, thumbnailUrl, harga, jumlahStok, penjual, deskripsi;

    private ArrayList<String> jualan;

    public Jualan() {
    }

    public Jualan(String id, String namaIkan, String thumbnailUrl, String harga, String jumlahStok, String penjual, String deskripsi,
                  ArrayList<String> jualan) {
        this.id = id;
        this.namaIkan = namaIkan;
        this.thumbnailUrl = thumbnailUrl;
        this.harga = harga;
        this.jumlahStok = jumlahStok;
        this.penjual = penjual;
        this.deskripsi = deskripsi;
    }

    public String getNamaIkan() {
        return namaIkan;
    }

    public void setNamaIkan(String namaIkan) {
        this.namaIkan = namaIkan;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlahStok() {
        return jumlahStok;
    }

    public void setJumlahStok(String jumlahStok) {
        this.jumlahStok = jumlahStok;
    }

    public ArrayList<String> getJualan() {
        return jualan;
    }

    public void setJualan(ArrayList<String> jualan) {
        this.jualan = jualan;
    }

    public String getPenjual() {
        return penjual;
    }

    public void setPenjual(String penjual) {
        this.penjual = penjual;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }
}
