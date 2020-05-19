package com.kasir.ui.barang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.kasir.R;
import com.kasir.data.model.BarangHistory;
import com.kasir.ui.barangHistory.BarangHistoryViewModel;

import java.util.List;

public class AddEditBarangActivity extends AppCompatActivity {
    private EditText etNama, etHarga, etStok;
    private TextView tvNama, tvTambah, tvPengurangan, tvJumlahStok, tvStokAkhir, tvPenyesuaian, tvStok, tvTambahStok;
//    private NumberPicker npStok;

    private BarangHistoryViewModel barangHistoryViewModel;

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_NAMA= "nama";
    public static final String EXTRA_HARGA= "harga";
    public static final String EXTRA_STOK= "stok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_barang);

        etNama = findViewById(R.id.et_nama);
        etHarga = findViewById(R.id.et_harga);
        etStok = findViewById(R.id.et_stok);
        tvStok = findViewById(R.id.tv_stok);
        tvTambahStok = findViewById(R.id.tv_edit_stok);
//        tvNama = findViewById(R.id.tv_namaBarang);
//        tvTambah = findViewById(R.id.tv_jumlah_tambah);
//        tvPengurangan = findViewById(R.id.tv_jumlah_pengurangan);
//        tvJumlahStok = findViewById(R.id.tv_jumlah_stok);
//        tvStokAkhir = findViewById(R.id.tv_jumlah_stok_akhir);
//        tvPenyesuaian = findViewById(R.id.tv_penyesuaian);
//        npStok = findViewById(R.id.np_stok);


        barangHistoryViewModel = ViewModelProviders.of(this).get(BarangHistoryViewModel.class);

        Intent intent = getIntent();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Barang");
//            npStok.setMinValue(0);
//            npStok.setMaxValue(999);
            etNama.setText(intent.getStringExtra(EXTRA_NAMA));
            etHarga.setText(String.valueOf(intent.getIntExtra(EXTRA_HARGA,0)));
            tvStok.setText(String.valueOf(intent.getIntExtra(EXTRA_STOK, 0)));

//            barangHistoryViewModel.getBarangHistory(intent.getIntExtra(EXTRA_ID, 0)).observe(this, new Observer<List<BarangHistory>>() {
//                @Override
//                public void onChanged(List<BarangHistory> barangHistories) {
//
//                    tvNama.setText(barangHistories.get(0).getNamaBarang());
//                    tvTambah.setText(String.valueOf(barangHistories.get(0).getJumlahPenambahan()));
//                    tvPengurangan.setText(String.valueOf(barangHistories.get(0).getJumlahPengurangan()));
//                    tvJumlahStok.setText(String.valueOf(barangHistories.get(0).getJumlahStok()));
//                    tvStokAkhir.setText(String.valueOf(barangHistories.get(0).getStokAkhir()));
//                    tvPenyesuaian.setText(String.valueOf(barangHistories.get(0).getPenyesuaian()));
//
//                }
//            });
        } else {
            setTitle("Add Barang");
            tvStok.setVisibility(View.GONE);
            tvTambahStok.setVisibility(View.GONE);
//            npStok.setMinValue(1);
//            npStok.setMaxValue(999);
//            tvNama.setVisibility(View.INVISIBLE);
//            tvTambah.setVisibility(View.INVISIBLE);
//            tvPengurangan.setVisibility(View.INVISIBLE);
//            tvJumlahStok.setVisibility(View.INVISIBLE);
//            tvStokAkhir.setVisibility(View.INVISIBLE);
//            tvPenyesuaian.setVisibility(View.INVISIBLE);
        }
    }

    private void saveBarang() {
        String nama = etNama.getText().toString();
        String harga = etHarga.getText().toString();
        String stok = tvStok.getText().toString();

            Intent data = new Intent();
            data.putExtra(EXTRA_NAMA, nama);
            data.putExtra(EXTRA_HARGA, Integer.parseInt(harga));
            data.putExtra(EXTRA_STOK, Integer.parseInt(stok) + Integer.parseInt(etStok.getText().toString()));

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if(id != -1){
                data.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, data);
            finish();
    }

    private void saveBarangHistory(){
        String nama = etNama.getText().toString();
        String harga = etHarga.getText().toString();

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            BarangHistory barangHis = new BarangHistory(intent.getIntExtra(EXTRA_ID, 0), tvNama.getText().toString(),
                    Integer.parseInt(etStok.getText().toString()) + Integer.parseInt(tvTambah.getText().toString()), Integer.parseInt(tvPengurangan.getText().toString()),
                    Integer.parseInt(tvJumlahStok.getText().toString()) + Integer.parseInt(etStok.getText().toString()),
                    Integer.parseInt(tvStokAkhir.getText().toString()), Integer.parseInt(tvPenyesuaian.getText().toString()));
            barangHis.setId(intent.getIntExtra(EXTRA_ID, 0));
            barangHistoryViewModel.update(barangHis);
        } else {
            Intent data = new Intent();
            BarangHistory barangHistory = new BarangHistory(data.getIntExtra(EXTRA_ID, 0),
                    nama, Integer.parseInt(etStok.getText().toString()), 0, Integer.parseInt(etStok.getText().toString()), 0, 0);
            barangHistoryViewModel.insert(barangHistory);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_barang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String nama = etNama.getText().toString();
        String harga = etHarga.getText().toString();
        String stok = etStok.getText().toString();
        switch (item.getItemId()){
            case R.id.save_barang :
                if (nama.trim().isEmpty() || harga.trim().isEmpty() || stok.trim().isEmpty()){
                    Toast.makeText(this, "Data harus diisi", Toast.LENGTH_SHORT).show();
                }else {
//                    saveBarangHistory();
                    saveBarang();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
