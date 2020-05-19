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
import com.kasir.data.model.Barang;

public class BarangAdapter extends ListAdapter<Barang, BarangAdapter.BarangHolder> {
    private onItemClickListerner mListener;

    public BarangAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Barang> DIFF_CALLBACK = new DiffUtil.ItemCallback<Barang>() {
        @Override
        public boolean areItemsTheSame(Barang oldItem, Barang newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Barang oldItem, Barang newItem) {
            return oldItem.getNamaBarang().equals(newItem.getNamaBarang()) &&
                    oldItem.getHargaBarang() == newItem.getHargaBarang() &&
                    oldItem.getStokBarang() == newItem.getStokBarang();
        }
    };

    @NonNull
    @Override
    public BarangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new BarangHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangHolder holder, int position) {
        Barang barang = getItem(position);
        holder.tvNama.setText(barang.getNamaBarang());
        holder.tvHarga.setText(String.valueOf(barang.getHargaBarang()));
        holder.tvStok.setText(String.valueOf(barang.getStokBarang()));
    }

    public class BarangHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvHarga, tvStok;

        public BarangHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvHarga = itemView.findViewById(R.id.tv_harga);
            tvStok = itemView.findViewById(R.id.tv_stok);

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

    public Barang getBarangAt(int position){
        return getItem(position);
    }

    public interface onItemClickListerner{
        void onItemClick(Barang barang);
    }

    public void setOnItemClickListener(onItemClickListerner listener){
        this.mListener = listener;
    }
}
