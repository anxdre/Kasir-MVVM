package com.kasir.ui.transaksi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kasir.R;
import com.kasir.adapter.TransaksiAdapter;
import com.kasir.data.database.history.HistoryDatabase;
import com.kasir.data.model.Barang;
import com.kasir.data.model.Cart;
import com.kasir.data.model.History;
import com.kasir.data.model.TransaksiDetail;
import com.kasir.ui.barang.AddEditBarangActivity;
import com.kasir.ui.barang.BarangViewModel;
import com.kasir.ui.dashboard.DashboardActivity;
import com.kasir.ui.history.HistoryActivity;
import com.kasir.ui.history.HistoryViewModel;
import com.kasir.ui.transaksiDetail.TransaksiDetailViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TransaksiActivity extends AppCompatActivity {
    private TransaksiViewModel transaksiViewModel;
    private HistoryViewModel historyViewModel;
    private TransaksiDetailViewModel transaksiDetailViewModel;
    private BarangViewModel barangViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;
    private TransaksiAdapter adapter;
    private TextView tvTotalHarga, tvKembalian, tvPesanan;
    private EditText etTotalUang;
    private Button btnBayar;
    private LinearLayout layProses;

    private int totalHarga = 0;
    private TransaksiDetail transaksiDetail = null;

    private String namaTranDet;
    private int jumlahBeli;
    private int jumlahHarga;
    private int harga;
    private int jumlahAkhir;
    private int jumlahMax;
    private int idBarang;

    private int[] angka;

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_IDBARANG = "idBarang";
    public static final String EXTRA_NAMA = "nama";
    public static final String EXTRA_JUMLAH = "jumlah";
    public static final String EXTRA_HARGA = "harga";
    public static final String EXTRA_JUMLAHMAX= "jumlahMax";
    public static final String EXTRA_ARRAY = "array";
    public static final String EXTRA_JUMLAH_HARGA= "jumlahBarang";

    public static final int RESULT_EDIT_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new TransaksiAdapter();
        recyclerView.setAdapter(adapter);

        tvTotalHarga = findViewById(R.id.tv_total_harga);
        btnAdd = findViewById(R.id.btn_add);
        btnBayar = findViewById(R.id.btn_bayar);
        etTotalUang = findViewById(R.id.et_total_uang);
        tvKembalian = findViewById(R.id.tv_kembalian);
        layProses = findViewById(R.id.lay_proses);
        tvPesanan = findViewById(R.id.tv_pesanan);

        final String totalUang = etTotalUang.getText().toString();

        transaksiViewModel = ViewModelProviders.of(this).get(TransaksiViewModel.class);
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        transaksiDetailViewModel = ViewModelProviders.of(this).get(TransaksiDetailViewModel.class);
        barangViewModel = ViewModelProviders.of(this).get(BarangViewModel.class);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalHarga = 0;

                final Intent intent = new Intent(TransaksiActivity.this, AddTransaksiActivity.class);

                transaksiViewModel.getAllCarts().observe(TransaksiActivity.this, new Observer<List<Cart>>() {
                    @Override
                    public void onChanged(List<Cart> carts) {
                        Bundle extras = new Bundle();
                        if (carts == null) {

                        }  else {
                            angka = new int[carts.size()];
                            for (int i = 0; i < carts.size(); i++) {
                                angka[i] = carts.get(i).getIdBarang();
                                Log.i("uwaw", "onChanged: " + angka[i]);
                            }
                            extras.putIntArray(AddTransaksiActivity.EXTRA_ARRAY, angka);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

        transaksiViewModel.getAllCarts().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                adapter.submitList(carts);
                totalHarga = 0;
                for(int i=0; i<carts.size(); i++){
                    totalHarga += carts.get(i).getJumlahHarga();
                }
                tvTotalHarga.setText(String.valueOf(totalHarga));
                etTotalUang.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.toString().equals("")){
                            tvKembalian.setText("0");
                        } else {
                            int kem = Integer.parseInt(String.valueOf(s)) - totalHarga;
                            tvKembalian.setText(String.valueOf(kem));
                        }
                    }
                });
                if (String.valueOf(totalHarga).equals("0")){
                    tvPesanan.setVisibility(View.VISIBLE);
                    layProses.setVisibility(View.INVISIBLE);
                } else {
                    tvPesanan.setVisibility(View.GONE);
                    layProses.setVisibility(View.VISIBLE);
                }
            }
        });

        Intent data = getIntent();
        if (data.getStringExtra(EXTRA_NAMA) == null){

        } else{
            int id = data.getIntExtra(EXTRA_IDBARANG, 0);
            String nama = data.getStringExtra(EXTRA_NAMA);
            int jumlah = 1 ;
            int harga = data.getIntExtra(EXTRA_HARGA, 1000);
            int jumlahMax = data.getIntExtra(EXTRA_JUMLAH, 1);

            Cart cart = new Cart(id, nama, jumlah, harga, jumlahMax, harga);
            transaksiViewModel.insert(cart);
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
        }

        adapter.setOnItemClickListener(new TransaksiAdapter.onItemClickListerner() {
            @Override
            public void onItemClick(Cart cart) {
                totalHarga = 0;
                Intent intent = new Intent(TransaksiActivity.this, EditTransaksiActivity.class);
                intent.putExtra(EditTransaksiActivity.EXTRA_ID, cart.getId());
                intent.putExtra(EditTransaksiActivity.EXTRA_IDBARANG, cart.getIdBarang());
                intent.putExtra(EditTransaksiActivity.EXTRA_NAMA, cart.getNamaBarang());
                intent.putExtra(EditTransaksiActivity.EXTRA_HARGA, cart.getHarga());
                intent.putExtra(EditTransaksiActivity.EXTRA_JUMLAH, cart.getJumlahBeli());
                intent.putExtra(EditTransaksiActivity.EXTRA_JUMLAHMAX, cart.getJumlahBeliMax());
                intent.putExtra(EditTransaksiActivity.EXTRA_JUMLAH_HARGA, cart.getJumlahHarga());
                startActivityForResult(intent, RESULT_EDIT_ACTIVITY);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                totalHarga = 0;
                transaksiViewModel.delete(adapter.getCartAt(viewHolder.getAdapterPosition()));
            }

        }).attachToRecyclerView(recyclerView);

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTotalUang.getText().toString().trim().isEmpty()){
                    Toast.makeText(TransaksiActivity.this, "Masukkan uang anda", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(etTotalUang.getText().toString()) < totalHarga){
                    Toast.makeText(TransaksiActivity.this, "Uang anda kurang", Toast.LENGTH_SHORT).show();
                } else {
                    long date = new Date().getTime();
                    History history = new History("Admin", totalHarga, Integer.parseInt(etTotalUang.getText().toString()),
                            String.valueOf(DateFormat.format("dd/MM/yyyy - HH:mm", date)));
                    historyViewModel.insert(history);

                    final Intent intent = new Intent(TransaksiActivity.this, ProsesTransaksiActivity.class);
                    historyViewModel.getId().observe(TransaksiActivity.this, new Observer<History>() {
                        @Override
                        public void onChanged(History history) {
                            int id = 0;
                            if (history == null){
                                intent.putExtra(ProsesTransaksiActivity.EXTRA_TOTALHARGA, Integer.parseInt(tvTotalHarga.getText().toString()));
                                intent.putExtra(ProsesTransaksiActivity.EXTRA_TOTALUANG, Integer.parseInt(etTotalUang.getText().toString()));
                                intent.putExtra(ProsesTransaksiActivity.EXTRA_KEMBALIAN, Integer.parseInt(tvKembalian.getText().toString()));
                                intent.putExtra(ProsesTransaksiActivity.EXTRA_IDHIS, id+1);
                                startActivity(intent);
                            } else {
                                id = history.getId();
                                intent.putExtra(ProsesTransaksiActivity.EXTRA_TOTALHARGA, Integer.parseInt(tvTotalHarga.getText().toString()));
                                intent.putExtra(ProsesTransaksiActivity.EXTRA_TOTALUANG, Integer.parseInt(etTotalUang.getText().toString()));
                                intent.putExtra(ProsesTransaksiActivity.EXTRA_KEMBALIAN, Integer.parseInt(tvKembalian.getText().toString()));
                                intent.putExtra(ProsesTransaksiActivity.EXTRA_IDHIS, id+1);
                                startActivity(intent);
                            }
                        }
                    });

//                    totalHarga = 0;
//                    etTotalUang.setText("");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_EDIT_ACTIVITY && resultCode == RESULT_OK){
            String nama = data.getStringExtra(EditTransaksiActivity.EXTRA_NAMA);
            int idBarang = data.getIntExtra(EditTransaksiActivity.EXTRA_IDBARANG, -1);
            int id = data.getIntExtra(EditTransaksiActivity.EXTRA_ID, -1);
            int harga = data.getIntExtra(EditTransaksiActivity.EXTRA_JUMLAH_HARGA, 1);
            int jumlah = data.getIntExtra(EditTransaksiActivity.EXTRA_JUMLAH, 1);
            int jumlahMax = data.getIntExtra(EditTransaksiActivity.EXTRA_JUMLAHMAX, 1);
            int hargaBarang = data.getIntExtra(EditTransaksiActivity.EXTRA_HARGA, 0);

            Cart cart = new Cart(idBarang, nama, jumlah, harga, jumlahMax, hargaBarang);
            cart.setId(id);
            transaksiViewModel.update(cart);
            Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data not added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        transaksiViewModel.deleteAll();
        startActivity(new Intent(TransaksiActivity.this, DashboardActivity.class));
    }
}
