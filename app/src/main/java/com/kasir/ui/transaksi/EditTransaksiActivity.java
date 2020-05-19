package com.kasir.ui.transaksi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.kasir.R;

public class EditTransaksiActivity extends AppCompatActivity {
    private TextView tvNama, tvHarga;
    private NumberPicker numberPicker;

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_IDBARANG = "idBarang";
    public static final String EXTRA_NAMA= "nama";
    public static final String EXTRA_HARGA= "harga";
    public static final String EXTRA_JUMLAH= "jumlah";
    public static final String EXTRA_JUMLAHMAX= "jumlahMax";
    public static final String EXTRA_HARGA_BARANG= "hargaBarang";
    public static final String EXTRA_JUMLAH_HARGA= "jumlahBarang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaksi);

        tvNama = findViewById(R.id.tv_namaTra);
        tvHarga = findViewById(R.id.tv_hargaTra);
        numberPicker = findViewById(R.id.number_picker);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Edit Jumlah Beli");
        final Intent intent = getIntent();
        tvNama.setText(intent.getStringExtra(EXTRA_NAMA));
        tvHarga.setText(String.valueOf(intent.getIntExtra(EXTRA_HARGA, 1)));
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(intent.getIntExtra(EXTRA_JUMLAHMAX, 1));
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tvHarga.setText(String.valueOf(picker.getValue() * intent.getIntExtra(EXTRA_HARGA, 1)));
            }
        });
    }

    private void saveTransaksi() {
        String nama = tvNama.getText().toString();
        int harga = Integer.parseInt(tvHarga.getText().toString());
        int jumlah = numberPicker.getValue();

        if (nama.trim().isEmpty() || harga == 0 || String.valueOf(jumlah).trim().isEmpty() ){
            Toast.makeText(this, "Harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = getIntent();
        Intent data = new Intent();
        data.putExtra(EXTRA_IDBARANG, intent.getIntExtra(EXTRA_IDBARANG, -1));
        data.putExtra(EXTRA_NAMA, nama);
        data.putExtra(EXTRA_HARGA, intent.getIntExtra(EXTRA_HARGA, 0));
        data.putExtra(EXTRA_JUMLAH, jumlah);
        data.putExtra(EXTRA_JUMLAHMAX, intent.getIntExtra(EXTRA_JUMLAHMAX, 1));
        data.putExtra(EXTRA_JUMLAH_HARGA, harga);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_barang, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_barang :
                saveTransaksi();
                return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
