package com.kasir.ui.barangHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kasir.R;
import com.kasir.adapter.BarangHistoryAdapter;
import com.kasir.data.model.BarangHistory;

import java.io.File;
import java.util.List;

public class BarangHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BarangHistoryViewModel barangHistoryViewModel;
    private FloatingActionButton fab;

    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";
    SQLiteToExcel sqliteToExcel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_history);

        File file = new File(directory_path);
        if (!file.exists()) {
            Log.v("File Created", String.valueOf(file.mkdirs()));
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        fab = findViewById(R.id.btn_export);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqliteToExcel = new SQLiteToExcel(getApplicationContext(), "barang_history_database", directory_path);
                sqliteToExcel.exportAllTables("bla.xls", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {

                    }
                    @Override
                    public void onCompleted(String filePath) {
                        Toast.makeText(BarangHistoryActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(BarangHistoryActivity.this, "gagal"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        final BarangHistoryAdapter adapter = new BarangHistoryAdapter();
        recyclerView.setAdapter(adapter);

        barangHistoryViewModel = ViewModelProviders.of(this).get(BarangHistoryViewModel.class);
        barangHistoryViewModel.getAllBarangHistories().observe(this, new Observer<List<BarangHistory>>() {
            @Override
            public void onChanged(List<BarangHistory> barangHistories) {
                adapter.submitList(barangHistories);
            }
        });

    }
}
