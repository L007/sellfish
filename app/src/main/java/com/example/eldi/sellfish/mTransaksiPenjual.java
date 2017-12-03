package com.example.eldi.sellfish;

/**
 * Created by eldi on 17/11/2017.
 */

import java.util.ArrayList;

public class mTransaksiPenjual {
    private String id, namaIkan, thumbnailUrl, harga, jumlahPesanan, pembeli, deskripsi;

    private ArrayList<String> transaksi;

    public mTransaksiPenjual() {
    }

    public mTransaksiPenjual(String id, String namaIkan, String thumbnailUrl, String harga, String jumlahPesanan, String pembeli, String deskripsi,
                             ArrayList<String> transaksi) {
        this.id = id;
        this.namaIkan = namaIkan;
        this.thumbnailUrl = thumbnailUrl;
        this.harga = harga;
        this.jumlahPesanan = jumlahPesanan;
        this.pembeli = pembeli;
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

    public String getjumlahPesanan() {
        return jumlahPesanan;
    }

    public void setjumlahPesanan(String jumlahPesanan) {
        this.jumlahPesanan = jumlahPesanan;
    }

    public ArrayList<String> getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(ArrayList<String> transaksi) {
        this.transaksi = transaksi;
    }

    public String getpembeli() {
        return pembeli;
    }

    public void setpembeli(String pembeli) {
        this.pembeli = pembeli;
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
