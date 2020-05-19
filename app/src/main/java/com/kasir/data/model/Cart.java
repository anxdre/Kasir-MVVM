package com.kasir.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "cart_table")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idBarang;
    private String namaBarang;
    private int jumlahBeli;
    private int jumlahBeliMax;
    private int jumlahHarga;
    private int harga;

    public Cart(int idBarang, String namaBarang, int jumlahBeli, int jumlahHarga, int jumlahBeliMax, int harga) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.jumlahBeli = jumlahBeli;
        this.jumlahHarga = jumlahHarga;
        this.jumlahBeliMax = jumlahBeliMax;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setIdBarang(String namaBarang) {
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

    public int getJumlahBeliMax() {
        return jumlahBeliMax;
    }

    public void setJumlahBeliMax(int jumlahBeliMax) {
        this.jumlahBeliMax = jumlahBeliMax;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
