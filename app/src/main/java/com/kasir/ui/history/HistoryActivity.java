package com.kasir.ui.history;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kasir.R;
import com.kasir.adapter.HistoryAdapter;
import com.kasir.data.model.History;
import com.kasir.ui.dashboard.DashboardActivity;
import com.kasir.ui.transaksiDetail.TransaksisDetailActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HistoryViewModel historyViewModel;
    private FloatingActionButton fab;
    String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";
    SQLiteToExcel sqliteToExcel;

    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        fab = findViewById(R.id.btn_export);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final HistoryAdapter adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);

        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);

        historyViewModel.getGetAllHistory().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                adapter.submitList(histories);
            }
        });

        adapter.setOnItemClickListener(new HistoryAdapter.onItemClickListerner() {
            @Override
            public void onItemClick(History history) {
                Intent intent = new Intent(HistoryActivity.this, TransaksisDetailActivity.class);
                intent.putExtra(TransaksisDetailActivity.EXTRA_ID, history.getId());
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(HistoryActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    File file = new File(directory_path);
                    if (!file.exists()) {
                        Log.v("File Created", String.valueOf(file.mkdirs()));
                    }

                    sqliteToExcel = new SQLiteToExcel(getApplicationContext(), "history_database", directory_path);
                    sqliteToExcel.exportAllTables("history.xls", new SQLiteToExcel.ExportListener() {
                        @Override
                        public void onStart() {

                        }
                        @Override
                        public void onCompleted(String filePath) {
                            sqliteToExcel = new SQLiteToExcel(getApplicationContext(), "transaksi_detail_database", directory_path);
                            sqliteToExcel.exportAllTables("detailBarang.xls", new SQLiteToExcel.ExportListener() {
                                @Override
                                public void onStart() {

                                }
                                @Override
                                public void onCompleted(String filePath) {
                                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                    StrictMode.setVmPolicy(builder.build());
                                    String dir = Environment.getExternalStorageDirectory().getPath() + "/Backup/history.xls";
                                    String direc = Environment.getExternalStorageDirectory().getPath() + "/Backup/detailBarang.xls";
                                    File fil = new File(dir);
                                    File fi = new File(direc);
                                    Uri path = Uri.fromFile(fil);
                                    Uri pat = Uri.fromFile(fi);
                                    ArrayList<Uri> uris = new ArrayList<>();
                                    uris.add(path);
                                    uris.add(pat);
                                    Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                                    emailIntent .setType("vnd.android.cursor.dir/email");
                                    String to[] = {"denidc27@gmail.com"};
                                    emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
                                    emailIntent .putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                                    emailIntent .putExtra(Intent.EXTRA_SUBJECT, "History Transaksi");
                                    startActivity(Intent.createChooser(emailIntent , "Send email..."));
                                    Toast.makeText(HistoryActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onError(Exception e) {
                                    Toast.makeText(HistoryActivity.this, "gagal"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(HistoryActivity.this, "gagal"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    requestStoragePermission();
                }
            }
        });
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(HistoryActivity.this,
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HistoryActivity.this, DashboardActivity.class));
    }
}
