package com.kasir.ui.transaksi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kasir.R;
import com.kasir.adapter.BarangAdapter;
import com.kasir.data.model.Barang;
import com.kasir.ui.barang.BarangViewModel;

import java.util.Arrays;
import java.util.List;

public class AddTransaksiActivity extends AppCompatActivity {
    private BarangViewModel barangViewModel;
    private RecyclerView recyclerView;

    public static final String EXTRA_ARRAY = "array";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaksi);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final BarangAdapter adapter = new BarangAdapter();
        recyclerView.setAdapter(adapter);

        barangViewModel = ViewModelProviders.of(this).get(BarangViewModel.class);
        barangViewModel.getAllBarangs().observe(this, new Observer<List<Barang>>() {
            @Override
            public void onChanged(List<Barang> barangs) {
                adapter.submitList(barangs);
            }
        });

        adapter.setOnItemClickListener(new BarangAdapter.onItemClickListerner() {
            @Override
            public void onItemClick(Barang barang) {
                Bundle in = getIntent().getExtras();
                int[] angka = in.getIntArray(EXTRA_ARRAY);
                if (contains(angka, barang.getId()) == true){
                    Toast.makeText(AddTransaksiActivity.this, "Barang sudah ada di keranjang", Toast.LENGTH_SHORT).show();
                } else if (barang.getStokBarang() == 0){
                    Toast.makeText(AddTransaksiActivity.this, "Stok Barang tidak mencukupi", Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = new Intent(AddTransaksiActivity.this, TransaksiActivity.class);
                    intent.putExtra(TransaksiActivity.EXTRA_IDBARANG, barang.getId());
                    intent.putExtra(TransaksiActivity.EXTRA_NAMA, barang.getNamaBarang());
                    intent.putExtra(TransaksiActivity.EXTRA_JUMLAH, barang.getStokBarang());
                    intent.putExtra(TransaksiActivity.EXTRA_HARGA, barang.getHargaBarang());
                    startActivity(intent);
                }
            }
        });
    }

    public static boolean contains(int[] array, int v){
        boolean result = false;
        for (int i : array){
            if (i == v){
                result = true;
            }
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddTransaksiActivity.this, TransaksiActivity.class));
    }
}
