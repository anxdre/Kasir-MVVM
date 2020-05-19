package com.kasir.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "history_table")
public class History {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String namaPetugas;
    private int totalHarga;
    private int totalUang;
    private String created_at;

    public History(String namaPetugas, int totalHarga, int totalUang, String created_at) {
        this.namaPetugas = namaPetugas;
        this.totalHarga = totalHarga;
        this.totalUang = totalUang;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public int getTotalUang() {
        return totalUang;
    }

    public void setTotalUang(int totalUang) {
        this.totalUang = totalUang;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
