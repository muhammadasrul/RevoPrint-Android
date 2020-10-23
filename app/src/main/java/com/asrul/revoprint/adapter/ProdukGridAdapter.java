package com.asrul.revoprint.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.asrul.revoprint.ui.ProdukDetailActivity;
import com.asrul.revoprint.R;
import com.asrul.revoprint.model.produk.DataProduk;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProdukGridAdapter extends RecyclerView.Adapter<ProdukGridAdapter.ProdukGridViewHolder> {

    Context mCtx;
    List<DataProduk> produkList;

    public ProdukGridAdapter(Context mCtx, List<DataProduk> produkList){
        this.mCtx = mCtx;
        this.produkList = produkList;
    }

    @NonNull
    @Override
    public ProdukGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.produk_grid_item, parent, false);
        return new ProdukGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProdukGridViewHolder holder, int position) {
        final DataProduk dataProduk = produkList.get(position);

        holder.tvName.setText(dataProduk.getNama());
        holder.tvPrice.setText(dataProduk.getHarga());

        Glide.with(mCtx)
                .load(dataProduk.getGambar1())
                .placeholder(R.color.colorPrimaryDark)
                .into(holder.imgProduct);

        holder.cvProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, ProdukDetailActivity.class);
                intent.putExtra(ProdukDetailActivity.EXTRA_PRODUK, dataProduk);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public static class ProdukGridViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvName, tvPrice;
        private final CardView cvProduct;

        public ProdukGridViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            cvProduct = itemView.findViewById(R.id.cv_product);
        }
    }
}
