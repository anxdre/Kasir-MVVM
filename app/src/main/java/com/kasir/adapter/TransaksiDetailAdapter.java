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
import com.kasir.data.model.TransaksiDetail;

public class TransaksiDetailAdapter extends ListAdapter<TransaksiDetail, TransaksiDetailAdapter.TransaksiDetailHolder> {
    private onItemClickListerner mListener;

    public TransaksiDetailAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TransaksiDetail> DIFF_CALLBACK = new DiffUtil.ItemCallback<TransaksiDetail>() {
        @Override
        public boolean areItemsTheSame(TransaksiDetail oldItem, TransaksiDetail newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(TransaksiDetail oldItem, TransaksiDetail newItem) {
            return oldItem.getIdTransaksi() == newItem.getIdTransaksi() &&
                    oldItem.getNamaBarang().equals(newItem.getNamaBarang())  &&
                    oldItem.getJumlahBeli() == newItem.getJumlahBeli() &&
                    oldItem.getJumlahHarga() == newItem.getJumlahHarga();
        }
    };

    @NonNull
    @Override
    public TransaksiDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi_detail, parent, false);
        return new TransaksiDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiDetailHolder holder, int position) {
        TransaksiDetail transaksiDetail = getItem(position);
        holder.tvNama.setText(transaksiDetail.getNamaBarang());
        holder.tvHarga.setText(String.valueOf(transaksiDetail.getJumlahHarga()));
        holder.tvStok.setText(String.valueOf(transaksiDetail.getJumlahBeli()));

    }

    public class TransaksiDetailHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvHarga, tvStok;
        public TransaksiDetailHolder(@NonNull View itemView) {
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

    public TransaksiDetail getCartAt(int position){
        return getItem(position);
    }

    public interface onItemClickListerner{
        void onItemClick(TransaksiDetail transaksiDetail);
    }

    public void setOnItemClickListener(onItemClickListerner listener){
        this.mListener = listener;
    }
}
