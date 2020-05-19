package com.kasir.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kasir.R;
import com.kasir.data.model.Barang;
import com.kasir.data.model.Cart;
import com.kasir.ui.transaksi.TransaksiActivity;

public class TransaksiAdapter extends ListAdapter<Cart, TransaksiAdapter.TransaksiHolder> {
    private onItemClickListerner mListener;

    public TransaksiAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Cart> DIFF_CALLBACK = new DiffUtil.ItemCallback<Cart>() {
        @Override
        public boolean areItemsTheSame(Cart oldItem, Cart newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Cart oldItem, Cart newItem) {
            return oldItem.getNamaBarang().equals(newItem.getNamaBarang())  &&
                    oldItem.getJumlahBeli() == newItem.getJumlahBeli() &&
                    oldItem.getJumlahHarga() == newItem.getJumlahHarga();
        }
    };

    @NonNull
    @Override
    public TransaksiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        return new TransaksiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TransaksiHolder holder, int position) {
        final Cart cart = getItem(position);
        holder.tvNama.setText(cart.getNamaBarang());
        holder.tvJumlahHarga.setText(String.valueOf(cart.getJumlahHarga()));
        holder.tvJumlah.setText(String.valueOf(cart.getJumlahBeli()));

    }

    public class TransaksiHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvJumlahHarga, tvJumlah;
        public TransaksiHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvJumlahHarga = itemView.findViewById(R.id.tv_harga);
            tvJumlah = itemView.findViewById(R.id.tv_jumlah);

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

    public Cart getCartAt(int position){
        return getItem(position);
    }

    public interface onItemClickListerner{
        void onItemClick(Cart cart);
    }

    public void setOnItemClickListener(onItemClickListerner listener){
        this.mListener = listener;
    }
}
