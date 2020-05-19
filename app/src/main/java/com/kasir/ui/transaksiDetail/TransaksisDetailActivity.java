package com.kasir.ui.transaksiDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kasir.R;
import com.kasir.adapter.TransaksiDetailAdapter;
import com.kasir.data.model.TransaksiDetail;
import com.kasir.ui.history.HistoryActivity;

import java.util.List;

public class TransaksisDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransaksiDetailViewModel transaksiDetailViewModel;

    public static final String EXTRA_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksis_detail);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TransaksiDetailAdapter adapter = new TransaksiDetailAdapter();
        recyclerView.setAdapter(adapter);

        transaksiDetailViewModel = ViewModelProviders.of(this).get(TransaksiDetailViewModel.class);

        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_ID, -1);
        Log.i("hmm", "onCreate: "+id);

        transaksiDetailViewModel.getGetAllTransaksiDetail().observe(this, new Observer<List<TransaksiDetail>>() {
            @Override
            public void onChanged(List<TransaksiDetail> transaksiDetails) {
                for (int i=0; i<transaksiDetails.size(); i++){
                    int hush = transaksiDetails.get(i).getIdTransaksi();
                    Log.i("hush", "onChanged: "+hush);
                }
            }
        });

        transaksiDetailViewModel.getTranDet(id).observe(this, new Observer<List<TransaksiDetail>>() {
            @Override
            public void onChanged(List<TransaksiDetail> transaksiDetails) {
                adapter.submitList(transaksiDetails);
                for (int i=0; i<transaksiDetails.size(); i++){
                    int bla = transaksiDetails.get(i).getIdTransaksi();
                    Log.i("oi", "onChanged: "+bla);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TransaksisDetailActivity.this, HistoryActivity.class));
    }
}
