package com.kasir.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kasir.R;
import com.kasir.data.model.History;
import com.kasir.ui.barang.BarangActivity;
import com.kasir.ui.barangHistory.BarangHistoryActivity;
import com.kasir.ui.history.HistoryActivity;
import com.kasir.ui.history.HistoryViewModel;
import com.kasir.ui.login.LoginActivity;
import com.kasir.ui.transaksi.TransaksiActivity;

public class DashboardActivity extends AppCompatActivity {
    private Button btnBarang, btnTransaksi, btnHistory, btnBarangHistory;
    private HistoryViewModel historyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnBarang = findViewById(R.id.btnBarang);
        btnTransaksi = findViewById(R.id.btn_transaksi);
        btnHistory = findViewById(R.id.btnHistory);
//        btnBarangHistory = findViewById(R.id.btnBarangHistory);

        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);

        btnBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, BarangActivity.class));
            }
        });

        btnTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, TransaksiActivity.class));
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, HistoryActivity.class));
            }
        });

//        btnBarangHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DashboardActivity.this, BarangHistoryActivity.class));
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
    }
}
