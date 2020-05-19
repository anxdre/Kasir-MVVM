package com.kasir.adapter;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kasir.R;
import com.kasir.data.model.History;

public class HistoryAdapter extends ListAdapter<History, HistoryAdapter.HistoryHolder> {
    private onItemClickListerner mListener;

    public HistoryAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<History> DIFF_CALLBACK = new DiffUtil.ItemCallback<History>() {
        @Override
        public boolean areItemsTheSame(History oldItem, History newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(History oldItem, History newItem) {
            return oldItem.getNamaPetugas().equals(newItem.getNamaPetugas())  &&
                    oldItem.getTotalHarga() == newItem.getTotalHarga() &&
                    oldItem.getCreated_at() == newItem.getCreated_at();
        }
    };

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        History history = getItem(position);
        holder.tvNama.setText(history.getNamaPetugas());
        holder.tvTotalHarga.setText(String.valueOf(history.getTotalHarga()));
        holder.tvTime.setText(history.getCreated_at());
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvTotalHarga, tvTime;
        public HistoryHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama_trandet);
            tvTotalHarga = itemView.findViewById(R.id.tv_total_harga_trandet);
            tvTime = itemView.findViewById(R.id.tv_time);

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

    public History getCartAt(int position){
        return getItem(position);
    }

    public interface onItemClickListerner{
        void onItemClick(History history);
    }

    public void setOnItemClickListener(onItemClickListerner listener){
        this.mListener = listener;
    }
}
