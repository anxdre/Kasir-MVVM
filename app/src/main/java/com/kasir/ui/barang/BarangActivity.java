package com.kasir.ui.barang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kasir.R;
import com.kasir.adapter.BarangAdapter;
import com.kasir.data.model.Barang;
import com.kasir.data.model.BarangHistory;
import com.kasir.ui.barangHistory.BarangHistoryViewModel;
import com.kasir.ui.transaksi.AddTransaksiActivity;

import java.util.List;

public class BarangActivity extends AppCompatActivity {
    private BarangViewModel barangViewModel;
//    private BarangHistoryViewModel barangHistoryViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;

    public static final int RESULT_ADD_ACTIVITY = 1;
    public static final int RESULT_EDIT_ACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        barangViewModel = ViewModelProviders.of(this).get(BarangViewModel.class);
//        barangHistoryViewModel = ViewModelProviders.of(this).get(BarangHistoryViewModel.class);

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(BarangActivity.this, AddEditBarangActivity.class), RESULT_ADD_ACTIVITY);
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final BarangAdapter adapter = new BarangAdapter();
        recyclerView.setAdapter(adapter);

        barangViewModel.getAllBarangs().observe(this, new Observer<List<Barang>>() {
            @Override
            public void onChanged(List<Barang> barangs) {
                adapter.submitList(barangs);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                barangViewModel.delete(adapter.getBarangAt(viewHolder.getAdapterPosition()));
            }

        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new BarangAdapter.onItemClickListerner() {
            @Override
            public void onItemClick(final Barang barang) {
                Intent intent = new Intent(BarangActivity.this, AddEditBarangActivity.class);
                intent.putExtra(AddEditBarangActivity.EXTRA_ID, barang.getId());
                intent.putExtra(AddEditBarangActivity.EXTRA_NAMA, barang.getNamaBarang());
                intent.putExtra(AddEditBarangActivity.EXTRA_HARGA, barang.getHargaBarang());
                intent.putExtra(AddEditBarangActivity.EXTRA_STOK, barang.getStokBarang());
                startActivityForResult(intent, RESULT_EDIT_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_ADD_ACTIVITY && resultCode == RESULT_OK){
            String nama = data.getStringExtra(AddEditBarangActivity.EXTRA_NAMA);
            int harga = data.getIntExtra(AddEditBarangActivity.EXTRA_HARGA, 1);
            int stok = data.getIntExtra(AddEditBarangActivity.EXTRA_STOK, 1);

            Barang barang = new Barang(nama, harga, stok);
            barangViewModel.insert(barang);
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();

        } else if(requestCode == RESULT_EDIT_ACTIVITY && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditBarangActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Data not updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String nama = data.getStringExtra(AddEditBarangActivity.EXTRA_NAMA);
            int harga = data.getIntExtra(AddEditBarangActivity.EXTRA_HARGA, 1);
            int stok = data.getIntExtra(AddEditBarangActivity.EXTRA_STOK, 1);

            Barang barang = new Barang(nama, harga, stok);
            barang.setId(id);
            barangViewModel.update(barang);
            Toast.makeText(this, "Data updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Data not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
