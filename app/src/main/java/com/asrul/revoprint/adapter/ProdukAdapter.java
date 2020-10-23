package com.asrul.revoprint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.asrul.revoprint.R;
import com.asrul.revoprint.model.produk.DataProduk;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> {

    Context mCtx;
    List<DataProduk> produkList;

    public ProdukAdapter(Context mCtx, List<DataProduk> produkList) {
        this.mCtx = mCtx;
        this.produkList = produkList;
    }

    @NonNull
    @Override
    public ProdukAdapter.ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.produk_item, parent, false);
        return new ProdukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukAdapter.ProdukViewHolder holder, int position) {
        final DataProduk dataProduk = produkList.get(position);

        holder.tvTitle.setText(dataProduk.getNama());
        holder.tvPrice.setText(dataProduk.getHarga());

        Glide.with(mCtx)
                .load(dataProduk.getGambar1())
                .placeholder(R.color.colorPrimaryDark)
                .into(holder.imgProduct);

        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, dataProduk.getHarga() + " added to favorites", Toast.LENGTH_SHORT).show();
            }
        });

        holder.cvProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "test", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public static class ProdukViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvPrice;
        ImageView imgProduct, btnFav;
        CardView cvProduct;

        public ProdukViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            imgProduct = itemView.findViewById(R.id.img_product);
            btnFav = itemView.findViewById(R.id.img_fav);
            cvProduct = itemView.findViewById(R.id.cv_product);
        }
    }
}
