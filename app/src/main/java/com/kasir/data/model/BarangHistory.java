package com.kasir.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "barang_history_table")
public class BarangHistory {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idBarang;
    private String namaBarang;
    private int jumlahPenambahan;
    private int jumlahPengurangan;
    private int jumlahStok;
    private int stokAkhir;
    private int penyesuaian;

    public BarangHistory(int idBarang, String namaBarang, int jumlahPenambahan, int jumlahPengurangan, int jumlahStok, int stokAkhir, int penyesuaian) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.jumlahPenambahan = jumlahPenambahan;
        this.jumlahPengurangan = jumlahPengurangan;
        this.jumlahStok = jumlahStok;
        this.stokAkhir = stokAkhir;
        this.penyesuaian = penyesuaian;
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

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getJumlahPenambahan() {
        return jumlahPenambahan;
    }

    public void setJumlahPenambahan(int jumlahPenambahan) {
        this.jumlahPenambahan = jumlahPenambahan;
    }

    public int getJumlahPengurangan() {
        return jumlahPengurangan;
    }

    public void setJumlahPengurangan(int jumlahPengurangan) {
        this.jumlahPengurangan = jumlahPengurangan;
    }

    public int getJumlahStok() {
        return jumlahStok;
    }

    public void setJumlahStok(int jumlahStok) {
        this.jumlahStok = jumlahStok;
    }

    public int getStokAkhir() {
        return stokAkhir;
    }

    public void setStokAkhir(int stokAkhir) {
        stokAkhir = stokAkhir;
    }

    public int getPenyesuaian() {
        return penyesuaian;
    }

    public void setPenyesuaian(int penyesuaian) {
        this.penyesuaian = penyesuaian;
    }
}
