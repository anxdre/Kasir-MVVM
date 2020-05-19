package com.kasir.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "transaksi_detail_table")
public class TransaksiDetail {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idTransaksi;
    private String namaBarang;
    private int jumlahBeli;
    private int jumlahHarga;

    public TransaksiDetail(int idTransaksi, String namaBarang, int jumlahBeli, int jumlahHarga) {
        this.idTransaksi = idTransaksi;
        this.namaBarang = namaBarang;
        this.jumlahBeli = jumlahBeli;
        this.jumlahHarga = jumlahHarga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getJumlahBeli() {
        return jumlahBeli;
    }

    public void setJumlahBeli(int jumlahBeli) {
        this.jumlahBeli = jumlahBeli;
    }

    public int getJumlahHarga() {
        return jumlahHarga;
    }

    public void setJumlahHarga(int jumlahHarga) {
        this.jumlahHarga = jumlahHarga;
    }
}
