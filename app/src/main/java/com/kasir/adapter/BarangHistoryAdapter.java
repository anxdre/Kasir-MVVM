package com.kasir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kasir.R;
import com.kasir.data.model.BarangHistory;

public class BarangHistoryAdapter extends ListAdapter<BarangHistory, BarangHistoryAdapter.BarangHistoryHolder> {
    private onItemClickListerner mListener;

    public BarangHistoryAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<BarangHistory> DIFF_CALLBACK = new DiffUtil.ItemCallback<BarangHistory>() {
        @Override
        public boolean areItemsTheSame(BarangHistory oldItem, BarangHistory newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(BarangHistory oldItem, BarangHistory newItem) {
            return oldItem.getIdBarang() == newItem.getIdBarang() &&
                    oldItem.getNamaBarang().equals(newItem.getNamaBarang()) &&
                    oldItem.getJumlahPenambahan() == newItem.getJumlahPenambahan() &&
                    oldItem.getJumlahPengurangan() == newItem.getJumlahPengurangan() &&
                    oldItem.getJumlahStok() == newItem.getJumlahStok() &&
                    oldItem.getPenyesuaian() == newItem.getPenyesuaian();
        }
    };

    @NonNull
    @Override
    public BarangHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_history, parent, false);
        return new BarangHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangHistoryHolder holder, int position) {
        BarangHistory barangHistory = getItem(position);
        holder.tvNamaBarang.setText(barangHistory.getNamaBarang());
        holder.tvJumlahTambah.setText(String.valueOf(barangHistory.getJumlahPenambahan()));
        holder.tvJumlahPengurangan.setText(String.valueOf(barangHistory.getJumlahPengurangan()));
        holder.tvJumlahStok.setText(String.valueOf(barangHistory.getJumlahStok()));
        holder.tvStokAkhir.setText(String.valueOf(barangHistory.getStokAkhir()));
        holder.tvPenyesuaian.setText(String.valueOf(barangHistory.getPenyesuaian()));
    }

    public class BarangHistoryHolder extends RecyclerView.ViewHolder {
        TextView tvNamaBarang, tvJumlahTambah, tvJumlahPengurangan, tvJumlahStok, tvStokAkhir, tvPenyesuaian;
        public BarangHistoryHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaBarang = itemView.findViewById(R.id.tv_namaBarang);
            tvJumlahTambah = itemView.findViewById(R.id.tv_jumlah_tambah);
            tvJumlahPengurangan = itemView.findViewById(R.id.tv_jumlah_pengurangan);
            tvJumlahStok = itemView.findViewById(R.id.tv_jumlah_stok);
            tvStokAkhir = itemView.findViewById(R.id.tv_jumlah_stok_akhir);
            tvPenyesuaian = itemView.findViewById(R.id.tv_penyesuaian);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(mListener != null && position != RecyclerView.NO_POSITION){
                        mListener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public BarangHistory getBarangHistoryAt(int position){
        return getItem(position);
    }

    public interface onItemClickListerner{
        void onItemClick(BarangHistory barangHistory);
    }

    public void setOnItemClickListener(onItemClickListerner listener){
        this.mListener = listener;
    }
}
