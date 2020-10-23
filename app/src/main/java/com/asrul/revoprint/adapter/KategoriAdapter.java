package com.asrul.revoprint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asrul.revoprint.R;
import com.asrul.revoprint.model.kategori.DataKategori;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder> {

    private Context mCtx;
    private List<DataKategori> kategoriList;

    private OnItemClickCallback onItemClickCallback;

    public KategoriAdapter(Context mCtx, List<DataKategori> kategoriList) {
        this.kategoriList = kategoriList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public KategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.kategori_item, parent, false);
        return new KategoriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final KategoriViewHolder holder, int position) {
        final DataKategori dataKategori = kategoriList.get(position);
        holder.tvKategori.setText(dataKategori.getNamaKategori());
        holder.tvKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(kategoriList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public void setOnItemClickCallback(OnItemClickCallback onClickItemCallback) {
        this.onItemClickCallback = onClickItemCallback;
    }

    public static class KategoriViewHolder extends RecyclerView.ViewHolder {
        TextView tvKategori;
        public KategoriViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKategori = itemView.findViewById(R.id.tv_kategori);
        }
    }

    public interface OnItemClickCallback {
        void  onItemClicked(DataKategori dataKategori);
    }
}
